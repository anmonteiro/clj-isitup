#!/usr/bin/env boot

(def +version+ "0.6.0-SNAPSHOT")

(set-env!
  :source-paths #{"src"}
  :dependencies '[[org.clojure/clojure "1.9.0-alpha1" :scope "provided"]
                  [org.clojure/tools.cli "0.3.5"]
                  [clj-http              "3.1.0"]
                  [clojurewerkz/urly     "1.0.0"]
                  [org.clojure/data.json "0.2.6"]
                  [org.clojure/test.check "0.9.0"]

                  [org.clojure/tools.namespace "0.2.11" :scope "test"]
                  [adzerk/bootlaces      "0.1.13" :scope "test"]
                  [adzerk/boot-test "1.1.1" :scope "test"]])

(require '[clojure.tools.namespace.repl :refer [set-refresh-dirs]])

(apply set-refresh-dirs (get-env :directories))

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

(deftask add-version-txt []
  (with-pre-wrap fs
    (let [t (tmp-dir!)]
      (spit (clojure.java.io/file t "version.txt") +version+)
      (-> fs (add-resource t) commit!))))

(deftask dist []
  (comp (add-version-txt)
        (aot :all true)
        (pom)
        (uber)
        (jar :main 'clj_isitup.cli)
        (target)))

(defn -main [& args]
  (require 'clj-isitup.cli)
  (apply (resolve 'clj-isitup.cli/-main) args))
