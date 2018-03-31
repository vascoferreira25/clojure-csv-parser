# csv-parser

A simple csv file parser for Clojure.

## Installation

1. Download the master branch
2. Go to the folder and install it to the local repositories with `lein install`
3. Add `[csv-parser "0.1.5-SNAPSHOT]` to `:dependencies` in your `project.clj`
4. Add `(:require [csv-parser.core :as csv-parser])` in the _namespace_ of `core.clj`

## Usage
``` clojure
;; File name
(def csv-file "grades.csv")

;; Define columns and values
(def column-names
  {:subject-name str ;; Value is string
   :grades csv-parser/str->int ;; Value is integer
   :credits csv-parser/str->float}) ;; Value is float

;; Bind csv to dataset variable
(def dataset
  (csv-parser/csv-mapv csv-file column-names))
; => [{:subject-name "Finance", :grades 19, :credits 7.5} 
      {:subject-name "Management Strategy", :grades 17, :credits 7.5} 
      {:subject-name "Methodology", :grades 13, :credits 7.5} 
      {:subject-name "Math I", :grades 18, :credits 7.5} 
      {:subject-name "Economy", :grades 15, :credits 7.5} 
      {:subject-name "Marketing Management", :grades 18, :credits 7.5} 
      {:subject-name "Math II", :grades 15, :credits 7.5} 
      {:subject-name "Accounting", :grades 16, :credits 7.5} 
      {:subject-name "Thesis", :grades 18, :credits 60.0}]
  
;; Count data
(count dataset)
  
;; Get all rows in column :grades
(csv-parser/column dataset :grades)
; => [19 17 13 18 15 18 15 16 18]

(def grades
  (csv-parser/column dataset :grades))

;; Calculate mean grade (ratio)
(/ (reduce + grades) (count grades))
; => 149/9  
 
;; Calculate mean grade (float)
(float (/ (reduce + grades) (count grades)))
; => 16.555555

;; Get rows index 2 and 3
(csv-parser/row dataset [1 2])
; => [("Management Strategy" 17 7.5) ("Methodology" 13 7.5)]
```
