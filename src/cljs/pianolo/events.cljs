(ns pianolo.events
  (:require [re-frame.core :as re-frame :refer [reg-event-db register-handler]]
            [pianolo.persistence :as localstorage]))

(reg-event-db
  :initialize-db
  (fn  [_ _]
    (if (nil? (localstorage/get "app-db"))
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
       :showing :all}
      (localstorage/get "app-db"))))

(reg-event-db
  :set-showing
  (fn [db [_ filter-keyword]]  ;; filter-keyword can be :all, :now or :soon
    (assoc db :showing filter-keyword)))

(reg-event-db
  :save-piece
  (fn [db [_ title]]
    (let [id (random-uuid)]
      (assoc-in db [:pieces id]
                {:id id
                 :level 1
                 :title title
                 :playing false}))))

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