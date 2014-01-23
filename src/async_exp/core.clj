(ns async-exp.core
    (:refer-clojure :exclude  [map reduce into partition partition-by take merge])
    (:require  [clojure.core.async :refer :all :as async]))

(def c (chan))


(defn isPrime?
  [x]
  (if  (= (mod x 2) 0)
    false
    (let [sqrt-of-x (Math/sqrt x)]
      (loop [div 3]
        (cond
          (= (mod x div) 0) false
          (> div sqrt-of-x) true
          :else (recur (+ 2 div)))))))

