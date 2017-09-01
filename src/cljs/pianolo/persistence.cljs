(ns pianolo.persistence
  (:require [cljs.reader :as reader]
            [re-frame.db :refer [app-db]]
            [goog.storage.Storage :as Storage]
            [goog.storage.mechanism.HTML5LocalStorage :as html5localstore])
  (:refer-clojure :exclude [get]))

(def storage (goog.storage.Storage. (goog.storage.mechanism.HTML5LocalStorage.)))

(defn- safe-key [key]
  (str "saskali.pianolo." key))

(defn save! [key value]
  (.set storage (safe-key key) (pr-str value)))

(defn get
  ([key]
   (get key nil))
  ([key not-found]
   (let [value (.get storage (safe-key key))]
     (if (undefined? value)
       not-found
       (reader/read-string value)))))

(add-watch app-db
           ::db
           (fn [_ _ _ new-state]
             (save! "app-db" new-state)))
