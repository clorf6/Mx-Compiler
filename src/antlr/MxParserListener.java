// Generated from D:/clorf/Code/SJTU/PPCA/Mx Compiler/src/antlr\MxParser.g4 by ANTLR 4.12.0
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxParser}.
 */
public interface MxParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#func_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFunc_stmt(MxParser.Func_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#func_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFunc_stmt(MxParser.Func_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(MxParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(MxParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#typedargslist}.
	 * @param ctx the parse tree
	 */
	void enterTypedargslist(MxParser.TypedargslistContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#typedargslist}.
	 * @param ctx the parse tree
	 */
	void exitTypedargslist(MxParser.TypedargslistContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#arglist}.
	 * @param ctx the parse tree
	 */
	void enterArglist(MxParser.ArglistContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#arglist}.
	 * @param ctx the parse tree
	 */
	void exitArglist(MxParser.ArglistContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#class_stmt}.
	 * @param ctx the parse tree
	 */
	void enterClass_stmt(MxParser.Class_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#class_stmt}.
	 * @param ctx the parse tree
	 */
	void exitClass_stmt(MxParser.Class_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#var_stmt}.
	 * @param ctx the parse tree
	 */
	void enterVar_stmt(MxParser.Var_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#var_stmt}.
	 * @param ctx the parse tree
	 */
	void exitVar_stmt(MxParser.Var_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#var_def}.
	 * @param ctx the parse tree
	 */
	void enterVar_def(MxParser.Var_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#var_def}.
	 * @param ctx the parse tree
	 */
	void exitVar_def(MxParser.Var_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSuite(MxParser.SuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSuite(MxParser.SuiteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code block}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MxParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code block}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MxParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterVarStmt(MxParser.VarStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitVarStmt(MxParser.VarStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code branchStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterBranchStmt(MxParser.BranchStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code branchStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitBranchStmt(MxParser.BranchStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code loopStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterLoopStmt(MxParser.LoopStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code loopStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitLoopStmt(MxParser.LoopStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code flowStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterFlowStmt(MxParser.FlowStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code flowStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitFlowStmt(MxParser.FlowStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(MxParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(MxParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#branch_stmt}.
	 * @param ctx the parse tree
	 */
	void enterBranch_stmt(MxParser.Branch_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#branch_stmt}.
	 * @param ctx the parse tree
	 */
	void exitBranch_stmt(MxParser.Branch_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link MxParser#loop_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link MxParser#loop_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link MxParser#loop_stmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link MxParser#loop_stmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stmt(MxParser.While_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stmt(MxParser.While_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFor_stmt(MxParser.For_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFor_stmt(MxParser.For_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#flow_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFlow_stmt(MxParser.Flow_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#flow_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFlow_stmt(MxParser.Flow_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stmt(MxParser.Return_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stmt(MxParser.Return_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#break_stmt}.
	 * @param ctx the parse tree
	 */
	void enterBreak_stmt(MxParser.Break_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#break_stmt}.
	 * @param ctx the parse tree
	 */
	void exitBreak_stmt(MxParser.Break_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#continue_stmt}.
	 * @param ctx the parse tree
	 */
	void enterContinue_stmt(MxParser.Continue_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#continue_stmt}.
	 * @param ctx the parse tree
	 */
	void exitContinue_stmt(MxParser.Continue_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#expr_stmt}.
	 * @param ctx the parse tree
	 */
	void enterExpr_stmt(MxParser.Expr_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#expr_stmt}.
	 * @param ctx the parse tree
	 */
	void exitExpr_stmt(MxParser.Expr_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bitorBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBitorBinary(MxParser.BitorBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bitorBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBitorBinary(MxParser.BitorBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code suffixUnary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSuffixUnary(MxParser.SuffixUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suffixUnary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSuffixUnary(MxParser.SuffixUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterThisExpr(MxParser.ThisExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitThisExpr(MxParser.ThisExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndBinary(MxParser.AndBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndBinary(MxParser.AndBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bitxorBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBitxorBinary(MxParser.BitxorBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bitxorBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBitxorBinary(MxParser.BitxorBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFuncExpr(MxParser.FuncExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFuncExpr(MxParser.FuncExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bitandBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBitandBinary(MxParser.BitandBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bitandBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBitandBinary(MxParser.BitandBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(MxParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(MxParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrBinary(MxParser.OrBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrBinary(MxParser.OrBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemberExpr(MxParser.MemberExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemberExpr(MxParser.MemberExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code shiftBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterShiftBinary(MxParser.ShiftBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code shiftBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitShiftBinary(MxParser.ShiftBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqualBinary(MxParser.EqualBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqualBinary(MxParser.EqualBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVarExpr(MxParser.VarExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVarExpr(MxParser.VarExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prefixUnary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUnary(MxParser.PrefixUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prefixUnary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUnary(MxParser.PrefixUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ternaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTernaryExpr(MxParser.TernaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ternaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTernaryExpr(MxParser.TernaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bitUnary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBitUnary(MxParser.BitUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bitUnary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBitUnary(MxParser.BitUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCompBinary(MxParser.CompBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCompBinary(MxParser.CompBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code muldivmodBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMuldivmodBinary(MxParser.MuldivmodBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code muldivmodBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMuldivmodBinary(MxParser.MuldivmodBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addsubBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddsubBinary(MxParser.AddsubBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addsubBinary}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddsubBinary(MxParser.AddsubBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConstExpr(MxParser.ConstExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConstExpr(MxParser.ConstExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#const_expr}.
	 * @param ctx the parse tree
	 */
	void enterConst_expr(MxParser.Const_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#const_expr}.
	 * @param ctx the parse tree
	 */
	void exitConst_expr(MxParser.Const_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#func_expr}.
	 * @param ctx the parse tree
	 */
	void enterFunc_expr(MxParser.Func_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#func_expr}.
	 * @param ctx the parse tree
	 */
	void exitFunc_expr(MxParser.Func_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#new_expr}.
	 * @param ctx the parse tree
	 */
	void enterNew_expr(MxParser.New_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#new_expr}.
	 * @param ctx the parse tree
	 */
	void exitNew_expr(MxParser.New_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#typename}.
	 * @param ctx the parse tree
	 */
	void enterTypename(MxParser.TypenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#typename}.
	 * @param ctx the parse tree
	 */
	void exitTypename(MxParser.TypenameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#basic_type}.
	 * @param ctx the parse tree
	 */
	void enterBasic_type(MxParser.Basic_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#basic_type}.
	 * @param ctx the parse tree
	 */
	void exitBasic_type(MxParser.Basic_typeContext ctx);
}