(ns stock)

(defmulti modify-stock
  (fn [stock action] (first action)))

(defmethod modify-stock :add [stock [_ item quantity]]
  (update stock item #(+ (or % 0) quantity)))

(defmethod modify-stock :remove [stock [_ item quantity]]
  (update stock item - quantity))

(def audit (atom [[:add "sta soft" 2]
                  [:remove "sta soft" 1]]))

(defn count-stock [audit]
  (reduce #(modify-stock %1 %2) {} audit))

;;(swap! mem assoc args ret)
;;(find @mem args)

(comment
  (modify-stock {} [:add "sta soft" 1])

  (-> {}
      (modify-stock [:add "sta soft" 1])
      (modify-stock [:add "sta soft" 1])
      (modify-stock [:remove "sta soft" 1]))

  (count-stock @audit)

  )
