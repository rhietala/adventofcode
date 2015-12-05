(ns adventofcode.day05
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(import '(java.io BufferedReader StringReader))

(def input-str (slurp "/Users/rhietala/ko/adventofcode/resources/05_input.txt"))
(def input-seq (line-seq (io/BufferedReader. (io/StringReader. input-str))))

(def naughty-strings '("ab" "cd" "pq" "xy"))
(def vowels '(\a \e \i \o \u))

(defn naughty-substrings?
  [s]
  (reduce #(or %1 %2)
          (map #(.contains s %) naughty-strings)))

(defn three-vowels?
  [s]
  (>= (count (filter #(.contains vowels %) s))
       3))

(defn two-letters-in-a-row?
  [s]
  (if-let [[x & xs] (seq s)]
    (if (= x (first xs))
      true
      (two-letters-in-a-row? xs))
    false))

(defn pair-of-two-non-overlapping-letters?
  [s]
  (if-let [[x & xs] (seq s)]
    (if (.contains (apply str (rest xs)) (str x (first xs)))
      true
      (pair-of-two-non-overlapping-letters? xs))
    false))

(defn letter-repeats-with-one-letter-in-between?
  [s]
  (if-let [[x & xs] (seq s)]
    (if (= x (first (rest xs)))
      true
      (letter-repeats-with-one-letter-in-between? xs))
    false))

(defn nice-string-part1?
  [s]
  (and (not (naughty-substrings? s))
       (three-vowels? s)
       (two-letters-in-a-row? s)))

(defn nice-string-part2?
  [s]
  (and (pair-of-two-non-overlapping-letters? s)
       (letter-repeats-with-one-letter-in-between? s)))

(defn solve-part1
  [input]
  (count (filter nice-string-part1? input)))

(defn solve-part2
  [input]
  (count (filter nice-string-part2? input)))

(println (str "Part 1 solution: " (solve-part1 input-seq)))
(println (str "Part 2 solution: " (solve-part2 input-seq)))
