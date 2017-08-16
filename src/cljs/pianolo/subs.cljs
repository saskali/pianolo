(ns pianolo.subs
  (:require [re-frame.core :as re-frame :refer [reg-sub subscribe]]))

(reg-sub
  :pieces
  (fn [db _]
    (:pieces db)))

(reg-sub
  :title
  (fn [query-v]
    (subscribe [:pieces]))
  (fn [pieces [_ piece-id]]
    (:title (get pieces piece-id))))

(reg-sub
  :skill-level
  (fn [query-v]
    (subscribe [:pieces]))
  (fn [pieces [_ piece-id]]
    (:level (get pieces piece-id))))

(reg-sub
  :playing
  (fn [query-v]
    (subscribe [:pieces]))
  (fn [pieces [_ piece-id]]
    (:playing (get pieces piece-id))))