(ns pianolo.events
  (:require [re-frame.core :as re-frame :refer [reg-event-db register-handler]]))

(reg-event-db
  :initialize-db
  (fn  [_ _]
    {:pieces [{:id (random-uuid)
               :title "Toccata"
               :level 9
               :playing true}
              {:id (random-uuid)
               :title "Pirates"
               :level 7
               :playing true}
              {:id (random-uuid)
               :title "Moonlight"
               :level 9
               :playing true}]}))

(reg-event-db
  :save-piece
  (fn [db [_ title]]
    (update db :pieces conj {:id (random-uuid)
                             :title title
                             :level 1})))

(reg-event-db
  :remove-piece
  (fn [db [_ idx]]
    (let [pieces (:pieces db)]
      (assoc db :pieces (-> (subvec pieces 0 idx)
                            (into (subvec pieces (inc idx))))))))

(reg-event-db
  :level-down
  (fn [db [_ idx]]
    (update-in db [:pieces idx :level] dec)))

(reg-event-db
  :level-up
  (fn [db [_ idx]]
    (update-in db [:pieces idx :level] inc)))

(reg-event-db
  :set-playing
  (fn [db [_ idx]]
    (update-in db [:pieces idx :playing] not)))