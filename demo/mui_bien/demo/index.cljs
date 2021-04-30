(ns mui-bien.demo.index
  (:require [mui-bien.core.app-bar :refer [app-bar]]
            [mui-bien.core.button :refer [button]]
            [mui-bien.core.icon-button :refer [icon-button]]
            [mui-bien.core.toolbar :refer [toolbar]]
            [mui-bien.core.typography :refer [typography]]
            [mui-bien.icons.menu :rename {menu menu-icon}]))

(defn my-app-bar []
  [app-bar {:position :static}
   [toolbar
    [icon-button {:edge :start
                  :color :inherit}
     [menu-icon]]
    [typography {:variant :h6
                 :style {:flex-grow 1}}
     "Demo"]
    [button {:color :inherit} "Login"]]])

(defn index-page []
  [my-app-bar])
