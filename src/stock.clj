(ns stock)

(defmulti modify-stock
  (fn [stock [action _ _]] action))

(defmethod modify-stock :add [stock [_ item quantity]]
  (update stock item #(+ (or % 0) quantity)))

(defmethod modify-stock :remove [stock [_ item quantity]]
  (update stock item - quantity))

(def audit (atom [[:add "sta soft" 2 "Alex"]
                  [:remove "sta soft" 1 "Daphne"]]))

(defn append-audit-trail [audit item]
  (swap! audit concat [item]))

(defn count-stock [audit]
  (reduce #(modify-stock %1 %2) {} audit))

(comment
  (reset! audit [[:add "sta soft" 2 "Alex"]
                [:remove "sta soft" 1 "Daphne"]])

  (modify-stock {} [:add "sta soft" 1])

  (-> {}
      (modify-stock [:add "sta soft" 1])
      (modify-stock [:add "sta soft" 1])
      (modify-stock [:remove "sta soft" 1]))

  (append-audit-trail audit [:remove "sta soft" 1 "Daphne"])

  (count-stock @audit)

  (clojure.pprint/pprint @audit)

  )
