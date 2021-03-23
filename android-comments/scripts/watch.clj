(require '[cljs.build.api :as b])

(def prefix "../app/src/main/res/raw")

(b/watch "src"
         {:main 'android.core
          :output-to (str prefix "/android.js")
          :optimizations :whitespace
          :asset-path "out"})
