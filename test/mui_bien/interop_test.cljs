(ns mui-bien.interop-test
  (:require [cljs.test :refer-macros [deftest is]]
            [mui-bien.interop :as interop]))

(def map-obj-pairs
  [[{}
    #js {}]
   
   [{:prop 1}
    #js {:prop 1}]
   
   [{:border-color "green"}
    #js {:borderColor "green"}]

   [{:arr [{:nested-attr {:even-deeper 1}}]}
    #js {:arr #js [#js {:nestedAttr #js {:evenDeeper 1}}]}]
   ])

(deftest test-map->obj
  (doseq [[map obj] map-obj-pairs]
    (is (= (js->clj obj) (js->clj (interop/map->obj map))))))

(deftest test-obj->map
  (doseq [[map obj] map-obj-pairs]
    (is (= map (interop/obj->map obj)))))
