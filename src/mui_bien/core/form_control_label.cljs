(ns mui-bien.core.form-control-label
  (:require [reagent.core :as r]
            ["@material-ui/core/FormControlLabel" :as FormControlLabel]))

(def -form-control-label (r/adapt-react-class FormControlLabel/default))

(defn form-control-label [{:keys [control] :as props}]
  [-form-control-label
   (assoc props :control (r/as-element control))])
