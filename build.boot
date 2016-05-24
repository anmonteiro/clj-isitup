(def +version+ "0.6.0-SNAPSHOT")

(set-env!
  :source-paths #{"src"}
  :dependencies '[[org.clojure/tools.cli "0.3.3"]
                 [clj-http              "3.1.0"]
                 [clojurewerkz/urly     "1.0.0"]
                 [org.clojure/data.json "0.2.6"]

                 [adzerk/bootlaces      "0.1.13" :scope "test"]
                 [adzerk/boot-test "1.1.1" :scope "test"]])

(task-options! pom
  {:project     'clj-isitup
   :version     +version+
   :description "isitup.org API client and command line tool"
   :url         "https://github.com/anmonteiro/clj-isitup"
   :scm         {:url "https://github.com/anmonteiro/clj-isitup"}
   :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}})

(require '[adzerk.boot-test :refer [test]])

(deftask run-tests []
  (set-env! :source-paths #(conj % "test"))
  (comp (test)))

#_(defproject clj-isitup 
  :main ^:skip-aot clj-isitup.cli
  :profiles {:uberjar {:aot :all}})
