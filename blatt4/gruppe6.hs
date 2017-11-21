import Data.List

data Tree a = Leaf | Node (Tree a) a (Tree a) deriving Show



deleteMin :: Tree a -> (a, Tree a)
deleteMin (Leaf) = error "bla"
deleteMin (Node Leaf a right) = (a, right)
deleteMin (Node left a right) = (min, Node rest a right)
    where (min, rest) = deleteMin left

merge :: Tree a -> Tree a -> Tree a
merge left Leaf = left
merge left right = Node left root rest
    where (root, rest) = deleteMin right


listToTree :: Ord a => [a] -> Tree a
listToTree = sortedListToTree . sort
    where
        sortedListToTree :: Ord a => [a] -> Tree a
        sortedListToTree [] = Leaf
        sortedListToTree [e] = Node Leaf e Leaf
        sortedListToTree xs = Node left root right
            where
                middle = length xs `div` 2
                left = sortedListToTree $ take middle xs
                rightL = drop middle xs
                root = head rightL
                right = sortedListToTree $ tail rightL