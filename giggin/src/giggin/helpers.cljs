(ns giggin.helpers)

(defn format-price
  [centavos]
  (str " R$" (/ centavos 100)))
