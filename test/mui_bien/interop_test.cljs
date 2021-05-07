(ns mui-bien.interop-test
  (:require [cljs.test :refer-macros [deftest is are]]
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
    (is (= (js->clj obj) (js->clj (interop/map->obj map)))))
  
  (is (= ["&:hover span"]
         (-> {"&:hover span" {}}
             (interop/map->obj)
             (js-keys)
             (js->clj)))
      "preserves string keys"))

(deftest test-obj->map
  (doseq [[map obj] map-obj-pairs]
    (is (= map (interop/obj->map obj))))
  
  (are [exp key] (= {exp {}}
                    (interop/obj->map (clj->js {key {}})))
    :lowercase "lowercase"
    :camel-case "camelCase"
    :pascal-case "PascalCase"
    :uppercase "UPPERCASE"
    :the-answer-is-42 "theAnswerIs42")
  
  (are [key] (= {key {}}
                (interop/obj->map (clj->js {key {}})))
    "42"
    "contains space"
    "nonWord-Character"
    " leadingSpace"
    "tailingSpace "))
