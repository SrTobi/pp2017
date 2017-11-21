import Data.List

data Tree a = Leaf | Node (Tree a) a (Tree a) deriving Show

deleteMin :: Tree a -> (a, Tree a)
deleteMin Leaf = error "bla"
deleteMin (Node Leaf x right) = (x, right)
deleteMin (Node left x right) = (min, Node rest x right)
    where
        (min, rest) = deleteMin left

merge :: Tree a -> Tree a -> Tree a
merge a Leaf = a
merge a b = Node a root rest
    where
        (root, rest) = deleteMin b

list2Tree :: Ord a => [a] -> Tree a
list2Tree = sortedList2Tree . sort
    where
        sortedList2Tree :: Ord a => [a] -> Tree a
        sortedList2Tree [] = Leaf
        sortedList2Tree [e] = Node Leaf e Leaf
        sortedList2Tree xs = Node left root right
            where
                middle = length xs `div` 2
                left = sortedList2Tree $ take middle xs
                (root:rightL) = drop middle xs
                right = sortedList2Tree $ rightL