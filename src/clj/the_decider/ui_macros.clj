(ns the-decider.ui-macros)

(defmacro for!
  [bindings body]
  `(doall (for ~bindings ~body)))