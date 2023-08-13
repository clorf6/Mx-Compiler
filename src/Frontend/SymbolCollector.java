package Frontend;

import AST.*;
import AST.Type.*;
import Utils.Scope.*;
import Utils.Position;

import java.util.ArrayList;

public class SymbolCollector implements ASTVisitor {
    public globalScope global;

    public SymbolCollector(globalScope global) {
        this.global = global;
    }

    public void init(Position pos) {
        Type IntType = new intType();
        Type BoolType = new boolType();
        Type StringType = new stringType();
        Type NullType = new nullType();
        Type VoidType = new voidType();
        typeNode IntNode = new typeNode(pos, IntType);
        typeNode StringNode = new typeNode(pos, StringType);
        typeNode VoidNode = new typeNode(pos, VoidType);
        global.putBasic("int", IntType, pos);
        global.putBasic("bool", BoolType, pos);
        global.putBasic("string", StringType, pos);
        global.putBasic("null", NullType, pos);
        paramNode paramInt = new paramNode(pos, IntNode, "n");
        paramNode paramStr = new paramNode(pos, StringNode, "str");
        global.putBasic("print", new funcType(new funcDefNode(
        pos, VoidNode, "print", new ArrayList<>() {{
            add(paramStr);
        }}, null
        )), pos);
        global.putBasic("println", new funcType(new funcDefNode(
                pos, VoidNode, "println", new ArrayList<>() {{
            add(paramStr);
        }}, null
        )), pos);
        global.putBasic("printInt", new funcType(new funcDefNode(
                pos, VoidNode, "printInt", new ArrayList<>() {{
            add(paramInt);
        }}, null
        )), pos);
        global.putBasic("printlnInt", new funcType(new funcDefNode(
                pos, VoidNode, "printlnInt", new ArrayList<>() {{
            add(paramInt);
        }}, null
        )), pos);
        global.putBasic("getString", new funcType(new funcDefNode(
                pos, StringNode, "getString", new ArrayList<>(), null
        )), pos);
        global.putBasic("getInt", new funcType(new funcDefNode(
                pos, IntNode, "getInt", new ArrayList<>(), null
        )), pos);
        global.putBasic("toString", new funcType(new funcDefNode(
                pos, StringNode, "toString", new ArrayList<>() {{
            add(paramInt);
        }}, null
        )), pos);
        funcDefNode Strlength = new funcDefNode(pos, IntNode, "string::length", new ArrayList<>(), null);
        funcDefNode Strsubstring = new funcDefNode(pos, StringNode, "string::substring", new ArrayList<>() {{
            add(new paramNode(pos, IntNode, "left"));
            add(new paramNode(pos, IntNode, "right"));
        }}, null);
        funcDefNode StrparseInt = new funcDefNode(pos, IntNode, "string::parseInt", new ArrayList<>(), null);
        funcDefNode Strord = new funcDefNode(pos, IntNode, "string::ord", new ArrayList<>() {{
            add(new paramNode(pos, IntNode, "pos"));
        }}, null);
        global.putBasic("string::length", new funcType(Strlength), pos);
        global.putBasic("string::substring", new funcType(Strsubstring), pos);
        global.putBasic("string::parseInt", new funcType(StrparseInt), pos);
        global.putBasic("string::ord", new funcType(Strord), pos);
        funcDefNode Arraysize = new funcDefNode(pos, IntNode, "_array::size", new ArrayList<>(), null);
        global.putBasic("_array::size", new funcType(Arraysize), pos);
    }

    @Override
    public void visit(rootNode it) {
        init(it.pos);
        it.Def.forEach(cd -> cd.accept(this));
    }

    @Override
    public void visit(funcDefNode it) {
        global.putBasic(it.name, new funcType(it), it.pos);
    }

    @Override
    public void visit(classDefNode it) {
        classType nowClass = new classType(it.name);
        for (funcDefNode func : it.funcDef) {
            global.putBasic(it.name + "::" + func.name, new funcType(func), func.pos);
        }
        for (varDefNode VarDef : it.varDef) {
            for (varNode Var : VarDef.var) {
                nowClass.vars.add(VarDef.typename.type);
                global.putBasic(it.name + "::" + Var.name, VarDef.typename.type, Var.pos);
            }
        }
        global.putBasic(it.name, nowClass, it.pos);
    }

    @Override
    public void visit(varNode it) {
    }

    @Override
    public void visit(paramNode it) {
    }

    @Override
    public void visit(varDefStmtNode it) {
    }

    @Override
    public void visit(blockStmtNode it) {
    }

    @Override
    public void visit(branchStmtNode it) {
    }

    @Override
    public void visit(exprStmtNode it) {
    }

    @Override
    public void visit(whileStmtNode it) {
    }

    @Override
    public void visit(forDefStmtNode it) {
    }

    @Override
    public void visit(forExprStmtNode it) {
    }

    @Override
    public void visit(returnStmtNode it) {
    }

    @Override
    public void visit(breakStmtNode it) {
    }

    @Override
    public void visit(continueStmtNode it) {
    }

    @Override
    public void visit(varExprNode it) {
    }

    @Override
    public void visit(thisExprNode it) {
    }

    @Override
    public void visit(funcExprNode it) {
    }

    @Override
    public void visit(memberVarExprNode it) {
    }

    @Override
    public void visit(memberFuncExprNode it) {
    }

    @Override
    public void visit(arrayExprNode it) {
    }

    @Override
    public void visit(newExprNode it) {
    }

    @Override
    public void visit(prefixUnaryExprNode it) {
    }

    @Override
    public void visit(suffixUnaryExprNode it) {
    }

    @Override
    public void visit(binaryExprNode it) {
    }

    @Override
    public void visit(ternaryExprNode it) {
    }

    @Override
    public void visit(assignExprNode it) {
    }

    @Override
    public void visit(boolNode it) {
    }

    @Override
    public void visit(numberNode it) {
    }

    @Override
    public void visit(strNode it) {
    }

    @Override
    public void visit(nullNode it) {
    }

    @Override
    public void visit(typeNode it) {
    }

    @Override
    public void visit(varDefNode it) {
    }
}