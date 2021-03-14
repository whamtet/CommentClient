(ns android.core
  (:require
    [android.storage :as storage]
    [android.util :as util]
    cljs.reader
    [ctmx.intercept :as intercept]
    [ctmx.render :as render]
    ctmx.rt)
  (:require-macros
    [ctmx.core :as ctmx]))

(enable-console-print!)

(ctmx/defcomponent ^:endpoint panel [req]
  [:div "hi"])

(def req {:params {}})

(ctmx/defstatic main []
  (set! js/document.body.innerHTML
        (render/html
          [:div.container {:hx-ext "intercept"}
           (panel req)])))

(intercept/set-responses!
  (ctmx/metas main))

(set! (.-defaultSettleDelay js/htmx.config) 0)
(set! (.-defaultSwapStyle js/htmx.config) "outerHTML")
(set! (.-includeIndicatorStyles js/htmx.config) false)
(main)

