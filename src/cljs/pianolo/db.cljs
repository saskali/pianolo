(ns pianolo.db
  (:require [re-frame.core :as re-frame :refer [reg-cofx]]))

(def ls-key "pieces")

;(defn pieces->local-store [pieces]
;  (.setItem js/localStorage ls-key (str pieces)))

(reg-cofx
  :local-store-pieces
  (fn [cofx _]
    (assoc cofx :local-store-pieces
                (into (sorted-map)
                      (some->> (.getItem js/localStorage ls-key)
                               (cljs.reader/read-string))))))

(def default-db
  {:pieces
   (reduce (fn [m piece-data]
             (let [id (random-uuid)]
               (assoc m id (assoc piece-data :id id))))
           {}
           [{:title "Toccata"
             :level 9
             :playing true}
            {:title "Pirates"
             :level 7
             :playing true}
            {:title "Moonlight"
             :level 9
             :playing true}])
   :showing :all})

