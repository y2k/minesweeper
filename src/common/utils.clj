;; Utils

(defn html_to_string [node]
  (let [tag (.at node 0)
        attrs (.at node 1)
        has_attrs (and (> node.length 1) (= (type (.at node 1)) "object") (not (Array.isArray (.at node 1))))]
    (if (= (type node) :string)
      node
      (str "<" tag " "
           (if (not has_attrs) ""
               (->
                (Object.entries attrs)
                (.reduce (fn [a x] (str a " " (.at x 0) "='" (.at x 1) "'")) ""))) ">"
           (->
            (.slice node (if has_attrs 2 1))
            (.map html_to_string)
            (.reduce (fn [a x] (str a x)) ""))
           "</" tag ">"))))

(defn random [prev]
  (let [m 4294967296]
    (/ (% (+ 1013904223 (* 1664525 (* m prev))) m) m)))

(defn unfold [seed f]
  (defn- loop [result prev i]
    (let [pair (f prev i)]
      (if (= pair null)
        result
        (let [x (.at pair 0)
              acc (.at pair 1)]
          (.push result x)
          (loop result acc (+ i 1))))))
  (loop [] seed 0))
