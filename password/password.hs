
module Password where

-- State monad with internal password state
newtype PwdOp a = PwdOp (String -> (a, String))

-- Make PwdOp an instance of Functor
instance Functor PwdOp where
    fmap f (PwdOp g) = PwdOp $ \s ->
        let (a, s') = g s in (f a, s')

-- Make PwdOp an instance of Applicative
instance Applicative PwdOp where
    pure x = PwdOp $ \s -> (x, s)
    (PwdOp f) <*> (PwdOp g) = PwdOp $ \s ->
        let (h, s1) = f s
            (x, s2) = g s1
        in (h x, s2)

-- Make PwdOp an instance of Monad
instance Monad PwdOp where
    return = pure
    (PwdOp g) >>= f = PwdOp $ \s ->
        let (a, s') = g s
            PwdOp h = f a
        in h s'

-- Updates the internal password
setPassword :: String -> PwdOp ()
setPassword pwd = PwdOp $ \_ -> ((), pwd)

-- Compares the current password with input
checkPassword :: String -> PwdOp Bool
checkPassword input = PwdOp $ \s -> (input == s, s)

-- Runs a password operation with initial password ""
runPwdOp :: PwdOp a -> a
runPwdOp (PwdOp f) = fst (f "")