(ns demo.components.text-fields
  (:require [mui-bien.material.text-field :refer [text-field]]
            [mui-bien.material.box :refer [box]]))

(defn- form-box [& children]
  (into [box {:component :form
              :no-validate true
              :auto-complete :off
              :sx {"& .MuiTextField-root" {:m 1
                                           :width "25ch"}}}]
        children))

(defn- basic-text-field []
  [:section
   [:h2 "Basic text field"]
   [form-box
    [text-field {:variant :outlined, :label "Outlined"}]
    [text-field {:variant :filled, :label "Filled"}]
    [text-field {:variant :standard, :label "Standard"}]]])

(defn- form-props []
  [:section
   [:h2 "Form props"]
   [form-box
    [text-field {:required true
                 :label "Required"
                 :default-value "Hello world"}]
    [text-field {:disabled true
                 :label "Disabled"
                 :default-value "Hello world"}]
    [text-field {:type :password
                 :label "Password"
                 :default-value "Hello world"
                 :auto-complete :current-password}]
    [text-field {:label "Read Only"
                 :default-value "Hello world"
                 :Input-Props {:read-only true}}]
    [text-field {:label "Number"
                 :type :number
                 :Input-Label-Props {:shrink true}}]
    [text-field {:label "Search Field"
                 :type :search}]
    [text-field {:label "Helper Text"
                 :default-value "Default value"
                 :helper-text "Some important text"}]]])

(defn- validation []
  [:section
   [:h2 "Validation"]
   [form-box
    [text-field {:error true
                 :label "Error"
                 :default-value "Hello World"}]
    [text-field {:error true
                 :label "Error"
                 :default-value "Hello World"
                 :helper-text "Incorrect entry."}]]])

(defn screen []
  [:main
   [:h1 "Text fields"]
   [basic-text-field]
   [form-props]
   [validation]])
