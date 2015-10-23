(ns isitup.cli
  (:require [clojure.tools.cli :refer [parse-opts]]
            [isitup.core :as isup])
  (:gen-class :main true))


(def cli-options
  [["-h" "--help" "Show help" :flag true :default false]
   ["-v" "--version" "Show isitup-cli's version" :flag true :default false]])

(defn- usage [cli-summary]
  (println "~~ IsItUp-CLI ~~")
  (println "Usage: isitup [-v] [-h] domain")
  (println cli-summary))

(defn get-output
  "Based on the API's response code, print a message to the console. Response
  codes include the following (from the API Docs - https://isitup.org/api/api.html):
  1 - Website is alive.
  2 - Website appears down.
  3 - Domain was not valid."
  [{:keys [status_code domain] :as asd}]
  (case status_code
    1 (str "✔ Up: " domain)
    2 (str "✖ Down: " domain)
    3 (str "⚠ Invalid Domain: " domain)))

(defn print-status [arguments]
  (let [status (isup/run-status arguments)]
    (doseq [res status]
      (println (get-output res)))))

(defn -main
  [& args]
  (let [{:keys [options arguments summary]} (parse-opts args cli-options)]
    (cond
      (:version options)
      (println (str "isitup-cli's version: " (System/getProperty "isitup.version")))

      (or (:help options) (= (count arguments) 0))
      (usage summary)

      (>= (count arguments) 1)
      (print-status arguments))
    (System/exit 0)))
