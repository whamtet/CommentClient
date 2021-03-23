#!/bin/sh
rlwrap lein trampoline run -m clojure.main scripts/release.clj
rm -r ../app/src/main/assets/www/out
#cp release/android.js ../app/src/main/assets/www
#cp index_release.html ../app/src/main/assets/www/index.html
#cp bootstrap.min.css ../app/src/main/assets/www/
#cp loading.gif ../app/src/main/assets/www/

cp htmx.js ../app/src/main/res/raw
