package Frontend;

import AST.*;
import Utils.*;
import AST.Type.*;
import Utils.Error.*;
import Parser.*;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Objects;

public class ASTBuilder extends MxParserBaseVisitor<ASTNode> {
    Type IntType = new intType();
    Type BoolType = new boolType();
    Type StringType = new stringType();
    Type VoidType = new voidType();
    Type NullType = new nullType();

    @Override
    public ASTNode visitProgram(MxParser.ProgramContext ctx) {
        ArrayList<defNode> DefNodes = new ArrayList<>();
        for (var Def : ctx.children) {
            if (Def instanceof MxParser.Var_defContext) {
                DefNodes.add((varDefNode) visit(Def));
            } else if (Def instanceof MxParser.Func_defContext) {
                DefNodes.add((funcDefNode) visit(Def));
            } else if (Def instanceof MxParser.Class_defContext) {
                DefNodes.add((classDefNode) visit(Def));
            }
        }
        return new rootNode(new Position(ctx), DefNodes);
    }

    @Override
    public ASTNode visitFunc_def(MxParser.Func_defContext ctx) {
        typeNode type;
        if (ctx.typename() != null) {
            type = (typeNode) visit(ctx.typename());
        } else {
            type = new typeNode(new Position(ctx.Void().getSymbol()), VoidType);
        }
        String name = ctx.Identifier().getText();
        ArrayList<paramNode> paramNodes = new ArrayList<>();
        var params = ctx.typedargslist();
        if (params != null) {
            int size = params.typename().size();
            for (int i = 0; i < size; i++) {
                typeNode paramType = (typeNode) visit(params.typename(i));
                String paramName = params.Identifier(i).getText();
                paramNodes.add(new paramNode(new Position(params.typename(i)), paramType, paramName));
            }
        }
        blockStmtNode suite = (blockStmtNode) visit(ctx.suite());
        return new funcDefNode(new Position(ctx), type, name, paramNodes, suite);
    }

    @Override
    public ASTNode visitClass_def(MxParser.Class_defContext ctx) {
        String name = ctx.Identifier().getText();
        var classStmts = ctx.class_suite();
        ArrayList<funcDefNode> funcDefNodes = new ArrayList<>();
        for (ParserRuleContext funcDef : classStmts.func_def()) {
            if (funcDef != null) {
                funcDefNodes.add((funcDefNode) visit(funcDef));
            }
        }
        ArrayList<varDefNode> varDefNodes = new ArrayList<>();
        for (ParserRuleContext varDef : classStmts.var_def()) {
            if (varDef != null) {
                varDefNodes.add((varDefNode) visit(varDef));
            }
        }
        if (classStmts.construct_stmt().size() > 1) {
            throw new syntaxError(new Position(ctx), "Constructor Number Exceed");
        } else if (classStmts.construct_stmt().size() == 1) {
            var constructStmt = classStmts.construct_stmt(0);
            if (!constructStmt.Identifier().getText().equals(name)) {
                throw new syntaxError(new Position(ctx), "Constructor Name Error");
            }
            funcDefNodes.add((funcDefNode) visit(constructStmt));
        }
        return new classDefNode(new Position(ctx), name, varDefNodes, funcDefNodes);
    }

    @Override
    public ASTNode visitConstruct_stmt(MxParser.Construct_stmtContext ctx) {
        typeNode type = new typeNode(new Position(ctx), NullType);
        String name = ctx.Identifier().getText();
        blockStmtNode suite = (blockStmtNode) visit(ctx.suite());
        return new funcDefNode(new Position(ctx), type, name, null, suite);
    }

    @Override
    public ASTNode visitVar_def(MxParser.Var_defContext ctx) {
        typeNode type = (typeNode) visit(ctx.typename());
        ArrayList<varNode> vars = new ArrayList<>();
        for (ParserRuleContext varStmt : ctx.var_stmt()) {
            if (varStmt != null) {
                vars.add((varNode) visit(varStmt));
            }
        }
        return new varDefNode(new Position(ctx), type, vars);
    }

    @Override
    public ASTNode visitVar_stmt(MxParser.Var_stmtContext ctx) {
        String name = ctx.Identifier().getText();
        ExprNode init = null;
        if (ctx.expression() != null) {
            init = (ExprNode) visit(ctx.expression());
        }
        return new varNode(new Position(ctx), name, init);
    }

    @Override
    public ASTNode visitSuite(MxParser.SuiteContext ctx) {
        ArrayList<StmtNode> stmts = new ArrayList<>();
        for (ParserRuleContext Stmt : ctx.stmt()) {
            if (Stmt != null) {
                stmts.add((StmtNode) visit(Stmt));
            }
        }
        return new blockStmtNode(new Position(ctx), stmts);
    }

    @Override
    public ASTNode visitBlock(MxParser.BlockContext ctx) {
        return visit(ctx.suite());
    }

    @Override
    public ASTNode visitVarDefStmt(MxParser.VarDefStmtContext ctx) {
        varDefNode varDef = (varDefNode) visit(ctx.var_def());
        return new varDefStmtNode(varDef.pos, varDef.typename, varDef.var);
    }

    @Override
    public ASTNode visitBranch_stmt(MxParser.Branch_stmtContext ctx) {
        ArrayList<ExprNode> exprs = new ArrayList<>();
        for (ParserRuleContext Expr : ctx.expression()) {
            if (Expr != null) {
                exprs.add((ExprNode) visit(Expr));
            }
        }
        ArrayList<StmtNode> stmts = new ArrayList<>();
        for (ParserRuleContext Stmt : ctx.stmt()) {
            if (Stmt != null) {
                stmts.add((StmtNode) visit(Stmt));
            }
        }
        return new branchStmtNode(new Position(ctx), exprs, stmts);
    }

    @Override
    public ASTNode visitBranchStmt(MxParser.BranchStmtContext ctx) {
        return visit(ctx.branch_stmt());
    }

    @Override
    public ASTNode visitLoop_stmt(MxParser.Loop_stmtContext ctx) {
        if (ctx.while_stmt() != null) {
            return visit(ctx.while_stmt());
        } else {
            return visit(ctx.for_stmt());
        }
    }

    @Override
    public ASTNode visitWhile_stmt(MxParser.While_stmtContext ctx) {
        ExprNode cond = (ExprNode) visit(ctx.expression());
        StmtNode stmt = (StmtNode) visit(ctx.stmt());
        return new whileStmtNode(new Position(ctx), cond, stmt);
    }

    @Override
    public ASTNode visitFor_stmt(MxParser.For_stmtContext ctx) {
        ExprNode cond = null;
        if (ctx.cond != null) {
            cond = (ExprNode) visit(ctx.cond);
        }
        ExprNode step = null;
        if (ctx.step != null) {
            step = (ExprNode) visit(ctx.step);
        }
        StmtNode stmt = (StmtNode) visit(ctx.stmt());
        if (ctx.var_def() != null) {
            varDefStmtNode init = new varDefStmtNode((varDefNode) visit(ctx.var_def()));
            return new forDefStmtNode(new Position(ctx), init, cond, step, stmt);
        } else {
            ExprNode init = null;
            if (ctx.init != null) {
                init = (ExprNode) visit(ctx.init);
            }
            return new forExprStmtNode(new Position(ctx), init, cond, step, stmt);
        }
    }

    @Override
    public ASTNode visitLoopStmt(MxParser.LoopStmtContext ctx) {
        return visit(ctx.loop_stmt());
    }

    @Override
    public ASTNode visitFlow_stmt(MxParser.Flow_stmtContext ctx) {
        if (ctx.return_stmt() != null) {
            return visit(ctx.return_stmt());
        } else if (ctx.break_stmt() != null) {
            return visit(ctx.break_stmt());
        } else {
            return visit(ctx.continue_stmt());
        }
    }

    @Override
    public ASTNode visitReturn_stmt(MxParser.Return_stmtContext ctx) {
        ExprNode ret = null;
        if (ctx.expression() != null) {
            ret = (ExprNode) visit(ctx.expression());
        }
        return new returnStmtNode(new Position(ctx), ret);
    }

    @Override
    public ASTNode visitBreak_stmt(MxParser.Break_stmtContext ctx) {
        return new breakStmtNode(new Position(ctx));
    }

    @Override
    public ASTNode visitContinue_stmt(MxParser.Continue_stmtContext ctx) {
        return new continueStmtNode(new Position(ctx));
    }

    @Override
    public ASTNode visitFlowStmt(MxParser.FlowStmtContext ctx) {
        return visit(ctx.flow_stmt());
    }

    @Override
    public ASTNode visitExpr_stmt(MxParser.Expr_stmtContext ctx) {
        ArrayList<ExprNode> exprs = new ArrayList<>();
        for (ParserRuleContext Expr : ctx.expression()) {
            if (Expr != null) {
                exprs.add((ExprNode) visit(Expr));
            }
        }
        return new exprStmtNode(new Position(ctx), exprs);
    }

    @Override
    public ASTNode visitExprStmt(MxParser.ExprStmtContext ctx) {
        return visit(ctx.expr_stmt());
    }

    @Override
    public ASTNode visitPureExpr(MxParser.PureExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public ASTNode visitConst_expr(MxParser.Const_exprContext ctx) {
        if (ctx.True() != null) {
            return new boolNode(new Position(ctx), true);
        } else if (ctx.False() != null) {
            return new boolNode(new Position(ctx), false);
        } else if (ctx.Number() != null) {
            return new numberNode(new Position(ctx), Long.parseLong(ctx.Number().getText()));
        } else if (ctx.Str() != null) {
            String str = ctx.Str().getText().substring(1, ctx.Str().getText().length() - 1);
            return new strNode(new Position(ctx), str);
        } else {
            return new nullNode(new Position(ctx));
        }
    }

    @Override
    public ASTNode visitConstExpr(MxParser.ConstExprContext ctx) {
        return visit(ctx.const_expr());
    }

    @Override
    public ASTNode visitVarExpr(MxParser.VarExprContext ctx) {
        String name = ctx.Identifier().getText();
        return new varExprNode(new Position(ctx), name);
    }

    @Override
    public ASTNode visitThisExpr(MxParser.ThisExprContext ctx) {
        return new thisExprNode(new Position(ctx));
    }

    @Override
    public ASTNode visitFunc_expr(MxParser.Func_exprContext ctx) {
        String name = ctx.Identifier().getText();
        ArrayList<ExprNode> args = new ArrayList<>();
        if (ctx.argslist() != null) {
            var Args = ctx.argslist();
            for (ParserRuleContext expr : Args.expression()) {
                if (expr != null) {
                    args.add((ExprNode) visit(expr));
                }
            }
        }
        return new funcExprNode(new Position(ctx), args, name);
    }

    @Override
    public ASTNode visitFuncExpr(MxParser.FuncExprContext ctx) {
        return visit(ctx.func_expr());
    }

    @Override
    public ASTNode visitMemberExpr(MxParser.MemberExprContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression(0));
        ASTNode member = visit(ctx.expression(1));
        if (member instanceof funcExprNode func) {
            return new memberFuncExprNode(new Position(ctx), expr, func);
        } else if (member instanceof varExprNode) {
            String name = ((varExprNode) member).name;
            return new memberVarExprNode(new Position(ctx), expr, name);
        } else {
            throw new syntaxError(new Position(ctx), "Member type wrong");
        }
    }


    @Override
    public ASTNode visitArrayExpr(MxParser.ArrayExprContext ctx) {
        ExprNode name = (ExprNode) visit(ctx.expression(0));
        ExprNode index = (ExprNode) visit(ctx.expression(1));
        return new arrayExprNode(new Position(ctx), name, index);
    }

    @Override public ASTNode visitNewvar_expr(MxParser.Newvar_exprContext ctx) {
        Type type = ((typeNode) visit(ctx.basic_type())).type;
        ArrayList<ExprNode> exprs = new ArrayList<>();
        for (ParserRuleContext Expr : ctx.expression()) {
            if (Expr != null) {
                exprs.add((ExprNode) visit(Expr));
            }
        }
        int dim = ctx.Lbracket().size();
        return new newExprNode(new Position(ctx), type, exprs, dim);
    }

    @Override
    public ASTNode visitNew_expr(MxParser.New_exprContext ctx) {
        if (ctx.newerror_expr() != null) {
            throw new syntaxError(new Position(ctx), "New array expression wrong");
        } else {
            return visit(ctx.newvar_expr());
        }
    }

    @Override
    public ASTNode visitNewExpr(MxParser.NewExprContext ctx) {
        return visit(ctx.new_expr());
    }

    @Override
    public ASTNode visitSuffixUnaryExpr(MxParser.SuffixUnaryExprContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        suffixUnaryExprNode.suffixOpType opCode;
        var Op = ctx.op.getText();
        if (Objects.equals(Op, "++")) {
            opCode = suffixUnaryExprNode.suffixOpType.Inc;
        } else if (Objects.equals(Op, "--")) {
            opCode = suffixUnaryExprNode.suffixOpType.Dec;
        } else {
            throw new syntaxError(new Position(ctx), "Wrong Suffix Unary Opcode");
        }
        return new suffixUnaryExprNode(new Position(ctx), expr, opCode);
    }

    @Override
    public ASTNode visitPrefixUnaryExpr(MxParser.PrefixUnaryExprContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        prefixUnaryExprNode.prefixOpType opCode;
        var Op = ctx.op.getText();
        opCode = switch (Op) {
            case "++" -> prefixUnaryExprNode.prefixOpType.Inc;
            case "--" -> prefixUnaryExprNode.prefixOpType.Dec;
            case "!" -> prefixUnaryExprNode.prefixOpType.Not;
            case "~" -> prefixUnaryExprNode.prefixOpType.Inv;
            case "-" -> prefixUnaryExprNode.prefixOpType.Sub;
            default -> throw new syntaxError(new Position(ctx), "Wrong Prefix Unary Opcode");
        };
        return new prefixUnaryExprNode(new Position(ctx), expr, opCode);
    }

    @Override
    public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
        ExprNode lhs = (ExprNode) visit(ctx.expression(0));
        ExprNode rhs = (ExprNode) visit(ctx.expression(1));
        binaryExprNode.binaryOpType opCode;
        var Op = ctx.op.getText();
        opCode = switch (Op) {
            case "*" -> binaryExprNode.binaryOpType.Mul;
            case "/" -> binaryExprNode.binaryOpType.Div;
            case "%" -> binaryExprNode.binaryOpType.Mod;
            case "+" -> binaryExprNode.binaryOpType.Add;
            case "-" -> binaryExprNode.binaryOpType.Sub;
            case "<<" -> binaryExprNode.binaryOpType.Lshift;
            case ">>" -> binaryExprNode.binaryOpType.Rshift;
            case "<" -> binaryExprNode.binaryOpType.Le;
            case ">" -> binaryExprNode.binaryOpType.Ge;
            case "<=" -> binaryExprNode.binaryOpType.Leq;
            case ">=" -> binaryExprNode.binaryOpType.Geq;
            case "==" -> binaryExprNode.binaryOpType.Eq;
            case "!=" -> binaryExprNode.binaryOpType.Neq;
            case "&" -> binaryExprNode.binaryOpType.Bitand;
            case "^" -> binaryExprNode.binaryOpType.Bitxor;
            case "|" -> binaryExprNode.binaryOpType.Bitor;
            case "&&" -> binaryExprNode.binaryOpType.And;
            case "||" -> binaryExprNode.binaryOpType.Or;
            default -> throw new syntaxError(new Position(ctx), "Wrong Binary Unary Opcode");
        };
        return new binaryExprNode(new Position(ctx), lhs, rhs, opCode);
    }

    @Override
    public ASTNode visitTernaryExpr(MxParser.TernaryExprContext ctx) {
        ExprNode cond = (ExprNode) visit(ctx.expression(0));
        ExprNode thenExpr = (ExprNode) visit(ctx.expression(1));
        ExprNode elseExpr = (ExprNode) visit(ctx.expression(2));
        return new ternaryExprNode(new Position(ctx), cond, thenExpr, elseExpr);
    }

    @Override
    public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {
        ExprNode lhs = (ExprNode) visit(ctx.expression(0));
        ExprNode rhs = (ExprNode) visit(ctx.expression(1));
        return new assignExprNode(new Position(ctx), lhs, rhs);
    }

    @Override public ASTNode visitTypename(MxParser.TypenameContext ctx) {
        if (ctx.Lbracket().isEmpty()) {
            return visit(ctx.basic_type());
        } else {
            var elemType = ((typeNode) visit(ctx.basic_type())).type;
            int dim = ctx.Lbracket().size();
            arrayType ArrayType = new arrayType(elemType, dim);
            return new typeNode(new Position(ctx), ArrayType);
        }
    }

    @Override public ASTNode visitBasic_type(MxParser.Basic_typeContext ctx) {
        if (ctx.Bool() != null) {
            return new typeNode(new Position(ctx), BoolType);
        } else if (ctx.Int() != null) {
            return new typeNode(new Position(ctx), IntType);
        } else if (ctx.String() != null) {
            return new typeNode(new Position(ctx), StringType);
        } else {
            var ClassType = new classType(ctx.Identifier().getText());
            return new typeNode(new Position(ctx), ClassType);
        }
    }

}
