package Utils;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class Position {
    private int row;
    private int column;

    public Position(int row, int col) {
        this.row = row;
        this.column = col;
    }

    public Position(Token token) {
        this.row = token.getLine();
        this.column = token.getCharPositionInLine();
    }
    public Position(ParserRuleContext ctx) {
        Token token = ctx.getStart();
        this.row = token.getLine();
        this.column = token.getCharPositionInLine();
    }
    public int row() { return row; }

    public int col() {
        return column;
    }

    public String toString() { return row + "," + column; }
}
