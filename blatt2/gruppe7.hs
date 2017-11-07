module Hirsch where
    import Data.List

    atLeastElements list n = (length $ filter (>=n) list) >= n

    hIndexCorrect hIndex list = atLeastElements list x && not (atLeastElements list (x+1)) 
        where x = hIndex list

    hIndex [] = 0
    hIndex list = maximum (zipWith op [1..] xs)
        where xs = reverse (sort list)
              op i e 
               | e >= i = i
               | otherwise = 0

    hIndex' :: [Int] -> Int
    hIndex' = length . takeWhile id . zipWith (<=) [1..] . reverse . sort
    hIndex'' xs = length $ takeWhile id $ zipWith (<=) [1..] $ reverse $ sort
{-
    filter
    map
    zipWith
    zip
    length
    takeWhile
    iterate
-}