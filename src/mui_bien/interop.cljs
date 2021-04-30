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

(defn map->obj [m]
  (clj->js (transform-keys ->camelCaseString m)))

(defn obj->map [obj]
  (transform-keys ->kebab-case-keyword (js->clj obj)))
