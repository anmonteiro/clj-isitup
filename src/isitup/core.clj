(ns isitup.core
  (:require [clojurewerkz.urly.core :as urly]
            [clj-http.client :as client]
            [clojure.data.json :as json])
  (:import [java.net URI URL]))


(def api "https://isitup.org/")

;; ToDo: parse URL ports correctly
;; ToDo: test URL sanitization
(defn- sanitize-url
  "Removes the \"http://\" part of the url, if any"
  [s]
  (-> (urly/url-like s)
      (urly/host-of)))

(defn- get-domain-status
  "Runs a domain check"
  [domain]
  (try
    (let [sanitized (sanitize-url domain)
          res (client/get (str api sanitized ".json"))
          parsed (json/read-str (:body res) :key-fn keyword)]
      parsed)
  (catch Exception e
    (throw (ex-info "API unreachable" (ex-data e))))))

(defn run-status
  "Runs the check for every argument in arguments against isitup's API."
  [arguments]
  (map get-domain-status arguments))
