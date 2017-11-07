module Hirsch where
    import Data.List


    atLeastElements l n = (length $ filter (>=n) l)>= n
    
    hIndexCorrect h xs = atLeastElements xs i && not (atLeastElements xs (i + 1))
        where i = h xs
    
    hIndex xs = length $ takeWhile (\(i, e) -> e >= i) $ zip [1..] (reverse $ sort xs)
    hIndex' :: [Int] -> Int
    hIndex' xs = length $ takeWhile id $ zipWith (<=) [1..] $  reverse $ sort xs
    {-
        sum
        filter
        map
        zipWith
        zip
        take
        drop
        takeWhile
        length
    -}