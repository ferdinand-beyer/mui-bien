(ns mui-bien.core.styles
  (:require
   [reagent.core :as r]
   [mui-bien.interop :refer [decorate]]
   ["@material-ui/core/styles" :as mui-styles]))

;;;; Theming
;;
;; API:     https://material-ui.com/customization/theming/#api
;; Default: https://material-ui.com/customization/default-theme/

;;;; TODO: Support both ClojureScript and JavaScript themes.
(def theme-provider (r/adapt-react-class mui-styles/ThemeProvider))

(defn create-mui-theme
  "Creates a theme from one or more options maps.  Returns a JavaScript
   object suitable to be passed to a theme-provider."
  [opts & more]
  (apply mui-styles/createMuiTheme (clj->js opts) (map clj->js more)))

(defn use-theme
  "This hook returns the theme object."
  []
  (mui-styles/useTheme))

;;;; Styles
;;
;; API: https://material-ui.com/styles/api/

(defn as-styles
  "Creates a styles JavaScript object or function from its ClojureScript
   equivalent.

   The styles argument can be a map or a fn taking a theme and returning
   a map.  Every item will generate a CSS class, identifyable in the
   component by the key.

   Note that the theme passed to a styles function will be a JavaScript
   object, not a ClojureScript map.

   Supports nested selectors and embedded functions to access the
   component's props, as described here:
   https://material-ui.com/styles/basics/"
  [styles]
  ;; TODO: Wrap functions to pass props as cljs maps?
  (if (fn? styles)
    ;; Transform theme?
    (fn [theme]
      (clj->js (styles theme)))
    (clj->js styles)))

(defn make-styles
  "Returns a React Hook (a function) from a styles description as supported
   by as-styles.  When called, the hook will return a ClojureScript map of
   style keywords to CSS class names."
  ([styles]
   (make-styles styles nil))
  ([styles options]
   (comp #(js->clj % :keywordize-keys true)
         (mui-styles/makeStyles (as-styles styles) (clj->js options)))))

(defn with-styles
  "DEPRECATED.  Use make-style hook instead.
   
   Apply Material-UI styles to a Reagent component.  Translates between
   React/JavaScript and Reagent/ClojureScript; styles and classes
   will be ClojureScript maps."
  ([styles]
   (let [hoc (mui-styles/withStyles
              (if (fn? styles)
                (fn [theme] (clj->js (styles theme)))
                (clj->js styles)))]
     (fn [component]
       (->> (fn [{:keys [classes] :as props} & children]
              (apply component
                     (assoc props :classes (js->clj classes :keywordize-keys true))
                     children))
            (decorate hoc)))))
  ([styles component] ((with-styles styles) component)))

;;;; Color Manipulators
;;
;; https://github.com/mui-org/material-ui/blob/master/packages/material-ui/src/styles/colorManipulator.js

(def emphasize mui-styles/emphasize)
(def fade mui-styles/fade)
