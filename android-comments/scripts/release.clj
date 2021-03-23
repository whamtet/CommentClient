(require '[cljs.build.api :as b])

(println "Building ...")
(def prefix "../app/src/main/res/raw")

(let [start (System/nanoTime)]
  (b/build "src"
           {:output-to (str prefix "/android.js")
            :optimizations :simple
            :verbose true})
  (println "... done. Elapsed" (/ (- (System/nanoTime) start) 1e9) "seconds"))
