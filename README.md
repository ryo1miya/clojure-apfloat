# clojure-apfloat

Current Version: 0.1.1

A library designed to use [Apfloat](http://www.apfloat.org/apfloat_java/) in Clojure. This provides 

- conversion from Clojure number(or string) to Apfloat number

- arithmetic operators (+,-,*,/)

- comparison operators (=,==,not=,<,>,<=,>=)

- min,max functions, etc.

## Installation

To install this library to your local repository, hit these commands in your terminal:

``` bash
$ git clone https://github.com/ryo1miya/clojure-apfloat.git
$ cd clojure-apfloat
$ lein install
```

## Usage

If you use this in your project, write dependencies in `project.clj` as follows. (the version numbers may be different.)

``` clojure
(defproject foo "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.apfloat/apfloat "1.8.3"]
                 [clojure-apfloat "0.1.1"]])
```

Ususally, numerical calculations does not need any project. To enjoy numerical calculations on REPL, [lein-try](https://github.com/rkneufeld/lein-try) is useful.

In terminal:

``` bash
$ lein try clojure-apfloat 0.1.1
```

In REPL:

```clojure
user=> (import '(org.apfloat Apfloat ApfloatMath Apcomplex ApcomplexMath))

user=> (require '[clojure_apfloat [core :as ap] [float :as apf] [complex :as apc]])
```

## Examples

In Clojure, we can easily use java functions. See [Apfloat documentation](http://www.apfloat.org/apfloat_java/docs/).

``` clojure
user=> (ap/precision) ; the default precision is INFINITE
9223372036854775807

user=> (ap/precision-set! 30) 
30

user=> (ap/precision)
30

user=> (apf/+ (ap/float 1/2) (ap/float "3.1415192653")) ;; ratio,string is ok.
3.6415192653

user=> (apf/> (ap/float 1) (ap/float 2))
false

user=> (apf/<= (ap/float 1.0M) (ap/float 2.0M) (ap/float 2.0M) (ap/float 3.0M))
true

user=> (apply apf/+ (apf/vec (range 1 1001))) ;; the default is exponential notation
5.005e5

user=> (.toString (apply apf/+ (apf/vec (range 1 1001))) true) 
"500500"

user=> (apf/vector 12345 1.0M "2.2360679" (ap/pi) (ap/e))
[1.2345e4 1 2.2360679 3.14159265358979323846264338327 2.71828182845904523536028747135]

user=> (apf/* (ApfloatMath/atan (ap/float 1.0M)) (ap/float 4.0M)) ; pi = 4 * atan(1)
3.14159265358979323846264338327

user=> (doseq [x (range 0 2 1/6)]
         (println "cos" x "PI" "=" 
                  (ApfloatMath/cos (apf/* (ap/pi) (ap/float x)))))

cos 0 PI = 1
cos 1/6 PI = 8.6602540378443864676372317075293e-1
cos 1/3 PI = 5e-1
cos 1/2 PI = 0
cos 2/3 PI = -4.99999999999999999999999999999e-1
cos 5/6 PI = -8.660254037844386467637231707529e-1
cos 1N PI = -1
cos 7/6 PI = -8.660254037844386467637231707529e-1
cos 4/3 PI = -5e-1
cos 3/2 PI = 0
cos 5/3 PI = 4.99999999999999999999999999999e-1
cos 11/6 PI = 8.660254037844386467637231707529e-1

user=> (ap/complex 1 2)
(1, 2)

user=> (ap/complex "(1,2)")
(1, 2)

user=> (ap/complex 1)
1

user=> (class *1)
org.apfloat.Apcomplex

user=> (apc/vector [1,2] [3,4] [5,6])
[(1, 2) (3, 4) (5, 6)]

user=> (ApcomplexMath/exp (apc/* ap/I
                                 (apf// (ap/pi)
                                        (ap/float 2)))) ; exp(I*pi/2)
(0, 1)
```

## License

Copyright © 2017 MIYAZAKI Ryoichi

Distributed under the Eclipse Public License either version 1.0 or any later version.
