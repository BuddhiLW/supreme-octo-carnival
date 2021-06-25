(ns giggin.components.gigs
  (:require [giggin.state :as state]
            [giggin.helpers :as helpers]))

;; (defn populate-main
;;   [gig]
;;   [:div.gig {:key (:id gig)}
;;    [:img.gig__artwork {:src (get gig :img) :alt (:title gig)}]
;;    [:div.gig__body
;;     [:div.gig__title
;;      [:div.btn.btn--primary.float--right.tooltip
;;       {:data-tooltip "Add to order"}
;;       [:i.icon.icon--plux]]
;;      (:title gig)]
;;     [:p.gig__price (:price gig)]
;;     [:p.gig__desc (:desc gig)]]])

;; (defn gigs
;;   []
;;   [:main
;;    [:div.gigs (map populate-main
;;                    (vals @state/gigs))]])

;; (defn gigs
;;   []
;;   [:main
;;    [:div.gigs
;;     (for [gig (vals @state/gigs)]
;;       (populate-main gig))]])




;;--- Destructured way

;;--- Partitioning functions
(defn back-art [img title]
   [:img.gig__artwork {:src img :alt title}])

(defn header-render
  "Render the title in the cards"
  [title id]
  [:div.gig__title
   [:div.btn.btn--primary.float--right.tooltip
    {:data-tooltip "Add to order"
     :on-click (fn [] (swap! state/orders update id inc))}
    [:i.icon.icon--plus]]
   title])

(defn gen-gig-render
  "Generic render"
  [var]
  (fn [var]
    [(re-pattern (str "p.gig__" var)) var]))

(defn price-render
  "Render the price"
  [price]
  [:p.gig__price (helpers/format-price price)])

(defn desc-render 
  "Render the destription"
  [desc]
  [:p.gig__desc
   desc])

(defn gig-body-render
  [title desc price id]
  [:div.gig__body
   ;; (map gen-gig-render [title price desc])
   (header-render id title)
   (desc-render desc)
   (price-render price)])

;; --- Main function to populate the gig, in the page. 

(defn populate-main-dest
  [id img title price desc]
  [:div.gig {:key id}
   (back-art img title)
   (gig-body-render id title price desc)])

;; --- Using =populate-main-dest=
(defn gigs
  []
  [:main
   [:div.gigs
    (for [{:keys [id img title price desc]} (vals @state/gigs)]
      (populate-main-dest id img title price desc))]])
