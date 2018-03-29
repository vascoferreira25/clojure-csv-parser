(ns csv-parser.core
  (:gen-class))

;; Read CSV file as string
(defn parse-csv
  "Parse CSV file. Splits columns with `separator` and rows with `line-break`
  arguments.
  Default:
  `separator`: ','
  `line-break`: '\r\n'"
  ([csv] (parse-csv csv "," "\r\n"))
  ([csv separator line-break]
  (let [string (slurp csv)]
    (mapv #(clojure.string/split % (re-pattern separator)) (clojure.string/split string (re-pattern line-break))))))
;; (parse-csv csv-file)

;; Convert values
(defn convert-value
  [k
   v
   conversion-table]
  ((get conversion-table k) v))

(defn str->int
  [str]
  (Integer. str))

(defn str->float
  [str]
  (Float. str))

;; Convert CSV string to vectors
(defn csv-mapv
  "Takes a file name `dataset.csv` and map `column-names` with column names and
  data type and returns a vector of maps

  ;; Example
  ;; Get file
  (def csv-file \"grades.csv\")

  ;; Define columns and values
  (def column-names
    {:subject-name str
     :grades csv-parser/str->int
     :credits csv-parser/str->float})

  ;; Get csv as map
  (csv-parser/csv-mapv csv-file column-names)

  ;; Bind csv to dataset variable
  (def dataset
    (csv-parser/csv-mapv csv-file column-names))

  ;; Get all rows in column :grades
  (csv-parser/column dataset :grades)

  ;; Get rows 2 and 3
  (csv-parser/row dataset [1 2])"
  [csv-file
   column-names]
  (let [rows (parse-csv csv-file)]
  ;; Create Vector with all the maps
  (mapv (fn
          ;; Take a row
          [unmapped-row]
          ;; Join all rows
          (reduce (fn
                    ;; Take a map with mapped rows, initially empty
                    ;; take the col-name and value
                    [mapped-rows [col-name value]]
                    ;; append to mapped rows map
                    ;; they key: col-name with value: value
                    (assoc mapped-rows
                           col-name
                           ;; Convert the value according to the conversion table
                           (convert-value col-name value column-names)))
                  ;; Empty map to append all the rows
                  {}
                  ;; Create map with key-value pairs in vectors
                  ;; ([:col-name value] [:col-name value] [:col-name value]])
                  (map vector (vec (keys column-names)) unmapped-row)))
        rows)))

;; Get Data
(defn column
  "Get all rows for column `col-name`"
  [csv
   col-name]
  (mapv col-name csv))

(defn row
  "Get data of rows `index-vector`: [1] or [1 2 3]"
  [csv
   index-vector]
  (let
      [rows (map csv index-vector)]
    (mapv vals rows)))

;; Example
;; Get file
;; (def csv-file "grades.csv")

;; Define columns and values
;; (def column-names
;;   {:subject-name str
;;    :grades str->int
;;    :credits str->float})

;; Get csv as map
;; (csv-mapv csv-file column-names)

;; Bind csv to dataset variable
;; (def dataset
  ;; (csv-mapv csv-file column-names))

;; Get all rows in column :grades
;; (column dataset :grades)

;; Get rows 2 and 3
;; (row dataset [1 2])

;; (defn -main
  ;; "I don't do a whole lot ... yet."
  ;; [& args]
  ;; (println "Hello, World!"))
