(ns pianolo.core
  (:require [reagent.core :as r]
            [pianolo.components.svg :as svg]))


(enable-console-print!)

(defonce app-state (r/atom {:input ""
                            :pieces [{:id (random-uuid)
                                      :title "Toccata"
                                      :level 9}]}))

(defn header-title []
  [:div.header-title
   [:h1 "welcome to pianolo"]])

(defn input-field [rep-state]
  [:div.input-field
   [:input {:type "field"
            :value (:input @rep-state)
            :on-change #(swap! rep-state assoc :input (-> % .-target .-value))
            :on-key-down #(if (= 13 (.-which %))
                            (do
                              (swap! rep-state assoc :input "")
                              (swap! rep-state update :pieces conj {:id (random-uuid)
                                                                    :title (-> % .-target .-value)
                                                                    :level 1})))}]])

(defn skill [rep-state idx]
  (let [level-cur (r/cursor rep-state [:pieces idx :level])]
    [:div.skill
     (if (> @level-cur 1)
       [:div.icon-circle
        {:type "button"
         :on-click (fn [] (swap! level-cur dec))}
        [svg/CircleLeftIcon]]
       [:div.icon-circle.inactive
        {:type "button"}
        [svg/CircleLeftIcon]])
     [:div.level @level-cur]
     (if (< @level-cur 10)
       [:div.icon-circle
        {:type "button"
         :on-click (fn [] (swap! level-cur inc))}
        [svg/CircleRightIcon]]
       [:div.icon-circle.inactive
        {:type "button"}
        [svg/CircleRightIcon]])]))

(defn repertoire-item [rep-state idx]
  (let [title (get-in @rep-state [:pieces idx :title])]
    [:div.piece
     [:div.name title]
     [skill rep-state idx]
     [:div.icon-remove {:type "button"
                        :on-click (fn []
                                    (swap! rep-state update :pieces
                                           #(vec (remove (fn [item] (= title (:title item))) (:pieces @rep-state)))))}
                       [svg/RemoveIcon]]]))

(defn repertoire [rep-state]
  [:div.repertoire
   (map-indexed (fn [idx {id :id}]
                  ^{:key id} [repertoire-item rep-state idx])
                (:pieces @rep-state))])

(defn panel [rep-state]
  [:div.panel
   [header-title]
   [input-field rep-state]
   [repertoire rep-state]])

(defn render []
  (r/render [panel app-state] (.getElementById js/document "app")))