(ns adventofcode.day04
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            digest))

(def input-str (str/trim (slurp "/Users/rhietala/ko/adventofcode/resources/04_input.txt")))

(defn find-first
  [f coll]
  (first (filter f coll)))

(defn md5-has-leading-string?
  [input-str leading-str]
  (= (subs (digest/md5 input-str) 0 (count leading-str))
     leading-str))

(defn first-number-with-md5-leading-string
  [input leading-str]
  (first (keep-indexed
          (fn [i val] (when (md5-has-leading-string? (str input i)
                                                     leading-str) i))
          (range))))

(defn solve-part1
  [input]
  (first-number-with-md5-leading-string input "00000"))

(defn solve-part2
  [input]
  (first-number-with-md5-leading-string input "000000"))

(println (str "Part 1 solution: " (solve-part1 input-str)))
(println (str "Part 2 solution: " (solve-part2 input-str)))
