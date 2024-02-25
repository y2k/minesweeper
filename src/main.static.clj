(ns main.static (:require [main :as app] [utils :as u]))

(defn- html []
  [:html
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Игра \"Сапер\""]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:style ":root { --color-text: #999; --color-tile: #30c9bc; --color-tile-opened: #f8f8f8; --color-bg: #ffffff; } @media (prefers-color-scheme: dark) { :root { --color-text: #999; --color-tile: #2a8278; --color-tile-opened: #202020; --color-bg: #121212; } }"]]
   [:body {:style "background-color: var(--color-bg); user-select: none; -webkit-user-select: none; margin: 0px;"}
    [:div {:id :container} (app/view (deref app.state))]
    [:script {:src "main.js" :type :module}]
    [:script {:type :module} "import { dispatch } from './main.js'; dispatch(null, \"loaded\")"]]])

(println (u/html_to_string (html)))
