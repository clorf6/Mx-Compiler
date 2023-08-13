// Generated from D:/OneDrive/clorf/Code/SJTU/PPCA/Mx Compiler/src/Parser\MxParser.g4 by ANTLR 4.12.0
package Parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MxParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#func_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_def(MxParser.Func_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#typedargslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedargslist(MxParser.TypedargslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#argslist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgslist(MxParser.ArgslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#class_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_def(MxParser.Class_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#class_suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClass_suite(MxParser.Class_suiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#construct_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstruct_stmt(MxParser.Construct_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#var_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_def(MxParser.Var_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#var_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_stmt(MxParser.Var_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(MxParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code block}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MxParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varDefStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefStmt(MxParser.VarDefStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code branchStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranchStmt(MxParser.BranchStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code loopStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStmt(MxParser.LoopStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code flowStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFlowStmt(MxParser.FlowStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(MxParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#branch_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranch_stmt(MxParser.Branch_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#loop_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop_stmt(MxParser.Loop_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#while_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stmt(MxParser.While_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#for_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_stmt(MxParser.For_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#flow_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFlow_stmt(MxParser.Flow_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#return_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stmt(MxParser.Return_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#break_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak_stmt(MxParser.Break_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#continue_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinue_stmt(MxParser.Continue_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#expr_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_stmt(MxParser.Expr_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisExpr(MxParser.ThisExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncExpr(MxParser.FuncExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(MxParser.ArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberExpr(MxParser.MemberExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prefixUnaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixUnaryExpr(MxParser.PrefixUnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarExpr(MxParser.VarExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ternaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernaryExpr(MxParser.TernaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pureExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPureExpr(MxParser.PureExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code suffixUnaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuffixUnaryExpr(MxParser.SuffixUnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstExpr(MxParser.ConstExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#const_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConst_expr(MxParser.Const_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#func_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_expr(MxParser.Func_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#new_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNew_expr(MxParser.New_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#newerror_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewerror_expr(MxParser.Newerror_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#newvar_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewvar_expr(MxParser.Newvar_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#typename}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypename(MxParser.TypenameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#basic_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasic_type(MxParser.Basic_typeContext ctx);
}