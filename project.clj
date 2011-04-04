(defproject hanoi "1.0.0-SNAPSHOT"
  :description "An iterative version of the Towers of Hanoi"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]]
  :dev-dependencies [[swank-clojure "1.3.0"]
                     [com.stuartsierra/lazytest "1.1.2"]
                     [lein-autotest "1.1.0"]]
  :repositories {"stuartsierra-releases" "http://stuartsierra.com/maven2"}
  :main hanoi.core)
