(ns pianolo.db
  (:require [re-frame.core :as re-frame :refer [reg-cofx]]))

(def default-db
  {:pieces {}
   :showing :all})

(def ls-key "pieces")

(defn safe-key [key]
  (str "saskali.pianolo." key))

(defn pieces->local-store [pieces]
  (.setItem js/localStorage (safe-key ls-key) (str pieces)))

(def ->local-store (re-frame/after pieces->local-store))

(reg-cofx
  :local-store-pieces
  (fn [cofx _]
    (assoc cofx :local-store-pieces
                (into (sorted-map)
                      (some->> (.getItem js/localStorage (safe-key ls-key))
                               (cljs.reader/read-string))))))

