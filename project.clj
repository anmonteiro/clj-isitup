(defproject isitup-cli "0.1.0-SNAPSHOT"
  :description "isitup.org API command line consumer"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-alpha2"]
                 [org.clojure/tools.cli "0.3.1"]
                 [clj-http "1.0.0"]]
  :main ^:skip-aot isitup-cli.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
