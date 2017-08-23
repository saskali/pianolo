(ns pianolo.events
  (:require [re-frame.core :as re-frame :refer [reg-event-db register-handler]]))

(reg-event-db
  :initialize-db
  (fn  [_ _]
    {:pieces
     (reduce (fn [m piece-data]
               (assoc m (random-uuid) piece-data))
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
     :display {}}))

(reg-event-db
  :save-piece
  (fn [db [_ title]]
    (assoc-in db [:pieces (random-uuid)]
              {:title title
               :level 1
               :playing false})))

(reg-event-db
  :remove-piece
  (fn [db [_ id]]
    (update db :pieces dissoc id)))

(reg-event-db
  :level-down
  (fn [db [_ id]]
    (update-in db [:pieces id :level] dec)))

(reg-event-db
  :level-up
  (fn [db [_ id]]
    (update-in db [:pieces id :level] inc)))

(reg-event-db
  :set-playing
  (fn [db [_ id]]
    (update-in db [:pieces id :playing] not)))