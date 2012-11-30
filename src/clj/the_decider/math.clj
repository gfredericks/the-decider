(ns the-decider.math)

(defn expected-value
  [option]
  (apply + (for [{:keys [probability payoff]} (:outcomes option)]
             (* probability payoff))))

(defn decide
  "options is a list of
   {:name <string>
    :outcomes
      [outcomes...]}
   where an outcome is
   {:probability <p>, :payoff <x>}

   Returns the name of the best option. Outcomes can
   have names too whatever."
  [options]
  (->> options
       (sort-by expected-value)
       last
       :name))

(defn values
  [options]
  (zipmap (map :name options)
          (map expected-value options)))