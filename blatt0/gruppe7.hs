

max3if x y z = if x >= y && x>=z then x
                else if y >= z then y
                     else z


max3guard x y z
    |x >= y && x >= z = x
    |y >= z = y
    |otherwise = z


max3max x y z = ((max x) . (max y)) z

listSum [] = 1
listSum (x:xs) = x + listSum xs 

listSumAcc [] acc = acc
listSumAcc (x:xs) acc = listSumAcc xs (x + acc)
listSum' xs = listSumAcc xs 0

isPrime a 
    | a <= 1 = False
    | otherwise = prime' (a-1)
    where prime' n
            | n <= 1 = True
            | a `mod` n == 0 = False
            | otherwise = prime' (n-1)

primes x = filter isPrime [1..x]

