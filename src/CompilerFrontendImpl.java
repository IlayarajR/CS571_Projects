public class CompilerFrontendImpl extends CompilerFrontend {
    public CompilerFrontendImpl() {
        super();
    }

    public CompilerFrontendImpl(boolean debug_) {
        super(debug_);
    }

    @Override
    protected void init_lexer() {
        lex = new LexerImpl();

        // NUM: [0-9]*\.[0-9]+
        Automaton a_num = new AutomatonImpl();
        a_num.addState(0, true, false);
        a_num.addState(1, false, false);
        a_num.addState(2, false, true);
        for (char c = '0'; c <= '9'; c++) {
            a_num.addTransition(0, c, 0);
            a_num.addTransition(0, c, 1);
            a_num.addTransition(1, c, 1);
            a_num.addTransition(2, c, 2);
        }
        a_num.addTransition(0, '.', 2);
        a_num.addTransition(1, '.', 2);
        lex.add_automaton(TokenType.NUM, a_num);

        // PLUS: \+
        Automaton a_plus = new AutomatonImpl();
        a_plus.addState(0, true, false);
        a_plus.addState(1, false, true);
        a_plus.addTransition(0, '+', 1);
        lex.add_automaton(TokenType.PLUS, a_plus);

        // MINUS: -
        Automaton a_minus = new AutomatonImpl();
        a_minus.addState(0, true, false);
        a_minus.addState(1, false, true);
        a_minus.addTransition(0, '-', 1);
        lex.add_automaton(TokenType.MINUS, a_minus);

        // TIMES: \*
        Automaton a_times = new AutomatonImpl();
        a_times.addState(0, true, false);
        a_times.addState(1, false, true);
        a_times.addTransition(0, '*', 1);
        lex.add_automaton(TokenType.TIMES, a_times);

        // DIV: /
        Automaton a_div = new AutomatonImpl();
        a_div.addState(0, true, false);
        a_div.addState(1, false, true);
        a_div.addTransition(0, '/', 1);
        lex.add_automaton(TokenType.DIV, a_div);

        // WHITE_SPACE (' '|\n|\r|\t)*
        Automaton a_ws = new AutomatonImpl();
        a_ws.addState(0, true, true);
        a_ws.addState(1, false, true);
        for (char c : new char[]{' ', '\n', '\r', '\t'}) {
            a_ws.addTransition(0, c, 1);
            a_ws.addTransition(1, c, 1);
        }
        lex.add_automaton(TokenType.WHITE_SPACE, a_ws);

        // LPAREN: (
        Automaton a_lp = new AutomatonImpl();
        a_lp.addState(0, true, false);
        a_lp.addState(1, false, true);
        a_lp.addTransition(0, '(', 1);
        lex.add_automaton(TokenType.LPAREN, a_lp);

        // RPAREN: )
        Automaton a_rp = new AutomatonImpl();
        a_rp.addState(0, true, false);
        a_rp.addState(1, false, true);
        a_rp.addTransition(0, ')', 1);
        lex.add_automaton(TokenType.RPAREN, a_rp);
    }
}
