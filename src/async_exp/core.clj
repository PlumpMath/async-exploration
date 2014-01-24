(ns async-exp.core
    (:refer-clojure :exclude  [map reduce into partition partition-by take merge])
    (:require  [clojure.core.async :refer :all :as async]))


;;(clojure.core/take 5 (repeatedly #(range 20)))

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

;; (def c (chan))
;;
;; (go
;;     (dotimes [n 10]
;;       (>! c n)))
;;
;; (<!! c)

(defn readwrite [] (let [ch (chan)]
  (go (while true
        (let [v (<! ch)]
          (println "Read: " v))))
  (go (>! ch "hi")
      (>! ch "there"))))

(defn readfrom
  [ch]
  (go
    (while true
      (let [v (<! ch)]
        (println "Read: " v)))))


(defn write
  [seq-to-write]
  (let [out (chan)]
    (go
      (doseq [i seq-to-write]
        (>! out i)))
    out))

