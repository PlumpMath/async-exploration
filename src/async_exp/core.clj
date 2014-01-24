(ns async-exp.core
    (:refer-clojure :exclude  [map reduce into partition partition-by take merge])
    (:require  [clojure.core.async :refer :all :as async]))


;;(clojure.core/take 5 (repeatedly #(range 20)))

(defn isPrime?
  "Maybe have it catch 3"
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

(def chan-size 10)

(defn getAnswers
  "read and keep results from channel"
  [ch max]
  (loop [remaining max
         results []]
    (if (zero? remaining)
      results
      (let [v (<!! ch)]
        (prn results)
        (recur (dec remaining)
               (conj results v))))))


(defn processData
  ;;takes channel to process, returns channel with results
  [in-ch]
  (let [out-ch (chan chan-size)]
    (go
      (while true
        (let [v (<! in-ch)]
          (>! out-ch [v (isPrime? v)]))))
    out-ch))

(defn write
  [seq-to-write]
  (let [out (chan chan-size)]
    (go
      (doseq [i seq-to-write]
        (>! out i)))
    out))
;;(a/readfrom (a/processData (a/write [1 2 3 4 5 6])))
