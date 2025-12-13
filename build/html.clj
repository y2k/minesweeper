(ns _ (:require ["$LY2K_PACKAGES_DIR/xml/0.2.0/main" :as xml]))

(xml/to_string
 [:html
  [:head
   [:meta {:charset "UTF-8"}]
   [:title "Игра \"Сапер\""]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   [:style ":root { --color-text: #999; --color-tile: #30c9bc; --color-tile-opened: #f8f8f8; --color-bg: #ffffff; } @media (prefers-color-scheme: dark) { :root { --color-text: #999; --color-tile: #2a8278; --color-tile-opened: #202020; --color-bg: #121212; } }"]]
  [:body {:style "background-color: var(--color-bg); user-select: none; -webkit-user-select: none; margin: 0px;"}
   [:div {:id :container}]
   [:script {:src "src/main.js" :type :module}]
   [:script {:type :module} "import { dispatch } from './src/main.js'; dispatch(null, 'loaded')"]]])
