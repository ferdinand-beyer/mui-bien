(ns mui-bien.lab.autocomplete
  (:require [reagent.core :as r]
            [goog.object :as gobj]
            [mui-bien.core.text-field :refer [-input]]
            ["@material-ui/lab/Autocomplete" :as autocomplete]
            ["@material-ui/core/TextField" :as TextField]))

;; https://github.com/reagent-project/reagent/blob/master/examples/material-ui/src/example/core.cljs

(def ^:private -autocomplete (r/adapt-react-class autocomplete/default))

;; TODO: Handle options nicer
(defn autocomplete [props & children]
  (let [tf-props (:text-field-props props)
        render-input (fn [^js params]
                       ;; TODO: These are expected to be camel-case
                       (doseq [[k v] tf-props]
                         (gobj/set params (key->js k) (clj->js v)))
                       (set! (.-inputComponent (.-InputProps params)) -input)
                       (r/create-element TextField/default params))
        props (-> props
                  (dissoc :text-field-props)
                  (assoc :render-input render-input))]
    (into [-autocomplete props] children)))
