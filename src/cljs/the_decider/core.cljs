(ns the-decider.core
  (:require [the-decider.math :as math]
            [jayq.core :as $ :refer [$]])
  (:require-macros [jayq.macros :as $m]))

(defn run
  []
  ($m/ready
   ($/append ($ "body") "Hello DOM World!")))