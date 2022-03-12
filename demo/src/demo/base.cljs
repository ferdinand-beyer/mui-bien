(ns demo.base
  (:require [mui-bien.icons-material.menu :refer [menu-icon]]
            [mui-bien.material.app-bar :refer [app-bar]]
            [mui-bien.material.box :refer [box]]
            [mui-bien.material.container :refer [container]]
            [mui-bien.material.css-baseline :refer [css-baseline]]
            [mui-bien.material.drawer :refer [drawer]]
            [mui-bien.material.icon-button :refer [icon-button]]
            [mui-bien.material.link :refer [link]]
            [mui-bien.material.list :refer [list]]
            [mui-bien.material.list-item-button :refer [list-item-button]]
            [mui-bien.material.list-item-text :refer [list-item-text]]
            [mui-bien.material.list-subheader :refer [list-subheader]]
            [mui-bien.material.styles :refer [create-theme theme-provider]]
            [mui-bien.material.toolbar :refer [toolbar]]
            [demo.router :as router]
            [reagent.core :as r]))

(def theme (create-theme {}))

(defn- demo-app-bar [{:keys [on-open-menu]}]
  [:<>
   [app-bar
    [toolbar
     [icon-button {:size :large
                   :edge :start
                   :color :inherit
                   :aria-label "menu"
                   :sx {:mr 2}
                   :on-click on-open-menu}
      [menu-icon]]
     [link {:href "#/"
            :variant :h6
            :color :inherit
            :underline :none}
      "mui-bien"]]]
   [toolbar]])

(defn- menu-subheader [text]
  [list-subheader {:component :div}
   text])

(defn- menu-item [name text]
  [list-item-button {:component :a
                     :href (router/href name)
                     :selected (= name (-> @router/match :data :name))}
   [list-item-text {:primary text}]])

(defn- menu []
  [list {:component :nav
         :dense true}
   [menu-subheader "Inputs"]
   [menu-item :autocomplete "Autocomplete"]
   [menu-item :buttons "Button"]
   [menu-item :text-fields "Text Field"]])

(defn- menu-drawer [{:keys [open? :on-close]}]
  [drawer {:anchor :left
           :open (boolean open?)
           :on-close on-close}
   [box {:sx {:width 250}
         :on-click on-close}
    [link {:href "#/"
           :variant :h6
           :underline :none
           :sx {:p 2}}
     "mui-bien"]
    [menu]]])

(defn- demo-layout [content]
  (r/with-let [drawer-open?   (r/atom false)
               toggle-drawer! (fn [_] (swap! drawer-open? not) nil)]
    [:<>
     [menu-drawer {:open? @drawer-open?
                   :on-close toggle-drawer!}]
     [demo-app-bar {:on-open-menu toggle-drawer!}]
     [container content]]))

(defn root [content]
  [theme-provider {:theme theme}
   [css-baseline]
   [demo-layout content]])
