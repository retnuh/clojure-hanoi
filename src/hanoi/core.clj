(ns hanoi.core)

(defn finished? "Detects when everything has been moved"
  [{:keys [source dest spare]}]
  (and (empty? source) (empty? spare) (not (empty? dest))))

(defn smallest-seq-odd
  "The infinite sequence of moves the smallest piece makes when there are an odd number of pieces"
  []
  (lazy-seq (concat [:dest :spare :source] (smallest-seq-odd))))

(defn smallest-seq-even
  "The infinite sequence of moves the smallest piece makes when there are an even number of pieces"
  []
  (lazy-seq (concat [:spare :dest :source] (smallest-seq-even))))

(defn tower-map
  "Makes a map with the three towers"
  [source dest spare]
  {:source source :dest dest :spare spare})


(defn move-middle "moves the smaller of the two pieces onto the larger (where an empty tower is considered infinitely large)"
  [towers]
  (let [[[akey aval] [bkey bval]] (seq towers)]
    (cond (empty? aval) [bkey akey]
          (empty? bval) [akey bkey]
          (< (first aval) (first bval)) [akey bkey]
          true [bkey akey])))

(defn next-move "make the next move"
  [move-count smallest-seq towers]
  (cond (= 1 move-count) [(first (towers :source)) :source (first smallest-seq)]
        (odd? move-count) (let [s (drop (dec (quot move-count 2)) smallest-seq)
                                prev (first s)
                                next (first (rest s))]
                            [(first (towers prev)) prev next])
        true (let [smallest-pos (nth smallest-seq (dec (quot move-count 2)))
                   [from to] (move-middle (dissoc towers smallest-pos))]
               [(first (towers from)) from to])))

(defn apply-move "apply a move instruction to the towers"
  [[num from to] towers]
  (println towers )
  (assoc towers from (rest (towers from)) to (conj (towers to) (first (towers from)))))

(defn move "moves tower from source to dest using spare"
  [source dest spare]
  (let [c (count source)
        smallest-seq (if (even? c) (smallest-seq-even) (smallest-seq-odd))]
    (loop [move-count 1
           moves []
           towers (tower-map source dest spare)]
      (if (finished? towers)
        [(dec move-count) towers]
        (let [move (next-move move-count smallest-seq towers)]
          (recur (inc move-count) (conj moves move) (apply-move move towers)))))))
