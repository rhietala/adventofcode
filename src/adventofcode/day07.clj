(ns adventofcode.day07
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(import '(java.io BufferedReader StringReader))
(use '[clojure.algo.generic.functor :only (fmap)])

(def input-str (slurp "/Users/rhietala/ko/adventofcode/resources/07_input.txt"))
(def input-seq (line-seq (io/BufferedReader. (io/StringReader. input-str))))

(defn parse-str
  [data s]
  (let [[_ in out] (re-matches #"^(.+) -> (.+)$" s)]
    (merge data { (symbol out) in })))

(defn overflow
  [i]
  (bit-and 0xffff i))

(def calculate-signal
  (memoize
   (fn
     [signal data]
     (let [command (str/split signal #" ")]
       (case (count command)
         1 (if (re-find #"^(\d+)$" (first command))
             (read-string (first command))
             (calculate-signal (data (symbol (first command))) data))
         2 (overflow (bit-not (calculate-signal (second command) data)))
         3 (let [op1 (calculate-signal (get command 0) data)
                 op2 (calculate-signal (get command 2) data)]
             (case (second command)
               "AND" (bit-and op1 op2)
               "OR" (bit-or op1 op2)
               "RSHIFT" (overflow (bit-shift-right op1 op2))
               "LSHIFT" (overflow (bit-shift-left op1 op2))
               command)))))))

(defn solve-part1
  [input]
  (let [data (reduce parse-str {} input-seq)]
    (fmap #(overflow (calculate-signal % data)) data)))

(defn solve-part2
  [input]
  (let [data (reduce parse-str {} input-seq)
        data2 (merge data { (symbol "b")
                            (str ((solve-part1 input-seq) (symbol "a"))) })]
    (fmap #(overflow (calculate-signal % data2)) data2)))

(println (str "Part 1 solution: " ((solve-part1 input-seq) (symbol "a"))))
(println (str "Part 2 solution: " ((solve-part2 input-seq) (symbol "a"))))
