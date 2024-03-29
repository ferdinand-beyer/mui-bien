(ns mui-bien.gen
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [clojure.string :as str]
            [camel-snake-kebab.core :as csk]))

(def ^:dynamic *src-dir*)
(def ^:dynamic *out-dir*)
(def ^:dynamic *verbosity*)

(defn log [min-verbosity & args]
  (when (>= *verbosity* min-verbosity)
    (apply println args)))

(defn clashes [symbols]
  (filter (ns-interns 'clojure.core) symbols))

(defn ns->filename [ns]
  (-> (name ns) (str/replace \- \_) (str/replace \. \/) (str ".cljs")))

(defn names [package module {:keys [var-format]}]
  (let [clj-name (csk/->kebab-case module)
        ns (symbol (str "mui-bien." package "." clj-name))]
    {:package package
     :module module
     :ns ns
     :clj-name clj-name
     :var-name (if (some? var-format)
                 (symbol (format var-format clj-name))
                 clj-name)
     :import-name (str "@material-ui/" package "/" module)}))

(defn module-forms [{:keys [module ns var-name import-name]}]
  [(list 'ns ns
         (list :refer-clojure :exclude (vec (clashes [var-name])))
         (list :require '[reagent.core :as r] [import-name :as module]))
   (list 'def var-name (list 'r/adapt-react-class (symbol (name module) "default")))])

(defn package-forms [ns mod-names]
  (concat
   [(list 'ns ns
          (list :refer-clojure :exclude (vec (clashes (map :var-name mod-names))))
          (conj (map :ns mod-names) :require))]
   (for [{:keys [ns var-name]} mod-names]
     (list 'def var-name (symbol (name ns) (name var-name))))))

(defn write-clj-file [ns forms-fn]
  (let [base-filename (ns->filename ns)
        src-filename (str *src-dir* "/" base-filename)]
    (if (.exists (io/file src-filename))
      (log 2 "Exists:" src-filename)
      (let [filename (str *out-dir* "/" base-filename)
            forms (forms-fn)]
        (io/make-parents filename)
        (log 3 "Writing:" filename)
        (with-open [writer (io/writer filename)]
          (doseq [form forms]
            (pprint form writer)))))))

(defn generate-module [{:keys [ns] :as names}]
  (write-clj-file ns #(module-forms names)))

(defn generate-package [package mod-names]
  (let [ns (symbol (str "mui-bien." package ".all"))]
    (write-clj-file ns #(package-forms ns mod-names))))

(defn generate-packages [packages]
  (doseq [{:keys [package components all-ns?]
           :or {all-ns? true}
           :as opts} packages]
    (log 2 "Generating code for package:" package)
    (let [mod-names (doall (for [component components]
                             (let [names (names package component opts)]
                               (generate-module names)
                               names)))]
      (when all-ns?
        (log 2 "Generating 'all' namespace for package:" package)
        (generate-package package mod-names)))))

(defn generate
  ([] (generate nil))
  ([{:keys [data-file src-dir out-dir verbosity]
     :or {data-file "mui.edn"
          src-dir "src"
          out-dir "build/src"
          verbosity 2}}]
   (binding [*src-dir* src-dir
             *out-dir* out-dir
             *verbosity* verbosity]
     (log 2 "Reading:" data-file)
     (generate-packages
      (with-open [reader (io/reader data-file)]
        (edn/read (java.io.PushbackReader. reader)))))))
