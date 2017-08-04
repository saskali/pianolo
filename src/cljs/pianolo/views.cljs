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

(defn skill [idx]
  (let [level (subscribe [:skill-level idx])]
    [:div.skill
      (if (> @level 1)
        [:div.icon.icon-circle
         {:type "button"
          :on-click #(dispatch [:level-down idx])}
         [svg/CircleLeftIcon]]
        [:div.icon.icon-circle.inactive
         {:type "button"}
         [svg/CircleLeftIcon]])
      [:div.level @level]
      (if (< @level 10)
        [:div.icon.icon-circle
         {:type "button"
          :on-click #(dispatch [:level-up idx])}
         [svg/CircleRightIcon]]
        [:div.icon.icon-circle.inactive
         {:type "button"}
         [svg/CircleRightIcon]])]))

(defn repertoire-item [idx piece]
  (let [title    (subscribe [:title idx])
        playing  (subscribe [:playing idx])]
    [:div.piece
     [:div.icon.icon-playing
      {:on-click #(dispatch [:set-playing idx])}
      (if @playing
        [svg/StarEmptyIcon]
        [svg/EyeIcon])]
     [:div.name @title]
     [skill idx]
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