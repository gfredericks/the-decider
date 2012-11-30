(ns the-decider.core
  (:require [the-decider.ui :as ui]
            [jayq.core :as $ :refer [$]])
  (:require-macros [jayq.macros :as $m]))

(def example-options
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

(defn run
  []
  (let [{:keys [el state]} (ui/create example-options)]
    ($m/ready
     ($/append ($ "body") el))))