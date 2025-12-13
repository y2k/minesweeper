(ns _ (:require ["$LY2K_PACKAGES_DIR/make/0.4.0/main" :as m]))

(m/build
 {:rules
  [{:target "dep"
    :name "edn"
    :version "0.3.0"
    :compile_target "js"
    :out-dir ".github/bin/test"}
   {:target "eval"
    :src "build/html.clj"
    :out ".github/bin/index.html"}
   {:target "js"
    :root "src"
    :out-dir ".github/bin/src"}
   {:target "js"
    :root "src"
    :out-dir ".github/bin/test"}
   {:target "js"
    :root "test"
    :out-dir ".github/bin/test"}]})
