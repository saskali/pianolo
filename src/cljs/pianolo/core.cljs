(ns pianolo.core
  (:require [reagent.core :as r]))

(enable-console-print!)

(defonce app-state (r/atom {:input ""
                            :pieces [{:title "Toccata"}]}))

(defn title []
  [:div.title
   [:h1 "welcome to pianolo"]])

(defn input-field []
  (let [pieces-cursor (r/cursor app-state [:pieces ])]
    [:div.input-field
     [:input {:type "field"
              :value (:input @app-state)
              :on-change #(swap! app-state assoc :input (-> % .-target .-value))
              :on-key-down #(if (= 13 (.-which %))
                              (swap! app-state update-in [:pieces] conj {:title (-> % .-target .-value)}))}]]))

(defn repertoire [app-state]
  [:div.repertoire
   (for [piece (:pieces @app-state)]
     [:div (:title piece)])])

(defn panel [app-state]
  [:div.panel
   [title]
   [input-field]
   [repertoire app-state]])

(defn render []
  (r/render [panel app-state] (js/document.getElementById "app")))
