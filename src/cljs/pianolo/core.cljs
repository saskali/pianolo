(ns pianolo.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defonce app-state (atom {:input ""}))

(defn title []
  [:div.title
   [:h1 "welcome to pianolo"]])

(defn input-field []
  [:div.input-field
   [:input {:type "field"
            :value (:input @app-state)
            :on-change #(swap! app-state assoc :input (-> % .-target .-value))}]])

(defn panel []
  [:div.panel
   [title]
   [input-field]])

(defn render []
  (reagent/render [panel] (js/document.getElementById "app")))
