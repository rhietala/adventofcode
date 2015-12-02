(ns adventofcode.day02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(import '(java.io BufferedReader StringReader))

(def input-str (slurp "/Users/rhietala/ko/adventofcode/resources/02_input.txt"))
(def input-seq (line-seq (io/BufferedReader. (io/StringReader. input-str))))
(def input-parsed (map #(map read-string (str/split % #"x")) input-seq))

(defn count-area
  [l w h]
  (let [lw (* l w)
        lh (* l h)
        wh (* w h)
        extra (min lw lh wh)]
    (+ (* 2 lw) (* 2 lh) (* 2 wh) extra)))

(defn count-ribbon
  [lengths]
  (let [shortests (take 2 (sort < lengths))]
    (+ (* 2 (reduce + shortests)) (reduce * lengths))))

(defn solve-part1
  [input]
  (let [areas (map #(apply count-area %) input)]
    (reduce + areas)))

(defn solve-part2
  [input]
  (let [lengths (map #(count-ribbon %) input)]
    (reduce + lengths)))

(println (str "Part 1 solution: " (solve-part1 input-parsed)))
(println (str "Part 2 solution: " (solve-part2 input-parsed)))
