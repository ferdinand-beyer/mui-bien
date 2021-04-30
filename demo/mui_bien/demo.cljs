(ns mui-bien.demo
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.history.Html5History)
  (:require [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [reagent.dom :as rdom]
            [reagent.core :as r]
            
            [mui-bien.demo.index :refer [index-page]]))

(enable-console-print!)

(def app-state (r/atom {}))

(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen
     EventType/NAVIGATE
     (fn [^goog.history.Event event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")

  (defroute "/" []
    (swap! app-state assoc :page :index))

  (hook-browser-navigation!))

(defmulti current-page #(@app-state :page))

(defmethod current-page :default []
  [:h1 "Not found"])

(defmethod current-page :index []
  [index-page])

(defn render []
  (rdom/render [current-page]
               (.getElementById js/document "app")))

(defn ^:dev/after-load refresh []
  (app-routes)
  (render))

(defn ^:export init []
  (refresh))
