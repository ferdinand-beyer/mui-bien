(ns demo.home
  (:require [mui-bien.material.typography :refer [typography]]))

(defn screen []
  [:div
   [typography {:variant :h1}
    "Welcome to mui-bien"]
   [:ul
    [:li "Inputs"
     [:ul
      [:li [:a {:href "#/components/buttons"} "Button"]]]]]])
