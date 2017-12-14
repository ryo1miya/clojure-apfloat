(ns clojure_apfloat.float
  "fix me."
  (:refer-clojure :exclude [* - + / seq vec vector seq? vector? == < <= > >= not= = min max])
  (:require
   [clojure_apfloat.core :as ap]
   [clojure_apfloat.util :refer :all])
  (:import
   (org.apfloat Apfloat ApfloatMath)))

(defn seq? [coll]
  "Returns true if coll is a seqable and all ellements of coll are Apfloat 
   instances, otherwise false."
  (and (clojure.core/seq? coll)
       (every? ap/float? coll)))

(defn vector? [coll]
  "Returns true if coll is a vector and all ellements of coll are Apfloat 
   instances, otherwise false."
  (and (clojure.core/vector? coll)
       (every? ap/float? coll)))

(defn seq
  "Returns a sequence of Apfloat instances. coll is a collection of numbers 
   or strings. A convenient way to make many Apfloat instances."
  ([coll] (map #(ap/float % (ap/precision)) coll))
  ([coll precision] (map #(ap/float % precision) coll)))

(defn vec
  "Returns a vector of Apfloat instances. coll is a collection of numbers
  or strings. A convenient way to make many Apfloat instances."
  ([coll] (mapv #(ap/float % (ap/precision)) coll))
  ([coll precision] (mapv #(ap/float % precision) coll)))

(defn vector [& values]
  "Returns a vector of Apfloat instances. values are numbers or strings."
  (clojure.core/vec (for [v values] (ap/float v (ap/precision)))))

(defn =
  "= for Apfloat instances. Same as clojure_apfloat.float/=="
  ([^Apfloat x] true)
  ([^Apfloat x ^Apfloat y] (.equals x y))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]}
   (every? #(= x %) (into [y] more))))

(defn ==
  "== for Apfloat instances. Same as clojure_apfloat.float/="
  ([^Apfloat x] true)
  ([^Apfloat x ^Apfloat y] (.equals x y))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]}
   (every? #(= x %) (into [y] more))))

(defn >
  "> for Apfloat instances."
  ([^Apfloat x] true)
  ([^Apfloat x ^Apfloat y] (clojure.core/= 1 (.compareTo x y)))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]}
   (loop-compare > x y more)))

(defn >=
  ">= for Apfloat instances."
  ([^Apfloat x] true)
  ([^Apfloat x ^Apfloat y] (or (= x y) (> x y)))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]}
   (loop-compare >= x y more)))

(defn not=
  "not= for Apfloat instances."
  ([^Apfloat x] false)
  ([^Apfloat x ^Apfloat y] (not (= x y)))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]}
   (not (apply = (into [x y] more)))))

(defn <
  "< for Apfloat instances."
  ([^Apfloat x] true)
  ([^Apfloat x ^Apfloat y] (not (>= x y)))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]}
   (loop-compare < x y more)))

(defn <=
  "<= for Apfloat instances."
  ([^Apfloat x] true)
  ([^Apfloat x ^Apfloat y] (not (> x y)))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]}
   (loop-compare <= x y more)))

(defn ^Apfloat min
  "Returns the minimum value of (Apfloat) arguments."
  ([^Apfloat x] x)
  ([^Apfloat x ^Apfloat y] (if (< x y) x y))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]}
   (reduce min (into [x y] more))))

(defn ^Apfloat max
  "Returns the maximum value of (Apfloat) arguments."
  ([^Apfloat x] x)
  ([^Apfloat x ^Apfloat y] (if (> x y) x y))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]}
   (reduce max (into [x y] more))))
                               
;; ^Apfloat + として返り値の型を指定してもそんなに差はない
(defn ^Apfloat +
  "+ for Apfloat instances."
  ([^Apfloat x] x)
  ([^Apfloat x ^Apfloat y] (.add x y))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]} (reduce + (into [x y] more))))

(defn ^Apfloat -
  "- for Apfloat instances."
  ([^Apfloat x] (.negate x))
  ([^Apfloat x ^Apfloat y] (.subtract x y))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]} (reduce - (into [x y] more))))

(defn ^Apfloat *
  "* for Apfloat instances."
  ([^Apfloat x] x)
  ([^Apfloat x ^Apfloat y] (.multiply x y))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]} (reduce * (into [x y] more))))

(defn ^Apfloat /
  "/ for Apfloat instances."
  ([^Apfloat x] (.divide (ap/float 1.0M) x))
  ([^Apfloat x ^Apfloat y] (.divide x y))
  ([^Apfloat x ^Apfloat y & more] {:pre [(seq? more)]} (reduce / (into [x y] more))))
