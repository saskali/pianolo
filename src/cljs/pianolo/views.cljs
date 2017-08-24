(ns pianolo.views
  (:require   [reagent.core :as r]
              [re-frame.core :as re-frame :refer [subscribe dispatch]]
              [pianolo.components.svg :as svg]))

(def <sub (comp deref subscribe))

(defn header-title []
  [:div.header-title
   [:h1 "Pianolo"]])

(defn filter-pieces []
  (let [showing (<sub [:showing])
        a-fn    (fn [filter-keyword string]
                  [:a {:class (when (= filter-keyword showing)
                                "selected")
                       :href (str "#/" (name filter-keyword))}
                   string])]

    [:ul.menu
     [:li.showing (a-fn :all "ALL")]
     [:li.showing (a-fn :now "NOW")]
     [:li.showing (a-fn :soon "SOON")]]))

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

(defn skill [{:keys [id level]}]
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
       [svg/CircleRightIcon]])])

(defn repertoire-item [{:keys [id title playing] :as item}]
  [:div.piece
   [:div.icon.icon-playing
    {:on-click #(dispatch [:set-playing id])}
    (if playing
      [svg/StarEmptyIcon]
      [svg/EyeIcon])]
   [:div.name title]
   [skill item]
   [:div.icon-remove {:type "button"
                      :on-click #(dispatch [:remove-piece id])}
    [svg/RemoveIcon]]])

(defn repertoire []
  (let [pieces (<sub [:pieces])]
    [:div.repertoire
     (map (fn [{:keys [id] :as item}]
            ^{:key id} [repertoire-item item])
          pieces)]))

(defn panel []
  [:div.panel
   [header-title]
   [filter-pieces]
   [input-field]
   [repertoire]])