(ns clojure_apfloat.util)

(defn range1 [n]
  "Same as (range 1 (inc n))"
  (range 1 (inc n)))

(defn loop-compare [binary-op x y coll]
  (loop [flag (binary-op x y), old y, coll coll]
    (cond
      (not flag) false
      (nil? coll) true
      :else (let [fst (first coll)]
              (recur (binary-op old fst) fst (next coll))))))

(defmacro form->result [& forms]
  `(do ~@(for [f forms] `(println '~f "=>" ~f))))
