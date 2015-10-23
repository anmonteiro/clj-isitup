(ns isitup.core-test
  (:require [clojure.test :refer [deftest testing is are]]
            [clj-http.client :as client]
            [isitup.core :as isup]
            [isitup.cli :as cli]))

(def mock-status {:domain "google.com"
                  :port 80
                  :status_code 1
                  :response_ip "127.0.0.1"
                  :response_code 200
                  :response_time 0.038})

(deftest api-calls
  (testing "HTTP requests"
    (with-redefs-fn
      {#'isup/get-domain-status
        (fn [domain] (assoc mock-status :domain domain))}
      (fn []
        (let [res (isup/run-status ["google.com"])]
          (is (seq? res))
          (is (= (-> res (first) :domain) "google.com")))
        (let [res (isup/run-status ["google.com" "sapo.pt"])]
          (is (seq? res))
          (is (= (-> res (second) :response_code) 200)))))
    (with-redefs-fn
      {#'client/get
        (fn [domain] {:body "{\n    \"domain\": \"google.com\",\n
                            \"port\": 80,\n    \"status_code\": 1,\n
                            \"response_ip\": \"127.0.0.1\",\n
                            \"response_code\": 200,\n
                            \"response_time\": 0.038\n}"})}
      (fn []
        (let [res (isup/get-domain-status "google.com")]
          (is (= (assoc mock-status :domain "google.com") res)))))))

(deftest cli
  (testing "API responses"
    (let [website "google.com"
          response (assoc mock-status :domain website)]
      (are [res output] (= (cli/get-output res) output)
        response (str "✔ Up: " website)
        (assoc response :status_code 2) (str "✖ Down: " website)
        (assoc response :status_code 3) (str "⚠ Invalid Domain: " website)))))