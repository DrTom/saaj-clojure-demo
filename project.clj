(defproject saaj-demo "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.6.0"]
                 [cider-ci/clj-utils "2.12.0"]
                 [org.clojure/tools.nrepl "0.2.9"]
                 [com.sun.xml.messaging.saaj/saaj-impl "1.3.25"]
                 ]
  
  :aot [saaj-demo.main] 
  :main saaj-demo.main
  )
