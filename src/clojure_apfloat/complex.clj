(ns clojure_apfloat.complex
  "fix me."
  (:refer-clojure :exclude [* - + / vec vector seq? vector? = == not=])
  (:require
   [clojure_apfloat.core :as ap]
   [clojure_apfloat.util :refer :all]
   [clojure_apfloat.float :as apf])
  (:import
   (org.apfloat Apcomplex ApcomplexMath Apfloat Apint)))

(defn seq? [coll]
  (and (clojure.core/seq? coll)
       (every? ap/complex? coll)))

(defn vector? [coll]
  (and (clojure.core/vector? coll)
       (every? ap/complex? coll)))

(defn vector [& vec2s]
  (mapv (fn [[x y]] (ap/complex x y)) vec2s))

(defn vec [coll]
  (apply vector coll))

(defn =
  ([^Apcomplex x] true)
  ([^Apcomplex x ^Apcomplex y] (.equals x y))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [(seq? more)]}
   (every? #(.equals x %) (into [y] more))))

(defn ==
  ([^Apcomplex x] true)
  ([^Apcomplex x ^Apcomplex y] (.equals x y))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [(seq? more)]}
   (every? #(.equals x %) (into [y] more))))

(defn not=
  ([^Apcomplex x] false)
  ([^Apcomplex x ^Apcomplex y] (not (.equals x y)))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [(seq? more)]}
   (not-every? #(.equals x %) (into [y] more))))

(defn +
  ([^Apcomplex x] x)
  ([^Apcomplex x ^Apcomplex y] (.add x y))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [seq? more]}
   (reduce + (into [x y] more))))

(defn -
  ([^Apcomplex x] (.negate x))
  ([^Apcomplex x ^Apcomplex y] (.subtract x y))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [(seq? more)]}
   (reduce - (into [x y] more))))

(defn *
  ([^Apcomplex x] x)
  ([^Apcomplex x ^Apcomplex y] (.multiply x y))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [(seq? more)]}
   (reduce * (into [x y] more))))

(defn /
  ([^Apcomplex x] (.divide (ap/float 1.0) x))
  ([^Apcomplex x ^Apcomplex y] (.divide x y))
  ([^Apcomplex x ^Apcomplex y & more] {:pre [(seq? more)]}
   (reduce / (into [x y] more))))


