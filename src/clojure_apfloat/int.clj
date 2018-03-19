(ns clojure_apfloat.int
  "Copyright © 2017 MIYAZAKI Ryoichi"
  (:refer-clojure :exclude [* - + / seq vec vector seq? vector? == < <= > >= not= = min max])
  (:require [clojure_apfloat.core :as ap]
            [clojure_apfloat.util :refer [loop-compare]])
  (:import
   (org.apfloat Apfloat Aprational Apint ApintMath)))

(defn seq? [coll]
  "Returns true if coll is a seqable and all ellements of coll are Apint
   instances, otherwise false."
  (and (clojure.core/seq? coll)
       (every? ap/int? coll)))

(defn vector? [coll]
  "Returns true if coll is a vector and all ellements of coll are Apint 
   instances, otherwise false."
  (and (clojure.core/vector? coll)
       (every? ap/int? coll)))

(defn seq
  "Returns a sequence of Apint instances. coll is a collection of numbers 
   or strings. A convenient way to make many Apint instances."
  ([coll] (map #(ap/int %) coll)))

(defn vec
  "Returns a vector of Apint instances. coll is a collection of numbers
  or strings. A convenient way to make many Apint instances."
  ([coll] (mapv #(ap/int %) coll)))

(defn vector [& values]
  "Returns a vector of Apint instances. values are numbers or strings."
  (clojure.core/vec (for [v values] (ap/int v))))

(defn =
  "= for Apint instances. Same as clojure_apfloat.int/=="
  ([^Apint x] true)
  ([^Apint x ^Apint y] (.equals x y))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]}
   (every? #(= x %) (into [y] more))))

(defn ==
  "== for Apint instances. Same as clojure_apfloat.int/="
  ([^Apint x] true)
  ([^Apint x ^Apint y] (.equals x y))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]}
   (every? #(= x %) (into [y] more))))

(defn not=
  "not= for Apint instances."
  ([^Apint x] false)
  ([^Apint x ^Apint y] (not (= x y)))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]}
   (not (apply = (into [x y] more)))))

(defn >
  "> for Apint instances."
  ([^Apint x] true)
  ([^Apint x ^Apint y] (clojure.core/= 1 (.compareTo x y)))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]}
   (loop-compare > x y more)))

(defn >=
  ">= for Apint instances."
  ([^Apint x] true)
  ([^Apint x ^Apint y] (or (= x y) (> x y)))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]}
   (loop-compare >= x y more)))

(defn <
  "< for Apint instances."
  ([^Apint x] true)
  ([^Apint x ^Apint y] (not (>= x y)))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]}
   (loop-compare < x y more)))

(defn <=
  "<= for Apint instances."
  ([^Apint x] true)
  ([^Apint x ^Apint y] (not (> x y)))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]}
   (loop-compare <= x y more)))

(defn ^Apint min
  "Returns the minimum value of Apint arguments."
  ([^Apint x] x)
  ([^Apint x ^Apint y] (if (< x y) x y))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]}
   (reduce min (into [x y] more))))

(defn ^Apint max
  "Returns the maximum value of Apint arguments."
  ([^Apint x] x)
  ([^Apint x ^Apint y] (if (> x y) x y))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]}
   (reduce max (into [x y] more))))

(defn ^Apint +
  "+ for Apint instances."
  ([^Apint x] x)
  ([^Apint x ^Apint y] (.add x y))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]} (reduce + (into [x y] more))))

(defn ^Apint -
  "- for Apint instances."
  ([^Apint x] (.negate x))
  ([^Apint x ^Apint y] (.subtract x y))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]} (reduce - (into [x y] more))))

(defn ^Apint *
  "* for Apint instances."
  ([^Apint x] x)
  ([^Apint x ^Apint y] (.multiply x y))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]} (reduce * (into [x y] more))))

(defn ^Apint /
  "/ for Apint instances."
  ([^Apint x] (.divide (ap/int 1) x))
  ([^Apint x ^Apint y] (.divide x y))
  ([^Apint x ^Apint y & more] {:pre [(seq? more)]} (reduce / (into [x y] more))))



