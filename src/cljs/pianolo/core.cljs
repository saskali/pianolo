(ns pianolo.core
  (:require [reagent.core :as r]
            [re-frame.core :as re-frame :refer [subscribe dispatch]]
            [devtools.core :as devtools]
            [pianolo.config :as config]
            [pianolo.views :as views]
            [pianolo.events]
            [pianolo.subs]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")
    (devtools/install!)))

(defn mount-root []
  (r/render [views/panel] (.getElementById js/document "app")))

(defn render []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
