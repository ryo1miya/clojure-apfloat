(ns clojure_apfloat.core
  "Copyright © 2017 MIYAZAKI Ryoichi"
  (:refer-clojure :exclude [float float? int int? ratio? rational?])
  (:import
   (org.apfloat Apfloat ApfloatMath Apcomplex Apint Aprational)))

(def DEFAULT (. Apcomplex DEFAULT))
(def I (. Apcomplex I))
(def INFINITE (. Apcomplex INFINITE))
(def ZERO (. Apcomplex ZERO))
(def ONE (. Apcomplex ONE))

;; (def ^:dynamic *precision* (. Apfloat INFINITE))
(defonce apfloat-precision (atom INFINITE))

(defn precision-set! [n] (reset! apfloat-precision n))
(defn precision [] @apfloat-precision)

(defn pi
  ([] (ApfloatMath/pi (precision)))
  ([prec] (ApfloatMath/pi prec)))

(defn e
  ([] (ApfloatMath/exp (Apfloat. 1.0M (precision))))
  ([prec] (ApfloatMath/exp (Apfloat. 1.0M prec))))

(defn int? [x] (instance? Apint x))
(defn ratio? [x] (instance? Aprational x))
(defn rational? [x] (instance? Aprational x))
(defn float? [x] (instance? Apfloat x))
(defn complex? [x] (instance? Apcomplex x))

;; radix を使うなら別途用意したほうが良い
(defn ^Apint int
  ([x] (cond
         (int? x) x
         (= (type x) clojure.lang.BigInt) (Apint. (str x))
         :else (Apint. x))))

(defn ^Aprational rational
  ([x] (cond
         (rational? x) x
         (string? x) (Aprational. x)
         (= (type x) clojure.lang.BigInt) (Aprational. (int x))
         (integer? x) (Aprational. (int x))
         (clojure.core/ratio? x) (Aprational. (int (numerator x))
                                                 (int (denominator x)))
         :else (Aprational. (int x))))
  ([numerator denominator]
   (Aprational. (int numerator) (int denominator))))

(defn ^Apfloat float
  ([x] (cond
         (float? x) x
         (= (type x) clojure.lang.BigInt) (Apfloat. (str x) (precision))
         (clojure.core/ratio? x) (.divide (Apfloat. (numerator x) (precision))
                                       (Apfloat. (denominator x) (precision)))
         :else (Apfloat. x (precision))))
  ([x precision] (if (float? x)
                   (Apfloat. (.toString x) precision)
                   (Apfloat. x precision))))

(defn ^Apcomplex complex
  ([x] (cond
         (complex? x) x
         (string? x) (Apcomplex. x)
         :else (Apcomplex. (float x))))
  ([x y] (Apcomplex. (float x) (float y))))

(defn ^Apfloat int->float
  [^Apint x]
  (float (.toString x)))

(defn ^Apfloat rational->float
  [^Aprational x]
  (let [n (.numerator x), d (.denominator x)]
    (.divide (int->float n) (int->float d))))

(defn ^Apcomplex int->complex
  [^Apint x]
  (complex (int->float x)))

(defn ^Apcomplex rational->complex
  [^Aprational x]
  (complex (rational->float x)))



