(ns mui-bien.material.autocomplete
  (:require [reagent.core :as r]
            [react :as react]
            [goog.object :as obj]
            [mui-bien.interop :as interop]
            [camel-snake-kebab.core :refer [->kebab-case-keyword ->camelCaseKeyword ->camelCaseString]]
            ["@mui/material/Autocomplete" :as Autocomplete]))

(def ^:private -autocomplete (r/adapt-react-class Autocomplete/default))

;; https://github.com/arttuka/reagent-material-ui/blob/master/src/core/reagent_mui/util.cljs
(def ^:private color-key? #{:A100 :A200 :A400 :A700 "A100" "A200" "A400" "A700"})
(defn ^:private numeric-string? [s]
  (and (string? s)
       (some? (re-matches #"[0-9]+" s))))
(defn ^:private pascal-case? [s]
  (and (string? s)
       (contains? #{\A \B \C \D \E \F \G \H \I \J \K \L \M \N \O \P \Q \R \S \T \U \V \W \X \Y \Z}
                  (first s))))
(defn ^:private keyword-safe? [s]
  (some? (re-matches #"[-*+!?<>='&$%#|\w]+" s)))
(defn ^:private js-key->clj [k]
  (cond
    (keyword? k) k
    (color-key? k) (keyword k)
    (numeric-string? k) (js/parseInt k)
    (keyword-safe? k) (if (pascal-case? k)
                        (keyword k)
                        (->kebab-case-keyword k))
    :else k))
(defn js->clj'
  [obj]
  (let [convert (fn convert [x]
                  (cond
                    (seq? x)
                    (doall (map convert x))

                    (map-entry? x)
                    (MapEntry. (convert (key x)) (convert (val x)) nil)

                    (coll? x)
                    (into (empty x) (map convert) x)

                    (array? x)
                    (persistent!
                     (reduce #(conj! %1 (convert %2))
                             (transient []) x))

                    (react/isValidElement x)
                    x

                    (identical? (type x) js/Object)
                    (persistent!
                     (reduce (fn [r k]
                               (if (= "ref" k)
                                 (assoc! r :ref (obj/get x k))
                                 (assoc! r (js-key->clj k) (convert (obj/get x k)))))
                             (transient {}) (js-keys x)))
                    :else x))]
    (convert obj)))

(defn autocomplete [{:keys [options render-input] :as props} & children]
  (let [props (assoc props
                     :options (clj->js options)
                     :render-input (fn [^js params]
                                     (r/as-element (render-input (js->clj' params)))))]
    (into [-autocomplete props] children)))
