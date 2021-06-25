(ns giggin.core
  (:require [reagent.core :as r]
            [giggin.components.orders :refer [orders]]
            [giggin.components.footer :refer [footer]]
            [giggin.components.header :refer [header]]
            [giggin.components.gigs :refer [gigs]]))

(defn app
  []
  [:div.container
   [header]
   [gigs]
   [orders]
   [footer]])

(defn ^:export main
  []
  (r/render
   [app]
   (.getElementById js/document "app")))
