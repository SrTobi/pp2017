diff :: Eq t => [t] -> [t] -> [t]
diff a b = filter (`notElem` b) a

intersect :: Eq t => [t] -> [t] -> [t]
intersect a b = filter (`elem` b) a

union a b = a ++ diff b a
a `subset` b = null (diff a b)


type Turn = Int
type State = ([Turn], [Turn])


isWin (cur, next) = isWinner cur || isWinner next
  where
    isWinner turns = any (`subset` turns) wins
    wins = [
      [0, 1, 2],
      [3, 4, 5],
      [6, 7, 8],
      [0, 3, 6],
      [1, 4, 7],
      [2, 5, 8],
      [0, 4, 8],
      [2, 4, 6]]
      
      

playGames :: State -> (Int, Int)
playGames state
  | isWin state = (1, 0)
  | null free = (0, 1)
  | otherwise = (sum wins, sum draws)
  where
    (wins, draws) = unzip $ map (playGames . nextState state) free
    nextState :: State -> Turn -> State
    nextState (cur, next) turn = (next, turn:cur)
    free :: [Turn]
    free = [0..8] `diff` occupied
    occupied = a `union` b
      where (a, b) = state


