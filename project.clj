(defproject hello-cljs "0.0.1-SNAPSHOT"
  :url "http://github.com/rasmuserik/dp"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2156"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [hiccups "0.3.0"]]
  :plugins [[lein-cljsbuild "1.0.2"]]
  :source-paths ["src"]
  :cljsbuild
  { :builds
    [ { :id "dp"
        :source-paths ["src"]
        :compiler
        { :output-to "target/plain/main.js"
          :output-dir "target/plain"
          :optimizations :none
          :source-map true} }
      { :id "dp-optim"
        :source-paths ["src"]
        :compiler
        { :output-to "target/opt/main.js"
          :output-dir "target/opt"
          :optimizations :advanced
          :pretty-print false}}]})
