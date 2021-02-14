(defproject org.clojars.fbeyer/mui-bien "0.1.0-SNAPSHOT"
  :description "Yet another integration library for Material UI and Reagent"
  :url "https://github.com/ferdinand-beyer/mui-bien"
  :licence {:name "MIT License"}
  :dependencies [[org.clojure/clojure "1.10.2"]
                 [org.clojure/clojurescript "1.10.773"]
                 [reagent "1.0.0"]
                 ;; These provide deps.cljs with :foreign-lib keys.
                 ;; They will be ignored for shadow-cljs, which reads the
                 ;; :npm-deps from src/deps.cljs
                 [cljsjs/material-ui "4.11.0-0"]
                 [cljsjs/material-ui-icons "4.4.1-0"]
                 [cljsjs/material-ui-lab "4.0.0-alpha.49-0"]])