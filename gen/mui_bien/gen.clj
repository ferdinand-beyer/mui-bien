(ns mui-bien.gen
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [clojure.string :as str]
            [camel-snake-kebab.core :as csk]))

(def ^:dynamic *out-dir* "build/src")
(def ^:dynamic *verbose* false)

(defn clashes [symbols]
  (filter (ns-interns 'clojure.core) symbols))

(defn ns->filename [ns]
  (-> (name ns) (str/replace \- \_) (str/replace \. \/) (str ".cljs")))

(defn names [package module]
  (let [clj-name (csk/->kebab-case module)
        ns (symbol (str "mui-bien." package "." clj-name))]
    {:package package
     :module module
     :ns ns
     :clj-name clj-name
     :js-name (str "@material-ui/" package "/" module)}))

(defn module-forms [{:keys [ns clj-name js-name module]}]
  [(list 'ns ns
         (list :refer-clojure :exclude (vec (clashes [clj-name])))
         (list :require '[reagent.core :as r] [js-name :as module]))
   (list 'def clj-name (list 'r/adapt-react-class (symbol (name module) "default")))])

(defn write-clj-file [ns forms]
  (let [filename (str *out-dir* "/" (ns->filename ns))]
    (io/make-parents filename)
    (with-open [writer (io/writer filename)]
      (println "Writing:" filename)
      (doseq [form forms]
        (pprint form writer)))))

(defn package-forms [ns mod-names]
  (concat
   [(list 'ns ns
          (list :refer-clojure :exclude (vec (clashes (map :clj-name mod-names))))
          (conj (map :ns mod-names) :require))]
   (for [{:keys [ns clj-name]} mod-names]
     (list 'def clj-name (symbol (name ns) (name clj-name))))))

(defn generate-module [{:keys [ns] :as names}]
  (write-clj-file ns (module-forms names)))

(defn generate-package [package mod-names]
  (let [ns (symbol (str "mui-bien." package ".all"))]
    (write-clj-file ns (package-forms ns mod-names))))

(defn generate-modules [modules]
  (doseq [[package [gen-mods provided-mods]] modules]
    (let [gen-mod-names
          (for [module gen-mods]
            (let [names (names package module)]
              (generate-module names)
              names))

          mod-names (->> provided-mods
                         (map (partial names package))
                         (concat gen-mod-names)
                         (sort-by :clj-name))]
      (generate-package package mod-names))))

(defn generate
  ([] (generate nil))
  ([{:keys [modules-file out-dir verbose]
     :or {modules-file "modules.edn"
          out-dir "build/src"
          verbose true}}]
   (binding [*out-dir* out-dir
             *verbose* verbose]
     (generate-modules
      (with-open [reader (io/reader modules-file)]
        (edn/read (java.io.PushbackReader. reader)))))))
