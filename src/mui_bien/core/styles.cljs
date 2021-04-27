(ns mui-bien.core.styles
  (:require
   [reagent.core :as r]
   [mui-bien.interop :refer [decorate]]
   ["@material-ui/core/styles" :as mui-styles
    :refer [ThemeProvider createMuiTheme withStyles]]))

(def theme-provider (r/adapt-react-class ThemeProvider))

(defn theme
  "Create a theme."
  [options]
  (createMuiTheme (clj->js options)))

(defn with-styles
  "Apply Material-UI styles to a Reagent component.  Translates between
   React/JavaScript and Reagent/ClojureScript; styles and classes
   will be ClojureScript maps."
  ([styles]
   (let [hoc (withStyles (if (fn? styles)
                           (fn [theme] (clj->js (styles theme)))
                           (clj->js styles)))]
     (fn [component]
       (->> (fn [{:keys [classes] :as props} & children]
              (apply component
                     (assoc props :classes (js->clj classes :keywordize-keys true))
                     children))
            (decorate hoc)))))
  ([styles component] ((with-styles styles) component)))

(def emphasize mui-styles/emphasize)
(def fade mui-styles/fade)