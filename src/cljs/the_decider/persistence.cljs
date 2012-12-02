(ns the-decider.persistence
  (:require [cljs.reader :refer [read-string]]
            [clojure.string :as s])
  (:require-macros [jayq.macros :as $m]))

(defn- read-query-parameter
  [name]
  (if-let [[_ val] (re-find (re-pattern (str "[\\?&]" name "=([^&#]*)"))
                          (-> js/window
                              .-location
                              .-search))]
    (-> val
        (s/replace "+" " ")
        (js/decodeURIComponent))))

(defn- link-here-with-params
  [params]
  (let [location (.-location js/window)
        href (.-href location)
        search (.-search location)
        wo-query (subs href 0 (- (count search) (count href)))]
    (->> params
         (map (fn [[k v]]
                (format "%s=%s" (name k) (js/encodeURIComponent (pr-str v)))))
         (s/join "&")
         (str wo-query "?"))))

(defn load-decision
  [callback default]
  (if-let [id (read-query-parameter "id")]
    ($m/let-ajax [edn {:url (str "decisions/" id)}]
                 (-> edn read-string callback))
    (callback default)))

(defn save-decision
  [decision]
  ($m/let-ajax [id {:method "POST", :data (pr-str decision)}]
               (set! (.-location js/window) (link-here-with-params {:id id}))))
