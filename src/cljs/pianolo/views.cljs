(ns pianolo.views
  (:require   [reagent.core :as r]
              [re-frame.core :as re-frame :refer [subscribe dispatch]]
              [pianolo.components.svg :as svg]))

(defn header-title []
  [:div.header-title
   [:h1 "welcome to pianolo"]])

(defn input-field []
  (let [input (r/atom "")]
    (fn []
      [:div.input-field
       [:input {:type "field"
                :value @input
                :on-change #(reset! input (-> % .-target .-value))
                :on-key-down #(if (and (= 13 (.-which %))
                                       (not (empty? @input)))
                                (do
                                  (dispatch [:save-piece @input])
                                  (reset! input "")))}]])))

(defn repertoire-item [idx piece]
  (let [title  (subscribe [:title idx])
        level  (subscribe [:skill-level idx])]
    [:div.piece
     [:div.name @title]
     ;[skill @level]
     [:div.icon-remove {:type "button"
                        :on-click #(dispatch [:remove-piece idx])}
      [svg/RemoveIcon]]]))

(defn repertoire []
  (let [pieces (subscribe [:pieces])]
    (fn []
      [:div.repertoire
       (map-indexed (fn [idx {id :id}]
                      ^{:key id} [repertoire-item idx])
                    @pieces)])))

(defn panel []
  [:div.panel
   [header-title]
   [input-field]
   [repertoire]])