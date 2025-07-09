(ns _ (:require ["../src/main" :as m]
                ["../src/utils" :as u]
                ["./vendor/edn/main" :as edn]))

(defn- assert_equal [expected actual]
  (if (= (JSON.stringify expected) (JSON.stringify actual))
    (eprintln "Test result: SUCCESS")
    (FIXME "Test result: FAILED\n"
           (edn/to_string expected)
           "\nnot=\n"
           (edn/to_string actual))))

(assert_equal
 {:initialized true
  :field [1 -2 -2 -1 -1 -1
          -1 -1 -1 -1 -1 -2
          -1 -2 -2 -1 -1 -1
          -1 -1 -1 -1 -1 -2
          -1 -1 -1 -1 -1 -2
          -1 -1 -1 -1 -1 -1]}
 (m/clicked
  {:now 1736811151123
   :db (m/make_state)}
  0))

(assert_equal
 {:initialized true
  :field [-1 1 -2 -2 -1 -1 -1 -1 -1 -1 -1 -1 -2 -2 -1 -1 -1 -1 -1 -1 -1 -1 -2 -2 -1 -1 -1 -1 -1 -2 -1 -1 -1 -1 -1 -1]}
 (m/clicked
  {:now 1736811151123
   :db (m/make_state)}
  1))

(let [x (/ 1736811151123 1000)
      seed (- x (Math.floor x))])
(assert_equal
 [0.23606797284446657
  0.23606797284446657
  0.7360679728444666
  0.6555013281758875
  0.5843499419279397
  0.32315557659603655
  0.7722014905884862
  0.9221897728275508]
 [(u/random 0)
  (u/random 1)
  (u/random 0.5)
  (u/random seed)
  (u/random (u/random seed))
  (u/random (u/random (u/random seed)))
  (u/random (u/random (u/random (u/random seed))))
  (u/random (u/random (u/random (u/random (u/random seed)))))])
