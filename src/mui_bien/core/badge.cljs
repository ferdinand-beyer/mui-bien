(ns mui-bien.core.badge
  (:require [reagent.core :as r]
            ["@material-ui/core/Badge" :as Badge]))

(def -badge (r/adapt-react-class Badge/default))

(defn badge [{:keys [badge-content] :as props} node]
  [-badge
   (assoc props
          :badge-content (r/as-element badge-content))
   node])
