(ns the-decider.core
  (:require [the-decider.ui :as ui]
            [the-decider.persistence :as saved]
            [jayq.core :as $ :refer [$]]
            [cljs.reader :refer [read-string]]
            [crate.core :refer [html]])
  (:require-macros [jayq.macros :as $m]))

(def example-decision
  [{:name "Stay home."
    :outcomes [{:name "Nothing happens"
                :probability 1
                :payoff 0}]}
   {:name "Go surfing"
    :outcomes [{:name "Eaten by shark"
                :probability 0.01
                :payoff -10000}
               {:name "Have fun"
                :probability 0.98
                :payoff 50}
               {:name "Meet somebody hot"
                :probability 0.01
                :payoff 500}]}
   {:name "Go skydiving"
    :outcomes [{:name "Crash into ground"
                :probability 0.1
                :payoff -10000}
               {:name "Have fun"
                :probability 0.9
                :payoff 350}]}])

(def button [:div [:button.save "Save Decision"]])

(defn main
  []
  ($/append ($ "body") (-> button html $))
  (saved/load-decision
   (fn [decision]
     (let [{:keys [el state]} (ui/create decision)]
       ($/append ($ "body") el)
       ($/bind ($ "button.save") "click"
               (fn [ev]
                 (let [decision @state]
                   (saved/save-decision decision))))))
   example-decision))

($m/ready (main))