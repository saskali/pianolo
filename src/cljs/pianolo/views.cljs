(ns pianolo.views
  (:require   [reagent.core :as r]
              [re-frame.core :as re-frame :refer [subscribe dispatch]]
              [pianolo.components.svg :as svg]))

(def <sub (comp deref subscribe))

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

(defn skill [id]
  (let [level (<sub [:skill-level id])]
    [:div.skill
      (if (> level 1)
        [:div.icon.icon-circle
         {:type "button"
          :on-click #(dispatch [:level-down id])}
         [svg/CircleLeftIcon]]
        [:div.icon.icon-circle.inactive
         {:type "button"}
         [svg/CircleLeftIcon]])
      [:div.level level]
      (if (< level 10)
        [:div.icon.icon-circle
         {:type "button"
          :on-click #(dispatch [:level-up id])}
         [svg/CircleRightIcon]]
        [:div.icon.icon-circle.inactive
         {:type "button"}
         [svg/CircleRightIcon]])]))

(defn repertoire-item [id]
  (let [title   (<sub [:title id])
        playing (<sub [:playing id])]
    [:div.piece
     [:div.icon.icon-playing
      {:on-click #(dispatch [:set-playing id])}
      (if playing
        [svg/StarEmptyIcon]
        [svg/EyeIcon])]
     [:div.name title]
     [skill id]
     [:div.icon-remove {:type "button"
                        :on-click #(dispatch [:remove-piece id])}
      [svg/RemoveIcon]]]))

(defn repertoire []
  (let [pieces (<sub [:pieces])]
    [:div.repertoire
     (map (fn [[id _]]
              ^{:key id} [repertoire-item id])
          pieces)]))

(defn panel []
  [:div.panel
   [header-title]
   [input-field]
   [repertoire]])