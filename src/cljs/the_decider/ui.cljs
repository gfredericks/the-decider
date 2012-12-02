(ns the-decider.ui
  (:require [jayq.core :as $ :refer [$]]
            [crate.core :as crate]
            [cljs.reader :refer [read-string]]
            [the-decider.math :as math])
  (:require-macros [the-decider.ui-macros :refer [for!]]))

(defn html
  [options]
  (crate/html
   [:div.decider
    (for [{:keys [name outcomes]} options]
      [:div.option {:data-name name}
       [:h1.name name]
       [:table
        (for [{:keys [name probability payoff]} outcomes]
          [:tr.outcome {:data-name name}
           [:td.name name]
           [:td [:input.probability {:value probability}]]
           [:td [:input.payoff {:value payoff}]]])]])
    [:div.results
     [:h1 "Results"]
     [:p.results "Okay here's what you should do."]]]))

(defn extract-decision-from-dom
  [el]
  ;; TODO: decent macro opportunity? (defmacro for! ...)
  (for! [option-el ($/find el "div.option")
        :let [option-el ($ option-el)
              name ($/attr option-el "data-name")
              outcomes  (for! [outcome-el ($/find option-el "tr.outcome")
                              :let [outcome-el ($ outcome-el)
                                    name ($/attr outcome-el "data-name")
                                    prob (-> outcome-el
                                             ($/find "input.probability")
                                             ($/val)
                                             (read-string))
                                    payoff (-> outcome-el
                                               ($/find "input.payoff")
                                               ($/val)
                                               (read-string))]]
                          {:name name, :probability prob, :payoff payoff})]]
    {:name name
     :outcomes outcomes}))

(defn create
  [options]
  (let [state (atom options)
        el ($ (html options))
        results ($/find el "p.results")
        report-best (fn [option-name]
                      ($/text results (format "The best option is %s." option-name)))]
    ($/bind el "change"
            (fn []
              (let [decision (extract-decision-from-dom el)
                    best (math/decide decision)]
                (report-best best)
                (reset! state decision))))
    (-> options math/decide report-best)
    {:state state
     :el el}))