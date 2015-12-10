(ns adventofcode.day09
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
            [loom.graph]))

(import '(java.io BufferedReader StringReader))

(def input-str (slurp "/Users/rhietala/ko/adventofcode/resources/09_input.txt"))
(def input-seq (line-seq (io/BufferedReader. (io/StringReader. input-str))))

(defn parse-route
  [s]
  (let [[_ a b len] (re-matches #"^(.+) to (.+) = (\d+)$" s)]
    [a b (read-string len)]))

(defn merge-routes
  [arr]
  (reduce (fn [acc [loc dest len]]
            (merge acc
                   { loc (merge (acc loc) { dest len }) }))
          {} arr))

(defn calculate-path-weight-impl
  [graph [x & xs] acc]
  (if (< (count xs) 1)
    acc
    (recur graph xs
           (+ acc (loom.graph/weight graph x (first xs))))))

(defn calculate-path-weight
  [graph path]
  (calculate-path-weight-impl graph path 0))

(defn weights
  [input]
  (let [graph (loom.graph/weighted-graph (merge-routes (map parse-route input)))
        perm (combo/permutations (loom.graph/nodes graph))]
    (map #(calculate-path-weight graph %) perm)))

(defn solve-part1
  [input]
  (apply min (weights input)))

(defn solve-part2
  [input]
  (apply max (weights input)))

(println (str "Part 1 solution: " (solve-part1 input-seq)))
(println (str "Part 2 solution: " (solve-part2 input-seq)))
