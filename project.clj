(defproject isitup "0.3.0"
  :description "isitup.org API command line consumer"
  :url "https://github.com/anmonteiro/isitup-cli"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.cli "0.3.1"]
                 ;; need to use a SNAPSHOT version because of
                 ;; https://github.com/dakrone/clj-http/issues/275
                 [clj-http "3.0.0-SNAPSHOT"]
                 [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot isitup.core
  :target-path "target/%s"
  :clean-targets [:target-path]
  :profiles {:uberjar {:aot :all}})
