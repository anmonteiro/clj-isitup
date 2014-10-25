(defproject isitup "0.3.0"
  :description "isitup.org API command line consumer"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.cli "0.3.1"]
                 [clj-http "1.0.0"]
                 [org.clojure/data.json "0.2.5"]]
  :main ^:skip-aot isitup.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
