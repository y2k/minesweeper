;; -2    : мина
;; -1    : неизвестно
;; 0 - 8 : открытое пустое пространство
(^export def state (atom {:field [-1 -1 -1 -1 -1  -1 -1 -1 -1 -1  -1 -1 -1 -1 -1  -1 -1 -1 -1 -1  -1 -1 -1 -1 -1]}))

(defn view [state]
  (concat
   [:div {:style "height: 100%; display: grid; grid-template-columns: repeat(5, 1fr); grid-template-rows: repeat(5, 1fr); gap: 10px;"}]
   (.map state.field
         (fn [x i]
           [:div {:style (str "background-color: " (if (or (= x -1) (= x -2)) "#30c9bc" "white") "; font-size: 10vw; display: flex; align-items: center; justify-content: center")
                  :onclick (str "dispatch(\"clicked\", " i ")")}
            (if (or (= x -1) (= x -2)) "" (str x))]))))

(defn- clicked [db index]
  (alert index)
  db)

(defn- loaded []
  {:field [2 -1 -1 -1 -1  -1 -2 -1 -1 -1  -1 -1 -2 -1 -1  -1 -1 -1 -2 -1  -1 -1 -1 -1 -2]})

;; Infrastructure

(defn html_to_string [node]
  (let [tag (.at node 0)
        attrs (.at node 1)
        has_attrs (and (> node.length 1) (= (type (.at node 1)) "object") (not (Array/isArray (.at node 1))))]
    (if (= (type node) :string)
      node
      (str "<" tag " "
           (if (not has_attrs) ""
               (->
                (Object/entries attrs)
                (.reduce (fn [a x] (str a " " (.at x 0) "='" (.at x 1) "'")) ""))) ">"
           (->
            (.slice node (if has_attrs 2 1))
            (.map html_to_string)
            (.reduce (fn [a x] (str a x)) ""))
           "</" tag ">"))))

(defn dispatch [action payload]
  (reset state
         (case action
           :loaded (do
                     (set! (.-dispatch window) dispatch)
                     (loaded (deref state)))
           :clicked (clicked (deref state) payload)
           (deref state)))
  (set! (.-innerHTML (.querySelector document "#container"))
        (html_to_string (view (deref state)))))
