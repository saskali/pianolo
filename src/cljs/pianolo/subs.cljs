(ns pianolo.subs
  (:require [re-frame.core :as re-frame :refer [reg-sub subscribe]]))


(reg-sub
  :pieces
  (fn [db _]
    (case (:showing db)
      :now (filter :playing (vals (:pieces db)))
      :soon (remove :playing (vals (:pieces db)))
      (vals (:pieces db)))))

(reg-sub
  :showing
  (fn [db _]
    (:showing db)))