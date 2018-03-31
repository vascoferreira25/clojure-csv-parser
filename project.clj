(defproject csv-parser "0.1.6-SNAPSHOT"
  :description "CSV Parser, transforms csv file in vector of maps."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot csv-parser.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
