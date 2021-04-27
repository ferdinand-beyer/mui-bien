(ns mui-bien.core.text-field
  (:require [reagent.core :as r]
            ["@material-ui/core/TextField" :as TextField]))

(set! *warn-on-infer* true)

(defn- map-input-props [props]
  (assoc (dissoc props :inputRef) :ref (:inputRef props)))

(def -input
  (r/reactify-component
   (fn [props]
     [:input (map-input-props props)])))

(def -textarea
  (r/reactify-component
   (fn [props]
     [:textarea (map-input-props props)])))

(def -text-field (r/adapt-react-class TextField/default))

(defn text-field [props & children]
  (let [input (cond
                (and (:multiline props)
                     (:rows props)
                     (not (:maxRows props))) -textarea
                (:multiline props) nil
                (:select props) nil
                :else -input)]
    (into
     [-text-field (assoc-in props [:InputProps :inputComponent] input)]
     children)))
