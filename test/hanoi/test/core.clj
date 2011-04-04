(ns hanoi.test.core
  (:use [lazytest.describe :only (describe it)])
  (:use [hanoi.core]))

(describe move
  (it "moves a single piece to the target"
    (= [1 (tower-map '() '(1) '())] (move '(1) '() '())))
  (it "moves a two piece stack to the target"
    (= [3 (tower-map '() '(1 2) '())] (move '(1 2) '() '())))
  (it "moves a 8 piece stack to the target"
    (= [255 (tower-map '() '(1 2 3 4 5 6 7 8) '())] (move '(1 2 3 4 5 6 7 8) '() '()))))

(describe smallest-seq-odd
  (it "moves in a cycle dest -> spare -> source"
    (= [:dest :spare :source :dest] (take 4 (smallest-seq-odd)))))

(describe tower-map
  (it "makes a map with the three towers named"
    (= {:dest '(), :spare '(), :source '(1 2 3)} (tower-map '(1 2 3) '() '()))))

(describe smallest-seq-even
  (it "moves in a cycle spare -> dest -> source"
    (= [:spare :dest :source :spare] (take 4 (smallest-seq-even)))))

(describe next-move
  (it "advances the smallest piece"
    (= (next-move 1 (smallest-seq-odd) (tower-map '(1 2 3) '() '())) [1 :source :dest]))
  (it "moves the top piece to the spare"
    (= (next-move 2 (smallest-seq-odd) (tower-map '(2 3) '(1) '())) [2 :source :spare]))
  (it "clears the smallest from dest"
    (= (next-move 3 (smallest-seq-odd) (tower-map '(3) '(1) '(2))) [1 :dest :spare]))
  (it "moves the biggest to the dest"
    (= (next-move 4 (smallest-seq-odd) (tower-map '(3) '() '(1 2))) [3 :source :dest]))
  (it "moves the smallest out of the way"
    (= (next-move 5 (smallest-seq-odd) (tower-map '() '(3) '(1 2))) [1 :spare :source]))
  (it "moves the middle to the dest"
    (= (next-move 6 (smallest-seq-odd) (tower-map '(1) '(3) '(2))) [2 :spare :dest]))
  (it "moves the smallest to dest"
    (= (next-move 7 (smallest-seq-odd) (tower-map '(1) '(2 3) '())) [1 :source :dest]))
  )

(describe finished?
  (it "knows when all the pieces have been moved"
    (= true (finished? (tower-map '() '(1 2 3 4) '()))))
  (it "knows when there are still pieces to be moved"
    (= false (finished? (tower-map'(4) '() '(1 2 3))))))

