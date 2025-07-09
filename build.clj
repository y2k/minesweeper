(ns _ (:require [".github/vendor/make/0.3.0/main" :as b]))

(b/generate
 [{:target "eval"
   :src "build/html.clj"
   :out ".github/bin/index.html"}
  {:target "js"
   :root "src"
   :out-dir ".github/bin/src"}
  {:target "js"
   :root "test"
   :out-dir ".github/bin/test"}
  ;;
  ;;
  ;; (b/module-files
  ;;  {:target "repl"
  ;;   :rules [{:src "src/main.static.clj" :target ".github/bin/index.html"}]})
  ;; (b/module
  ;;  {:lang "js"
  ;;   :src-dir "src"
  ;;   :target-dir ".github/bin/src"
  ;;   :items ["main" "utils"]})
  ;; (b/module
  ;;  {:lang "js"
  ;;   :src-dir "test"
  ;;   :target-dir ".github/bin/test"
  ;;   :items ["test"]})
  ;; (b/vendor
  ;;  {:lang "js"
  ;;   :target-dir ".github/bin/vendor"
  ;;   :items [{:name "edn" :version "0.1.0"}]})
  ])
