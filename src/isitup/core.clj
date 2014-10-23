(ns isitup.core
  (:require [clj-http.client :as client]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.data.json :as json])
  (:gen-class :main true))


(def api "http://isitup.org/")

(def cli-options
  [["-h" "--help" "Show help" :flag true :default false]
   ["-v" "--version" "Show isitup-cli's version" :flag true :default false]])

(defn- sanitize-url
  "Removes the \"http://\" part of the url, if any"
  [url]
  (last (re-matches #"(http://)*(.*)" url)))

(defn run-status
  "Runs the check for every argument in arguments against isitup's API.
  Response codes include the following (from the isitup API Docs -
  https://isitup.org/api/api.html):
  1 - Website is alive.
  2 - Website appears down.
  3 - Domain was not valid."
  [arguments]
  (doseq [domain arguments]
    (let [sanitized (sanitize-url domain)
          res (client/get (str api sanitized ".json"))
          parsed (json/read-str (:body res))
          status (get parsed "status_code")]
      (case status
        1 (println (str "✔ Up: " sanitized))
        2 (println (str "✖ Down: " sanitized))
        3 (println (str "⚠ Invalid Domain: " sanitized))))))

(defn -main
  [& args]
  (let [{:keys [options arguments summary]} (parse-opts args cli-options)]
    (cond
      (:help options)
      (do
        (println "~~ IsItUp-CLI ~~")
        (println "Usage: isitup [-v] [-h] domain") 
        (println summary))
      
      (:version options)
      (println (str "isitup-cli's version: " (System/getProperty "isitup.version")))
      
      (>= (count arguments) 1)
      (run-status arguments))
    (System/exit 0)))
