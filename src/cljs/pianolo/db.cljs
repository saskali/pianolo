(ns pianolo.db
  (:require [re-frame.core :as re-frame :refer [reg-cofx]]))

(def ls-key "pieces")

(defn pieces->local-store [pieces]
  (.setItem js/localStorage ls-key (str pieces)))

(def ->local-store (re-frame/after pieces->local-store))

(reg-cofx
  :local-store-pieces
  (fn [cofx _]
    (assoc cofx :local-store-pieces
                (into (sorted-map)
                      (some->> (.getItem js/localStorage ls-key)
                               (cljs.reader/read-string))))))

(def default-db
  {:pieces {}
   :showing :all})

