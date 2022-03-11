(ns mui-bien.button-cards
  (:require [reagent.core]
            [devcards.core :as dc]
            ["@mui/material/Button" :as Button]
            ["@mui/material/Stack" :as Stack]))

(dc/defcard-doc
  "# Markdown
  It's very easy to make some words **bold** and other words *italic* with Markdown.
  You can even [link to shadow-cljs!](https://github.com/thheller/shadow-cljs)")

(dc/defcard-rg basic-button
  [:> Stack/default {:direction :row, :spacing 2}
   [:> Button/default {:variant :text} "Text"]
   [:> Button/default {:variant :contained} "Contained"]
   [:> Button/default {:variant :outlined} "Outlined"]])

(dc/defcard-rg text-button
  [:> Stack/default {:direction :row, :spacing 2}
   [:> Button/default "Primary"]
   [:> Button/default {:disabled true} "Disabled"]
   [:> Button/default {:href "#!/mui_bien.button_cards/text-button"} "Link"]])
