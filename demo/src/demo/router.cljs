(ns demo.router
  (:require [reagent.core :as r]
            [reitit.frontend :as rf]
            [reitit.frontend.controllers :as rfc]
            [reitit.frontend.easy :as rfe]))

;; https://github.com/metosin/reitit/blob/master/examples/frontend-controllers/src/frontend/core.cljs

(defonce match (r/atom nil))

(def href rfe/href)

(defn- handle-navigate [new-match]
  (swap! match (fn [old-match]
                 (when new-match
                   (assoc new-match :controllers (rfc/apply-controllers (:controllers old-match) new-match))))))

(defn start! [routes]
  (-> (rf/router routes
                 {:data {:controllers []}})
      (rfe/start! handle-navigate {:use-fragment true})))
