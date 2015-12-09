(ns adventofcode.day08
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(import '(java.io BufferedReader StringReader))
(use '[clojure.algo.generic.functor :only (fmap)])

(def input-str (slurp "/Users/rhietala/ko/adventofcode/resources/08_input.txt"))
(def input-seq (line-seq (io/BufferedReader. (io/StringReader. input-str))))

(defn unescape-string
  [s]
  (-> s
      (str/replace #"^\"" "")
      (str/replace #"\"$" "")
      (str/replace #"\\(x[0-9a-f][0-9a-f])"
                   #(str (char (read-string (str "0" (second %))))))
      (str/replace #"\\\"" "\"")
      (str/replace #"\\\\" "\\\\")))

(defn escape-string
  [s]
  (str "\""
       (-> s
           (str/replace #"\\" "\\\\\\\\")
           (str/replace #"\"" "\\\\\""))
       "\""))

(defn solve-part1
  [input]
  (- (reduce + (map count input))
     (reduce + (map #(count (unescape-string %)) input))))

(defn solve-part2
  [input]
  (- (reduce + (map #(count (escape-string %)) input))
     (reduce + (map count input))))

(println (str "Part 1 solution: " (solve-part1 input-seq)))
(println (str "Part 2 solution: " (solve-part2 input-seq)))
