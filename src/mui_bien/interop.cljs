(ns mui-bien.interop
  (:require [reagent.core :as r]))

(defn decorate
  "DEPRECATED.  Use hooks instead.
   
   Decorate a Reagent component using the higher-order component (HOC) pattern.
   `hoc` needs to be a JavaScript function."
  [hoc component]
  ;; FIXME component will now receive props in camelCase...
  (-> component r/reactify-component hoc r/adapt-react-class))