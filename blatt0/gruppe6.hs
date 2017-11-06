


max3if x y z = if x >= y && x >= z
    then x
    else if y >= x && y >= z
        then y
        else z

max3Guard x y z 
 | ( x >= y && x >= z) = x
 | y >= x && y >= z = y
 | otherwise = x

max3max x y z = max x (max y z)

listSum [] = 0
listSum (x:xs) = x + listSum xs

listSum' xs = listSum'' xs 0
listSum'' (x:xs) acc = listSum'' xs (acc + x)
listSum'' [] acc = acc



main = putStr "tes"

max3if x y z = if x >= y && x >= z
    then x
    else if y >= x && y >= z
        then y
        else z

max3Guard x y z 
 | ( x >= y && x >= z) = x
 | y >= x && y >= z = y
 | otherwise = x

max3max x y z = max x (max y z)

listSum [] = 0
listSum (x:xs) = x + listSum xs

listSum' xs = listSum'' xs 0
listSum'' (x:xs) acc = listSum'' xs (acc + x)
listSum'' [] acc = acc

divideable' x 1 = False
divideable' x n = x `mod` n == 0 || divideable' x (n-1)
divideable x = divideable' x (x-1)


primes x
  | (x < 2) = error "No numbers smaller 2" 
  | (divideable x) =primes (x-1)
  |otherwise =  x : primes (x-1)




