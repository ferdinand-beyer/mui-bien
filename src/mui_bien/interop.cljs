(ns mui-bien.interop
  (:require [camel-snake-kebab.core :refer [->kebab-case-keyword ->camelCaseString]]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [reagent.core :as r]))

(defn decorate
  "DEPRECATED.  Use hooks instead.
   
   Decorate a Reagent component using the higher-order component (HOC) pattern.
   `hoc` needs to be a JavaScript function."
  [hoc component]
  ;; FIXME component will now receive props in camelCase...
  (-> component r/reactify-component hoc r/adapt-react-class))

(defn ->obj-key
  "Transforms a Clojure key for a JavaScript object."
  [k]
  (if (keyword? k)
    (->camelCaseString k)
    k))

(defn ->map-key
  "Transforms a JavaScript key for a Clojure map."
  [k]
  (if (and (string? k)
           (re-matches #"[A-Za-z]\w*" k))
    (->kebab-case-keyword k)
    k))

(defn map->obj
  "Transforms a Clojure Map to a JavaScript object."
  [m]
  (clj->js (transform-keys ->obj-key m)))

(defn obj->map
  "Transforms a JavaScript object to a Clojure map."
  [obj]
  (transform-keys ->map-key (js->clj obj)))

(defn obj->map->obj
  "Returns a function that takes a JavaScript object, calls f
   with that object converted to a map, then converts the result
   back to a JavaScript object."
  [f]
  (fn [obj]
    (-> (obj->map obj) (f) (map->obj))))

(defn wrap-element-props
  "Wraps props that require element values with as-element, according
   to (pred k).  One common idiom is to use a set as pred, e.g. to
   map a known set of 'node' properties."
  [props pred]
  (reduce (fn [props [k v]]
            (if (pred k)
              (assoc props k (r/as-element v))
              props))
          {}
          props))
