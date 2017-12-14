(ns clojure_apfloat.rational
  "fix me."
  (:refer-clojure :exclude [* - + / seq vec vector seq? vector? == < <= > >= not= = min max])
  (:require
   [clojure_apfloat.core :as ap]
   [clojure_apfloat.util :refer :all])
  (:import
   (org.apfloat Apfloat Aprational AprationalMath Apint)))

(defn seq? [coll]
  "Returns true if coll is a seqable and all ellements of coll are Aprational
   instances, otherwise false."
  (and (clojure.core/seq? coll)
       (every? ap/rational? coll)))

(defn vector? [coll]
  "Returns true if coll is a vector and all ellements of coll are Aprational 
   instances, otherwise false."
  (and (clojure.core/vector? coll)
       (every? ap/rational? coll)))

(defn seq
  "Returns a sequence of Aprational instances. coll is a collection of numbers 
   or strings. A convenient way to make many Aprational instances."
  ([coll] (map #(ap/rational %) coll)))

(defn vec
  "Returns a vector of Aprational instances. coll is a collection of numbers
  or strings. A convenient way to make many Aprational instances."
  ([coll] (mapv #(ap/rational %) coll)))

(defn vector [& values]
  "Returns a vector of Aprational instances. values are numbers or strings."
  (clojure.core/vec (for [v values] (ap/rational v))))

(defn =
  "= for Aprational instances. Same as clojure_apfloat.rational/=="
  ([^Aprational x] true)
  ([^Aprational x ^Aprational y] (.equals x y))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]}
   (every? #(= x %) (into [y] more))))

(defn ==
  "== for Aprational instances. Same as clojure_apfloat.rational/="
  ([^Aprational x] true)
  ([^Aprational x ^Aprational y] (.equals x y))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]}
   (every? #(= x %) (into [y] more))))

(defn >
  "> for Aprational instances."
  ([^Aprational x] true)
  ([^Aprational x ^Aprational y] (clojure.core/= 1 (.compareTo x y)))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]}
   (loop-compare > x y more)))

(defn >=
  ">= for Aprational instances."
  ([^Aprational x] true)
  ([^Aprational x ^Aprational y] (or (= x y) (> x y)))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]}
   (loop-compare >= x y more)))

(defn not=
  "not= for Aprational instances."
  ([^Aprational x] false)
  ([^Aprational x ^Aprational y] (not (= x y)))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]}
   (not (apply = (into [x y] more)))))

(defn <
  "< for Aprational instances."
  ([^Aprational x] true)
  ([^Aprational x ^Aprational y] (not (>= x y)))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]}
   (loop-compare < x y more)))

(defn <=
  "<= for Aprational instances."
  ([^Aprational x] true)
  ([^Aprational x ^Aprational y] (not (> x y)))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]}
   (loop-compare <= x y more)))

(defn ^Aprational min
  "Returns the minimum value of (Aprational) arguments."
  ([^Aprational x] x)
  ([^Aprational x ^Aprational y] (if (< x y) x y))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]}
   (reduce min (into [x y] more))))

(defn ^Aprational max
  "Returns the maximum value of (Aprational) arguments."
  ([^Aprational x] x)
  ([^Aprational x ^Aprational y] (if (> x y) x y))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]}
   (reduce max (into [x y] more))))

(defn ^Aprational +
  "+ for Aprational instances."
  ([^Aprational x] x)
  ([^Aprational x ^Aprational y] (.add x y))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]} (reduce + (into [x y] more))))

(defn ^Aprational -
  "- for Aprational instances."
  ([^Aprational x] (.negate x))
  ([^Aprational x ^Aprational y] (.subtract x y))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]} (reduce - (into [x y] more))))

(defn ^Aprational *
  "* for Aprational instances."
  ([^Aprational x] x)
  ([^Aprational x ^Aprational y] (.multiply x y))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]} (reduce * (into [x y] more))))

(defn ^Aprational /
  "/ for Aprational instances."
  ([^Aprational x] (.divide (ap/rational 1.0M) x))
  ([^Aprational x ^Aprational y] (.divide x y))
  ([^Aprational x ^Aprational y & more] {:pre [(seq? more)]} (reduce / (into [x y] more))))


