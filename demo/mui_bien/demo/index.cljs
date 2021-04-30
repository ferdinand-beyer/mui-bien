(ns mui-bien.demo.index
  (:require [mui-bien.core.app-bar :refer [app-bar]]
            [mui-bien.core.button :refer [button]]
            [mui-bien.core.css-baseline :refer [css-baseline]]
            [mui-bien.core.icon-button :refer [icon-button]]
            [mui-bien.core.toolbar :refer [toolbar]]
            [mui-bien.core.typography :refer [typography]]
            [mui-bien.icons.menu :rename {menu menu-icon}]

            [mui-bien.core.colors :refer [color]]
            [mui-bien.core.styles :refer [make-styles use-theme create-mui-theme theme-provider]]

            [reagent.core :as r]))

(defn my-app-bar []
  (r/with-let
    [use-styles (make-styles
                 (fn [theme]
                   {:menu-button {:margin-right (.spacing theme 2)}
                    :title {:flex-grow 1}}))]
    (let [classes (use-styles)]
      [app-bar {:position :static}
       [toolbar
        [icon-button {:edge :start
                      :class (:menu-button classes)
                      :color :inherit
                      :aria-label "menu"}
         [menu-icon]]
        [typography {:variant :h6
                     :class (:title classes)}
         "Demo"]
        [button {:color :inherit} "Login"]]])))

(defn access-theme []
  (let [theme (use-theme)]
    [typography
     (str "Theme type: " (.. theme -palette -type))]))

(def theme (create-mui-theme
            {:palette {:type :dark}}
            {:palette {:primary {:main (color :purple :500)}
                       :secondary {:main (color :green :500)}}}))

(defn index-page []
  [:<>
   [theme-provider {:theme theme}
    [css-baseline]
    [:f> my-app-bar]
    [:f> access-theme]]])
