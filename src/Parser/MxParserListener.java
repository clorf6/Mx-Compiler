// Generated from D:/OneDrive/clorf/Code/SJTU/PPCA/Mx Compiler/src/Parser\MxParser.g4 by ANTLR 4.12.0
package Parser;
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
	 * Enter a parse tree produced by {@link MxParser#func_def}.
	 * @param ctx the parse tree
	 */
	void enterFunc_def(MxParser.Func_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#func_def}.
	 * @param ctx the parse tree
	 */
	void exitFunc_def(MxParser.Func_defContext ctx);
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
	 * Enter a parse tree produced by {@link MxParser#argslist}.
	 * @param ctx the parse tree
	 */
	void enterArgslist(MxParser.ArgslistContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#argslist}.
	 * @param ctx the parse tree
	 */
	void exitArgslist(MxParser.ArgslistContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#class_def}.
	 * @param ctx the parse tree
	 */
	void enterClass_def(MxParser.Class_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#class_def}.
	 * @param ctx the parse tree
	 */
	void exitClass_def(MxParser.Class_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#class_suite}.
	 * @param ctx the parse tree
	 */
	void enterClass_suite(MxParser.Class_suiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#class_suite}.
	 * @param ctx the parse tree
	 */
	void exitClass_suite(MxParser.Class_suiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#construct_stmt}.
	 * @param ctx the parse tree
	 */
	void enterConstruct_stmt(MxParser.Construct_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#construct_stmt}.
	 * @param ctx the parse tree
	 */
	void exitConstruct_stmt(MxParser.Construct_stmtContext ctx);
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
	 * Enter a parse tree produced by the {@code varDefStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterVarDefStmt(MxParser.VarDefStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varDefStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitVarDefStmt(MxParser.VarDefStmtContext ctx);
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
	 * Enter a parse tree produced by {@link MxParser#loop_stmt}.
	 * @param ctx the parse tree
	 */
	void enterLoop_stmt(MxParser.Loop_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#loop_stmt}.
	 * @param ctx the parse tree
	 */
	void exitLoop_stmt(MxParser.Loop_stmtContext ctx);
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
	 * Enter a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prefixUnaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUnaryExpr(MxParser.PrefixUnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prefixUnaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUnaryExpr(MxParser.PrefixUnaryExprContext ctx);
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
	 * Enter a parse tree produced by the {@code pureExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPureExpr(MxParser.PureExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pureExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPureExpr(MxParser.PureExprContext ctx);
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
	 * Enter a parse tree produced by the {@code suffixUnaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSuffixUnaryExpr(MxParser.SuffixUnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suffixUnaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSuffixUnaryExpr(MxParser.SuffixUnaryExprContext ctx);
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
	 * Enter a parse tree produced by {@link MxParser#newerror_expr}.
	 * @param ctx the parse tree
	 */
	void enterNewerror_expr(MxParser.Newerror_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#newerror_expr}.
	 * @param ctx the parse tree
	 */
	void exitNewerror_expr(MxParser.Newerror_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#newvar_expr}.
	 * @param ctx the parse tree
	 */
	void enterNewvar_expr(MxParser.Newvar_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#newvar_expr}.
	 * @param ctx the parse tree
	 */
	void exitNewvar_expr(MxParser.Newvar_exprContext ctx);
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