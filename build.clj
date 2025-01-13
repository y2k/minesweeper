(ns _ (:require ["vendor/make/0.1.0/main" :as b]))

(b/generate
 [(b/module-files
   {:target "repl"
    :rules [{:src "src/main.static.clj" :target ".github/bin/index.html"}]})
  (b/module
   {:lang "js"
    :src-dir "src"
    :target-dir ".github/bin/src"
    :items ["main" "utils"]})])
