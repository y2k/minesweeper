(ns main (:require [utils :as u]))
;; Domain

;; -1    : неизвестно
;; -2    : мина
;; -3    : мина видимая (game over)
;; -4    : флажок поверх мины
;; -5    : флажок поверх пустого места
;; 0 - 8 : открытое пустое пространство

(def FIELD_WIDTH 6)
(def FIELD_SIZE (* FIELD_WIDTH FIELD_WIDTH))

(defn- loaded [cofx]
  (let [x (/ cofx.now 1000)
        seed (- x (Math/floor x))]
    {:field
     (u/unfold seed
               (fn [prev i]
                 (if (< i FIELD_SIZE) (let [r (u/random prev)] [(if (> (* 5 r) 1) -1 -2) r]) null)))}))

(defn- get_at [field index dx dy]
  (let [x (+ dx (% index FIELD_WIDTH))
        y (+ dy (Math/floor (/ index FIELD_WIDTH)))]
    (if (or (< x 0) (< y 0) (>= x FIELD_WIDTH) (>= y FIELD_WIDTH))
      null
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

(defn- clicked [cofx index]
  (case (.at cofx.db.field index)
    -2 (do (alert "Game over!")
           (assoc cofx.db :field (.map cofx.db.field (fn [x] (if (= x -2) -3 x)))))
    -1 (assoc cofx.db :field (open_cell cofx.db.field index))
    -5 (assoc cofx.db :field (.map cofx.db.field (fn [x i] (if (= i index) -1 x))))
    -4 (assoc cofx.db :field (.map cofx.db.field (fn [x i] (if (= i index) -2 x))))
    cofx.db))

(defn- mini_flag_clicked [cofx index]
  (case (.at cofx.db.field index)
    -2 (assoc cofx.db :field (.map cofx.db.field (fn [x i] (if (= i index) -4 x))))
    (assoc cofx.db :field (.map cofx.db.field (fn [x i] (if (= i index) -5 x))))))

;; View

(defn view [state]
  (concat
   [:div {:style (str "height: 100%; display: grid; grid-template-columns: repeat(" FIELD_WIDTH ", 1fr); grid-template-rows: repeat(" FIELD_WIDTH ", 1fr); gap: 10px;")}]
   (.map state.field
         (fn [x i]
           [:div {:style (str "display: flex; align-items: center; justify-content: center; width: 100%; height: 100%; position: relative; cursor: default; border-radius: 1vw; background-color: " (if (or (= x -1) (= x -2) (= x -4) (= x -5)) "#30c9bc" "white"))
                  :onclick (str "dispatch(event, \"clicked\", " i ")")}
            [:div {:style "font-size: 6vw; color: #999;"}
             (if (or (= x -1) (= x -2) (= x 0)) ""
                 (if (= x -3) "💣"
                     (if (or (= x -4) (= x -5)) "🚩" (str x))))]
            (if (or (= x -1) (= x -2))
              [:div {:style (str "font-size: 3vw; border-radius: 1vw; background-color: #11111108; display: flex; align-items: center; justify-content: center; position: absolute; left: 50%; top: 0px; width: 50%; height: 50%; cursor: default;")
                     :onclick (str "dispatch(event, \"mini_flag_clicked\", " i ")")}
               "❌"]
              [:div])]))))

;; Infrastructure

(^export def state (atom {:field (u/unfold 0 (fn [_ i] (if (< i FIELD_SIZE) [-1 0] null)))}))

(defn dispatch [e action payload]
  (if (= null e) null (.stopPropagation e))
  (reset state
         (case action
           :loaded (do
                     (set! (.-dispatch window) dispatch)
                     (loaded {:now (Date/now) :db (deref state)}))
           :clicked (clicked {:db (deref state)} payload)
           :mini_flag_clicked (mini_flag_clicked {:db (deref state)} payload)
           (deref state)))
  (set! (.-innerHTML (.querySelector document "#container"))
        (u/html_to_string (view (deref state)))))
