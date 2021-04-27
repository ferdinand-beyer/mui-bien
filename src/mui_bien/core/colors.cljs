(ns mui-bien.core.colors
  (:require ["@material-ui/core/colors" :as mui-colors]))

(def colors (js->clj mui-colors :keywordize-keys true))

(defn color
  "Return a Material UI color."
  ([hue] (get colors hue))
  ([hue shade] (get-in colors [hue shade])))
