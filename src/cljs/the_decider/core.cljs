(ns the-decider.core
  (:require [the-decider.math :as math]))

(defn run
  []
  (js/alert (pr-str (math/decide [{:name "Only choice." :options [{:probability 1 :payoff 7}]}]))))