package AST;

public interface ASTVisitor {
    void visit(rootNode it);

    void visit(varDefNode it);

    void visit(classDefNode it);

    void visit(funcDefNode it);

    void visit(varNode it);

    void visit(paramNode it);

    void visit(varDefStmtNode it);

    void visit(blockStmtNode it);

    void visit(branchStmtNode it);

    void visit(exprStmtNode it);

    void visit(whileStmtNode it);

    void visit(forDefStmtNode it);

    void visit(forExprStmtNode it);

    void visit(returnStmtNode it);

    void visit(breakStmtNode it);

    void visit(continueStmtNode it);

    void visit(varExprNode it);

    void visit(thisExprNode it);

    void visit(funcExprNode it);

    void visit(memberVarExprNode it);

    void visit(memberFuncExprNode it);

    void visit(arrayExprNode it);

    void visit(newExprNode it);

    void visit(prefixUnaryExprNode it);

    void visit(suffixUnaryExprNode it);

    void visit(binaryExprNode it);

    void visit(ternaryExprNode it);

    void visit(assignExprNode it);

    void visit(boolNode it);

    void visit(numberNode it);

    void visit(strNode it);

    void visit(nullNode it);

    void visit(typeNode it);
}