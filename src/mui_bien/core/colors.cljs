(ns mui-bien.core.colors
  (:require ["@material-ui/core/colors" :as mui-colors]))

;;;; https://material-ui.com/customization/color/

(def colors (js->clj mui-colors :keywordize-keys true))

;; 2014 Material Design color palettes
(def red (:red colors))
(def pink (:pink colors))
(def purple (:purple colors))
(def deep-purple (:deepPurple colors))
(def indigo (:indigo colors))
(def blue (:blue colors))
(def light-blue (:lightBlue colors))
(def cyan (:cyan colors))
(def teal (:teal colors))
(def green (:green colors))
(def light-green (:lightGreen colors))
(def lime (:lime colors))
(def yellow (:yellow colors))
(def amber (:amber colors))
(def orange (:orange colors))
(def deep-orange (:deepOrange colors))
(def brown (:brown colors))
(def grey (:grey colors))
(def blue-grey (:blueGrey colors))

(defn color
  "Return a Material UI color."
  ([hue] (get colors hue))
  ([hue shade] (get-in colors [hue shade])))
