(defproject the-decider "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljsbuild "0.2.9"]
            [lein-ring "0.7.5"]]
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [jayq "0.3.0"]
                 [crate "0.2.1"]
                 [compojure "1.1.3"]]
  :cljsbuild {:builds [{:source-path "src/cljs"
                        :compiler {:output-to "resources/public/main.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]
              :crossovers [the-decider.math]
              :crossovers-path "src/cljs"}
  :source-paths ["src/clj"]
  :ring {:handler the-decider.core/app})
