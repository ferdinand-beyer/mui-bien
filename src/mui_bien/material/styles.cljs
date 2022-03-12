(ns mui-bien.material.styles
  (:require [reagent.core :as r]
            [mui-bien.interop :refer [map->obj]]
            ["@mui/material/styles" :as styles]))

;; https://mui.com/customization/theming/

(def theme-provider (r/adapt-react-class styles/ThemeProvider))

(defn create-theme
  "Creates a theme from one or more options maps.  Returns a JavaScript
   object suitable to be passed to a theme-provider, as this is the
   most typical use-case."
  [opts & args]
  (apply styles/createTheme (map->obj opts) (map map->obj args)))
