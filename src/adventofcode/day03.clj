(ns adventofcode.day03
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(import '(java.io BufferedReader StringReader))

(def input-str (slurp "/Users/rhietala/ko/adventofcode/resources/03_input.txt"))

(defn parse-coordinate-change-seq
  [c]
  (case c
    \< [-1, 0]
    \> [1, 0]
    \^ [0, 1]
    \v [0, -1]
    [0 0]))

(defn calculate-coord-changes
  [input]
  (map parse-coordinate-change-seq input))

(defn sum-coords
  [a b]
  (let [[xa ya] a
        [xb yb] b]
    [(+ xa xb) (+ ya yb)]))

(defn calculate-coords
  [coord-changes]
  (distinct
   (conj
    (map-indexed (fn [i val] (reduce sum-coords
                                     (take (+ i 1) coord-changes)))
                 (range (count coord-changes)))
    [0 0])))

(defn solve-part1
  [input]
  (let [coord-changes (calculate-coord-changes input)
        coords (calculate-coords coord-changes)]
    (count coords)))

(defn solve-part2
  [input]
  (let [coord-changes (calculate-coord-changes input)
        coord-changes-even (keep-indexed (fn [i val] (when (even? i) val))
                                         coord-changes)
        coord-changes-odd  (keep-indexed (fn [i val] (when (odd? i) val))
                                         coord-changes)
        coords-even (calculate-coords coord-changes-even)
        coords-odd  (calculate-coords coord-changes-odd)]
    (count (distinct (concat coords-even coords-odd)))))

(println (str "Part 1 solution: " (solve-part1 input-str)))
(println (str "Part 2 solution: " (solve-part2 input-str)))
