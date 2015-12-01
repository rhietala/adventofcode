(ns adventofcode.day01
  (:require [clojure.java.io :as io]))

(def s (slurp "/Users/rhietala/ko/adventofcode/resources/01_input.txt"))

;; http://stackoverflow.com/a/10192733
(defn find-first
  [f coll]
  (first (filter f coll)))

(defn solve-part2
  [s]
  (let [values (map #(if (= % \)) -1 1) s)
        cumul  (map #(reduce + (take (+ % 1)
                                     values))
                    (range (count values)))
        indexes (map-indexed (fn [i val] (if (< val 0) i -1))
                             cumul)]
    (+ (find-first #(> % -1) indexes)
       1)))

(solve-part2 s)
