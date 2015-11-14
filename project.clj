(defproject clj-isitup "0.6.0-SNAPSHOT"
  :description "isitup.org API client and command line tool"
  :url "https://github.com/anmonteiro/clj-isitup"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0" :scope "provided"]
                 [org.clojure/tools.cli "0.3.3"]
                 ;; need to use a SNAPSHOT version because of
                 ;; https://github.com/dakrone/clj-http/issues/275
                 [clj-http "3.0.0-SNAPSHOT"]
                 [clojurewerkz/urly "1.0.0"]
                 [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot clj-isitup.cli
  :target-path "target/%s"
  :clean-targets [:target-path]
  :profiles {:uberjar {:aot :all}})
