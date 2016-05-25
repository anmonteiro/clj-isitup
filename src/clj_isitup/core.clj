(ns clj-isitup.core
  (:require [clojurewerkz.urly.core :as urly]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.spec :as s])
  (:import [java.net URI URL]))

(s/def ::nil-or-str? #(or (nil? %) (string? %)))


(def api "https://isitup.org/")

(s/fdef sanitize-url
  :args (s/cat :s string?)
  :ret  ::nil-or-str?)
;; ToDo: parse URL ports correctly
(defn- sanitize-url
  "Removes the \"http://\" part of the url, if any"
  [s]
  (-> (urly/url-like s)
      (urly/host-of)))

(defn- run-status* [domain]
  (let [sanitized (sanitize-url domain)]
    (when (nil? sanitized)
      (throw (ex-info "Malformed domain.")))
    (try
      (-> (client/get (str api sanitized ".json"))
          :body
          (json/read-str :key-fn keyword))
      (catch Exception e
        (throw (ex-info "API unreachable" (ex-data e)))))))

(defn run-status
  "Runs a domain check"
  [domain]
  (if (string? domain)
    (run-status* domain)
    (throw (ex-info (str "Expected java.lang.String as the domain, got "
                         (type domain))
                    {}))))
