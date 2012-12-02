(ns the-decider.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]))

;; TODO: better database
(def db (atom []))

(defroutes app
  (GET "/decisions/:id" [id]
       (-> id
           (Long/parseLong)
           (@db)
           (pr-str)))
  (POST "/decisions" {body :body}
        (let [decision (read-string (slurp body))
              id (dec (count (swap! db conj decision)))]
          (str id)))
  (route/resources "/"))

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!"))
