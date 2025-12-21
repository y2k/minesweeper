(ns main (:require ["./utils" :as u]))

;; Domain


;; -1    : неизвестно
;; -2    : мина
;; -3    : мина видимая (game over)
;; -4    : флажок поверх мины
;; -5    : флажок поверх пустого места
;; 0 - 8 : открытое пустое пространство

(def CELL_MINE -2)

(def BOMB_COUNT 7)
(def FIELD_WIDTH 6)
(def FIELD_SIZE (* FIELD_WIDTH FIELD_WIDTH))

(defn- make_bomb_indecies [seed init_index]
  (defn- loop [xs]
    (defn- find_free_index [i]
      (if (or (.includes xs i) (= i init_index))
        (find_free_index (% (+ i 1) FIELD_SIZE))
        i))
    (if (>= xs.length BOMB_COUNT)
      xs
      (let [prev (.at xs -1)
            next (find_free_index
                  (% (+ prev
                        (Math.floor (* (- FIELD_SIZE xs.length)
                                       (u/random (/ prev FIELD_SIZE)))))
                     FIELD_SIZE))]
        (.push xs next)
        (loop xs))))
  (loop [(Math.floor (* FIELD_SIZE (u/random seed)))]))

(defn loaded [cofx init_index]
  (let [x (/ cofx.now 1000)
        seed (- x (Math.floor x))
        bomb_indecies (make_bomb_indecies seed init_index)]
    {:initialized true
     :field (u/unfold
             seed
             (fn [_ i]
               (if (< i FIELD_SIZE)
                 [(if (.includes bomb_indecies i) -2 -1) 0]
                 nil)))}))

(defn- get_at [field index dx dy]
  (let [x (+ dx (% index FIELD_WIDTH))
        y (+ dy (Math.floor (/ index FIELD_WIDTH)))]
    (if (or (< x 0) (< y 0) (>= x FIELD_WIDTH) (>= y FIELD_WIDTH))
      nil
      (get field (+ x (* y FIELD_WIDTH))))))

(defn- compute_count [field index]
  (defn- get_mine [dx dy]
    (let [m (get_at field index dx dy)]
      (if (or (= -2 m) (= -4 m)) 1 0)))
  (+
   (get_mine -1 -1)
   (get_mine  0 -1)
   (get_mine  1 -1)
   (get_mine -1  0)
   (get_mine  1  0)
   (get_mine -1  1)
   (get_mine  0  1)
   (get_mine  1  1)))

(defn open_cell [field index]
  (.map field (fn [x i]
                (if (= i index)
                  (compute_count field index)
                  x))))

(defn clicked [cofx index]
  (let [db (if cofx.db.initialized cofx.db (loaded cofx index))]
    (case (.at db.field index)
      -2 (do ;;(alert "Game over!")
           (assoc db :field (.map db.field (fn [x] (if (= x -2) -3 x)))))
      -1 (assoc db :field (open_cell db.field index))
      db)))

(defn- mini_flag_clicked [cofx index]
  (defn- update [v]
    (assoc cofx.db :field (.map cofx.db.field (fn [x i] (if (= i index) v x)))))
  (case (.at cofx.db.field index)
    -1 (update -5)
    -5 (update -1)
    -2 (update -4)
    -4 (update -2)
    cofx.db))

;; View

(defn view [state]
  [:div {:style "width: 100%; height: 100%; display: flex; align-items: center; justify-content: center;"}
   (concat
    [:div {:style (str "width: 100vmin; height: 100vmin; display: grid; grid-template-columns: repeat(" FIELD_WIDTH ", 1fr); grid-template-rows: repeat(" FIELD_WIDTH ", 1fr); gap: 4px;")}]
    (.map state.field
          (fn [x i]
            [:div {:style (str "overflow: hidden; display: flex; align-items: center; justify-content: center; width: 100%; height: 100%; position: relative; cursor: default; border-radius: 1vw; background-color: " (if (or (= x -1) (= x -2) (= x -4) (= x -5)) "var(--color-tile)" "var(--color-tile-opened)"))
                   :onclick (str "dispatch(event, \"clicked\", " i ")")
                   :oncontextmenu (str "dispatch(event, \"oncontextmenu\", " i ")")}
             [:div {:style "font-size: 6vw; color: var(--color-text);"}
              (if (or (= x -1) (= x -2) (= x 0)) ""
                  (if (= x -3) "💣"
                      (if (or (= x -4) (= x -5)) "🚩" (str x))))]])))])

;; Infrastructure

(defn make_state []
  {:initialized false
   :field (u/unfold 0 (fn [_ i] (if (< i FIELD_SIZE) [-1 0] nil)))})

(def- state (atom (make_state)))

(defn dispatch [e action payload]
  (if (= nil e) nil (.preventDefault e))
  (reset! state
          (case action
            :loaded (do
                      (set! (.-dispatch window) dispatch)
                      (deref state))
            :clicked (clicked {:now (Date.now) :db (deref state)} payload)
            :oncontextmenu (mini_flag_clicked {:db (deref state)} payload)
            (deref state)))
  (set! (.-innerHTML (.querySelector document "#container"))
        (u/html_to_string (view (deref state)))))
