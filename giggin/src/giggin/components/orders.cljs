(ns giggin.components.orders
  (:require [giggin.state :as state]
            [giggin.helpers :as helpers]))

(defn order-img [id]
  [:div.img
   [:img {:src (get-in @state/gigs [id :img])
          :alt (get-in @state/gigs [id :title])}]])

(defn order-content [id quantity]
  "Title of the order"
  [:div.content
   [:p.title (str (get-in @state/gigs [id :title]) " \u00D7" quantity)]])

(defn order-price
  "DOM side-bar of prices/actions"
  [id quantity]
  [:div.price (helpers/format-price
               (* (get-in @state/gigs [id :price])
                  quantity))])

(defn order-cancel-btn
  [id]
  [:button.btn.btn--link.tooltip
   {:data-tooltip "Remove order"
    :on-click (fn [] (swap! state/orders dissoc id))}
   [:i.icon.icon--cross]])

;; ideia: to show isomorphism, highlight the equivalence => TCC.  
(defn total
  []
  (->> @state/orders
       (map (fn
              [[id quant]]
              (* quant (get-in @state/gigs [id :price]))))
       (reduce +)))
  ;; (reduce + (map (fn
  ;;                  [[id quantity]]
  ;;                  (* quantity (get-in @state/gigs [id :price])))
  ;;                @state/orders)))

(defn order-total-price
  []
  [:div.total
   [:hr]
   [:div.item
    [:div.content "Total"]
    [:div.action
     [:div.price (helpers/format-price (total))]]
    [:button.btn.btn--link.tooltip
     {:data-tooltip "Remove all"
      :on-click (fn [] (reset! state/orders {}))}
     [:i.icon.icon--delete]]]])

(defn empty-order []
  [:div.empty
   [:div.title "Você não possui compras"]
   [:div.subtitle "Clicke em +, para adicionar à compra"]])

(defn orders
  "Render order's side panel"
  []
  [:aside
   [:div.order
    [:div.body
     (for [[id quantity] @state/orders] ;;=>
       [:div.item {:key id}
        (order-img id)
        (order-content id quantity)
        [:div.action
         (order-price id quantity)
         (order-cancel-btn id)]])]
    (if (empty? @state/orders)
      (empty-order)
      (order-total-price))]])

    ;; (if (empty? @state/orders)
      ;; []
      ;; (order-total-price))]])
;; (fn [id]
;;   (dec (get-in @state/orders [id])))

;; (defn dec-id [id] (dec (id @state/orders)))

;; [:button.btn.btn--link.tooltip
;;  {:data-tooltip "Remove one item"
;;   :onclick (fn []
;;              (swap! state/orders id))}
;;  [:i.icon.icon--plus]]
