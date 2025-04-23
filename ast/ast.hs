
module Ast where
data Expr
  = Plus   Expr Expr
  | Minus  Expr Expr
  | Times  Expr Expr
  | Div    Expr Expr
  | Literal Float
  deriving (Eq)

eval :: Expr -> Float
eval expr = case expr of
  Literal x     -> x
  Plus   a b    -> eval a + eval b
  Minus  a b    -> eval a - eval b
  Times  a b    -> eval a * eval b
  Div    a b    -> eval a / eval b

eq :: Expr -> Expr -> Bool
eq = (==)

test1 :: Expr
test1 = Plus (Literal 3.0) (Literal 2.0)

test2 :: Expr
test2 = Plus (Literal 3.0) (Div (Literal 1.0) (Literal 2.0))

test3 :: Expr
test3 = Plus (Times (Literal 3.0) (Literal 5.0))
             (Div   (Literal 1.0) (Literal 2.0))