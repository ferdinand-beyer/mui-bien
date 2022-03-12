(ns demo.core
  (:require [demo.components.buttons]
            [demo.components.text-fields]
            [demo.home]
            [demo.base :as base]
            [demo.router :as router]
            [reagent.dom :as rdom]))

(enable-console-print!)

(def routes
  ["/"
   ["" {:name :home
        :view demo.home/screen}]

   ["components"
    ["/buttons" {:name :buttons
                 :view demo.components.buttons/screen}]
    ["/text-fields" {:name :text-fields
                     :view demo.components.text-fields/screen}]]])

(defn- not-found []
  [:main
   [:h1 "Not found"]])

(defn view []
  (let [match @router/match]
    [base/root
     (if (some? match)
       (let [view (:view (:data match))]
         [view match])
       [not-found])]))

(defn render []
  (rdom/render [view]
               (.getElementById js/document "app")))

(defn ^:dev/after-load refresh []
  (router/start! routes)
  (render))

(defn ^:export init []
  (refresh))
