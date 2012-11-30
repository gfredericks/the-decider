(ns the-decider.ui
  (:require [jayq.core :as $ :refer [$]]
            [crate.core :as crate]))

(defn html
  [options]
  (crate/html
   [:div.decider
    (for [{:keys [name outcomes]} options]
      [:div.option
       [:h1 name]
       [:table
        (for [{:keys [name probability payoff]} outcomes]
          [:tr
           [:td name]
           [:td [:input.probability {:value probability}]]
           [:td [:input.payoff {:value payoff}]]])]])
    [:div.results
     [:h1 "Results"]
     [:p "Okay here's what you should do."]]]))

(defn create
  [options]
  {:state (atom options)
   :el ($ (html options))})