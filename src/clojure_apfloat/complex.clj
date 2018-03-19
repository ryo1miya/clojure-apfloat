(ns clojure_apfloat.complex
  "Copyright © 2017 MIYAZAKI Ryoichi"
  (:refer-clojure :exclude [* - + / vec vector seq seq? vector? = == not=])
  (:require
   [clojure_apfloat.core :as ap]
   [clojure_apfloat.float :as apf])
  (:import
   (org.apfloat Apcomplex ApcomplexMath Apfloat Apint)))

(defn seq? [coll]
  "Returns true if coll is a seqable and all ellements of coll are Apcomplex
   instances, otherwise false."
  (and (clojure.core/seq? coll)
       (every? ap/complex? coll)))

(defn vector? [coll]
  "Returns true if coll is a vector and all ellements of coll are Apcomplex
   instances, otherwise false."
  (and (clojure.core/vector? coll)
       (every? ap/complex? coll)))

(defn seq
  "Returns a sequence of Apcomplex instances. coll is a collection of numbers 
   or strings. A convenient way to make many Apcomplex instances."
  ([coll] (map #(ap/complex %) coll)))

(defn vector [& args]
  "Returns a vector of Apcomplex instances. args are [real1,imag1], [real2,imag2],..."
  (mapv (fn [[x y]] (ap/complex x y)) args))

(defn vec [coll]
  "Returns a vector of Apcomplex instances. coll is [[real1,imag1], [real2,imag2],...]"
  (apply vector coll))

(defn =
  "= for Apcomplex instances. Same as clojure_apfloat.complex/=="
  ([^Apcomplex x] true)
  ([^Apcomplex x ^Apcomplex y] (.equals x y))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [(seq? more)]}
   (every? #(.equals x %) (into [y] more))))

(defn ==
  "== for Apcomplex instances. Same as clojure_apfloat.complex/="
  ([^Apcomplex x] true)
  ([^Apcomplex x ^Apcomplex y] (.equals x y))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [(seq? more)]}
   (every? #(.equals x %) (into [y] more))))

(defn not=
  "not= for Apcomplex instances."  
  ([^Apcomplex x] false)
  ([^Apcomplex x ^Apcomplex y] (not (.equals x y)))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [(seq? more)]}
   (not-every? #(.equals x %) (into [y] more))))

(defn +
  "+ for Apcomplex instances."  
  ([^Apcomplex x] x)
  ([^Apcomplex x ^Apcomplex y] (.add x y))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [seq? more]}
   (reduce + (into [x y] more))))

(defn -
  "- for Apcomplex instances."  
  ([^Apcomplex x] (.negate x))
  ([^Apcomplex x ^Apcomplex y] (.subtract x y))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [(seq? more)]}
   (reduce - (into [x y] more))))

(defn *
  "* for Apcomplex instances."  
  ([^Apcomplex x] x)
  ([^Apcomplex x ^Apcomplex y] (.multiply x y))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [(seq? more)]}
   (reduce * (into [x y] more))))

(defn /
  "/ for Apcomplex instances."
  ([^Apcomplex x] (.divide (ap/float 1.0M) x))
  ([^Apcomplex x ^Apcomplex y] (.divide x y))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [(seq? more)]}
   (reduce / (into [x y] more))))


