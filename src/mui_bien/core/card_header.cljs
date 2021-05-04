(ns mui-bien.core.card-header
  (:require [reagent.core :as r]
            [mui-bien.interop :refer [wrap-element-props]]
            ["@material-ui/core/CardHeader" :as CardHeader]))

(def element-props #{:action :avatar :subheader :title})

(def -card-header (r/adapt-react-class CardHeader/default))

(defn card-header [props]
  [-card-header (wrap-element-props props element-props)])
