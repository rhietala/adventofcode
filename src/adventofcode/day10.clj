(ns adventofcode.day10
  (:require [clojure.string :as str]))

(def input-str (str/trim
                (slurp "/Users/rhietala/ko/adventofcode/resources/10_input.txt")))

(defn look-and-say-digits
  [seq]
  (str (count seq) (first seq)))

(defn group-digits
  [s]
  (partition-by identity s))

(defn look-and-say-iterate
  [input iterations]
  (let [s (apply str (map look-and-say-digits (group-digits input)))]
    (if (<= iterations 1)
      s
      (look-and-say-iterate s (dec iterations)))))

(defn solve-part1
  [input]
  (count (look-and-say-iterate input 40)))

(defn solve-part2
  [input]
  (count (look-and-say-iterate input 50)))

(println (str "Part 1 solution: " (solve-part1 input-str)))
(println (str "Part 2 solution: " (solve-part2 input-str)))
