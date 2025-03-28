public class ParserImpl extends Parser {

    @Override
    public Expr do_parse() throws Exception {
        return parseT();
    }

    private Expr parseT() throws Exception {
        Expr f = parseF();
        if (peek(TokenType.PLUS, 0)) {
            consume(TokenType.PLUS);
            Expr t = parseT();
            return new PlusExpr(f, t);
        } else if (peek(TokenType.MINUS, 0)) {
            consume(TokenType.MINUS);
            Expr t = parseT();
            return new MinusExpr(f, t);
        } else {
            return f;
        }
    }

    private Expr parseF() throws Exception {
        Expr lit = parseLit();
        if (peek(TokenType.TIMES, 0)) {
            consume(TokenType.TIMES);
            Expr f = parseF();
            return new TimesExpr(lit, f);
        } else if (peek(TokenType.DIV, 0)) {
            consume(TokenType.DIV);
            Expr f = parseF();
            return new DivExpr(lit, f);
        } else {
            return lit;
        }
    }

    private Expr parseLit() throws Exception {
        if (peek(TokenType.NUM, 0)) {
            Token tok = consume(TokenType.NUM);
            return new FloatExpr(Float.parseFloat(tok.lexeme));
        } else if (peek(TokenType.LPAREN, 0)) {
            consume(TokenType.LPAREN);
            Expr t = parseT();
            consume(TokenType.RPAREN);
            return t;
        } else {
            throw new Exception("Expected NUM or LPAREN");
        }
    }
}
