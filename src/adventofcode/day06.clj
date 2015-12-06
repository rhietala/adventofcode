(ns adventofcode.day06
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(import '(java.io BufferedReader StringReader))

(def input-str (slurp "/Users/rhietala/ko/adventofcode/resources/06_input.txt"))
(def input-seq (line-seq (io/BufferedReader. (io/StringReader. input-str))))

(defn parse-input-line
  [s]
  (let [[_ command & coords]
        (re-matches #"^(.+) (\d+),(\d+) through (\d+),(\d+)$" s)]
    (flatten [command (map read-string coords)])))

(defn add-command-to-coordinates
  [coordmap command-coords]
  (let [[command x0 y0 x1 y1] command-coords]
    (apply merge coordmap
           (for [x (range x0 (+ x1 1)) y (range y0 (+ y1 1))]
             (hash-map (vector x y)
                       (conj (or (coordmap (vector x y)) [])
                             command))))))

(defn light-on?
  [commands]
  (reduce (fn [prev cur]
            (case cur
              "turn off" false
              "turn on" true
              "toggle" (not prev)))
          false
          commands))

(defn calculate-brightness
  [commands]
  (reduce (fn [prev cur]
            (case cur
              "turn off" (if (> prev 0) (- prev 1) 0)
              "turn on" (+ prev 1)
              "toggle" (+ prev 2)))
          0
          commands))

(defn solve-part1
  [input]
  (count (filter #(light-on? (second %))
                 (reduce add-command-to-coordinates (hash-map)
                         (map parse-input-line input)))))

(defn solve-part2
  [input]
  (reduce (fn [prev cur] (+ (calculate-brightness (second cur)) prev))
          0
          (reduce add-command-to-coordinates (hash-map)
                  (map parse-input-line input))))

(println (str "Part 1 solution: " (solve-part1 input-seq)))
(println (str "Part 2 solution: " (solve-part2 input-seq)))
