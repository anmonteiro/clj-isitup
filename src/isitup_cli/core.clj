(ns isitup-cli.core
  (:require [clj-http.client :as client]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class :main true))


(def api "http://isitup.org/")

(def cli-options
  [["-h" "--help" "Show help" :flag true :default false]
   ["-v" "--version" "Show isitup-cli's version" :flag true :default false]])

(defn- sanitize-url
  "Removes the \"http://\" part of the url, if any"
  [url]
  (last (re-matches #"(http://)*(.*)" url)))

(defn run
  "Runs the check for domain against isitup's API. Response codes include the
  following (from the isitup API Docs - https://isitup.org/api/api.html):
  1 - Website is alive.
  2 - Website appears down.
  3 - Domain was not valid."
  [domain]
  (println (str api domain ".json"))
  (let [res (client/get (str api (sanitize-url domain) ".json"))]
    (println res)))

(defn -main
  [& args]
  (let [{:keys [options arguments summary]} (parse-opts args cli-options)]
    (println "options: ✔✖⚠" options)
    (println "arguments: " arguments)
    (when (:help options)
      (do (println "~~ IsItUp-CLI ~~")
        (println "Usage: ")
        (println summary)
        (System/exit 0)))
    (when (:version options)
      (do (println (System/getProperty "isitup-cli.version"))
        (System/exit 0)))
    ;; TODO: Check we actually have an argument at this point
    ;; then treat JSON response
    (run (first arguments))))
