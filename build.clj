(ns build
  (:require [clojure.tools.build.api :as b]
            [mui-bien.generate :as gen]))

(defn generate [opts]
  (gen/generate opts))
