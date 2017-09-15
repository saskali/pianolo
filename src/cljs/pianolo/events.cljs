(ns pianolo.events
  (:require [re-frame.core :as re-frame :refer [reg-event-db register-handler reg-event-fx inject-cofx]]
            [pianolo.db :as db :refer [default-db]]))

(reg-event-fx
  :initialize-db
  [(inject-cofx :local-store-pieces)]
  (fn [{:keys [db local-store-pieces]} _]
    {:db (update default-db :pieces merge (:pieces local-store-pieces))}))

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