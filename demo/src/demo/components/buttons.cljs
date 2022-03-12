(ns demo.components.buttons
  (:require [mui-bien.material.button :refer [button]]
            [mui-bien.material.stack :refer [stack]]))

(defn- basic-button []
  [:section
   [:h2 "Basic button"]
   [stack {:direction :row, :spacing 2}
    [button {:variant :text} "Text"]
    [button {:variant :contained} "Contained"]
    [button {:variant :outlined} "Outlined"]]])

(defn screen []
  [:main
   [:h1 "Buttons"]
   [basic-button]])
