(ns demo.home
  (:require [mui-bien.material.typography :refer [typography]]))

(defn screen []
  [:div
   [typography {:variant :h1}
    "Welcome to mui-bien"]
   [:p "Use the menu to explore mui-bien components."]
   [:p "For a complete reference, visit the "
    [:a {:href "https://mui.com/"} "Official MUI site"]
    "."]])
