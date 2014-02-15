(ns dp
  ( :require-macros
    [cljs.core.async.macros :refer [go]]
    [hiccups.core :as hiccups])
  ( :require
    [cljs.core.async :refer [put! chan <!]]
    [clojure.data :as data]
    [clojure.string :as string]
    [hiccups.runtime :as hiccupsrt]))

(def is-node (and (exists? js/process)))
(def is-node-webkit
  (try (when (js/require "nw.gui") true)
    (catch js/Object e false)))
(def is-browser (and (exists? js/navigator)
                     (exists? js/navigator.userAgent)))

(.log js/console "isNode" is-node)
(.log js/console  (hiccups/html [:span "hello"]))

(when is-browser
  (aset js/document.body "innerHTML" "Hello"))

(def default-header-tags
  [[:meta {:http-equiv "content-type" :content "text/html;charset=UTF-8"}]
   [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
   [:meta {:name "viewport"
           :content (str "width=device-width,"
                         "initial-scale=1.0,"
                         "minimum-scale=1.0,"
                         "maximum-scale=1.0,"
                         "user-scalable=0")}]
   [:meta {:name "format-detection" :content "telephone=no"}]])

(hiccups/html
 [:html
  ( into
    [:head [:title "solsort.com"]]
    default-header-tags)
  [:body
   [:div "blah"]]])
;;;;
(when is-node
  (defn handle-request [req res]
    (.log js/console req res)
    (doto res
      (.writeHead 200 #js {"Content-Type" "test/html"})
      (.end "Hello2!\n"))))
(when is-node
  (-> (js/require "http")
      (.createServer #(handle-request %1 %2))
      (.listen 9999)))

;<!DOCTYPE html>
;<html>
;    <head>
;        <title>solsort.com</title>
;        <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
;        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
;        <meta name="HandheldFriendly" content="True" />
;        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" />
;        <meta name="format-detection" content="telephone=no" />
;    </head>
;    <body>
;        <script src="target/plain/goog/base.js"></script>
;        <script src="target/plain/main.js"></script>
;        <script>goog.require("dp");</script>
;        <img src="https://ssl.solsort.com/_dp_cljs.png">
;        <script id='lt_ws' src='http://localhost:48392/socket.io/lighttable/ws.js'></script>
;    </body>
;</html>
