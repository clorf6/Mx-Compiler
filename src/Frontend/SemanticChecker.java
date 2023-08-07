package Frontend;

import AST.*;
import Utils.Error.semanticError;
import Utils.Position;
import Utils.Type.*;
import Utils.Scope.*;

import java.util.Objects;

public class SemanticChecker implements ASTVisitor {
    public Scope currentScope;
    public globalScope global;
    public Type currentType, retType;
    public classType currentClass;
    public boolean ifret, infunc, ismain;
    public intType IntType = new intType();
    public boolType BoolType = new boolType();
    public nullType NullType = new nullType();
    public stringType StringType = new stringType();

    public SemanticChecker(globalScope global) {
        this.currentScope = this.global = global;
        currentClass = null;
        currentType = retType = null;
        ifret = infunc = ismain = false;
    }

    @Override
    public void visit(rootNode it) {
        var mainFunc = global.getBasic("main", it.pos);
        if (mainFunc == null) {
            throw new semanticError(it.pos, "No main function");
        }
        if (!(mainFunc instanceof funcType)) {
            throw new semanticError(it.pos, "Main is not a function");
        }
        if (!(((funcType) mainFunc).func.typename.type instanceof intType)) {
            throw new semanticError(it.pos, "Return type of main function isn't int");
        }
        if (!((funcType) mainFunc).func.param.isEmpty()) {
            throw new semanticError(it.pos, "Main functions's param isn't empty");
        }
        it.varDef.forEach(vd -> vd.accept(this));
        it.classDef.forEach(cd -> cd.accept(this));
        it.funcDef.forEach(fd -> fd.accept(this));
    }

    @Override
    public void visit(varDefNode it) {
        String name = getName(it.typename.type, it.pos);
        currentType = global.getBasic(name, it.pos);
        if (currentType == null) {
            throw new semanticError(it.pos, "Variables type doesn't exsit");
        }
        if (it.typename.type instanceof arrayType) {
            currentType = new arrayType(currentType, ((arrayType) it.typename.type).dim);
        }
        it.var.forEach(v -> v.accept(this));
        currentType = null;
    }

    private static String getName(Type it, Position pos) {
        String name = it.typeName;
        if (it instanceof nullType || it instanceof funcType
                || it instanceof voidType) {
            throw new semanticError(pos, "Wrong variable type");
        } else if (it instanceof classType) {
            name = ((classType) it).className;
        } else if (it instanceof arrayType) {
            name = getName(((arrayType) it).elemType, pos);
        }
        return name;
    }

    @Override
    public void visit(classDefNode it) {
        currentScope = new Scope(currentScope);
        currentClass = new classType(it.name);
        Type constructFunc = global.getBasic(it.name + "::" + it.name, it.pos);
        if (constructFunc != null) {
            if (!(constructFunc instanceof funcType)) {
                throw new semanticError(it.pos, "The constructor isn't a function");
            }
            if (!(((funcType) constructFunc).func.typename.type instanceof nullType)) {
                throw new semanticError(it.pos, "The return value of the constructor is not null");
            }
        }
        it.varDef.forEach(vd -> vd.accept(this));
        it.funcDef.forEach(fd -> fd.accept(this));
        currentClass = null;
        currentScope = currentScope.fatherScope;
    }

    @Override
    public void visit(funcDefNode it) {
        currentScope = new Scope(currentScope);
        infunc = true;
        retType = it.typename.type;
        ismain = (Objects.equals(it.name, "main"));
        if (it.param != null) {
            it.param.forEach(pm -> pm.accept(this));
        }
        it.suite.accept(this);
        if (!(it.typename.type instanceof voidType || it.typename.type instanceof nullType)) {
            if (!ifret && !Objects.equals(it.name, "main")) {
                throw new semanticError(it.pos, "No return value");
            }
        }
        infunc = ismain = false;
        retType = null;
        currentScope = currentScope.fatherScope;
    }

    @Override
    public void visit(varNode it) {
        if (global.getBasic(it.name, it.pos) instanceof funcType) {
            throw new semanticError(it.pos, "Variables and functions have the same name");
        }
        if (it.init != null) {
            it.init.accept(this);
            if (it.init.type instanceof nullType) {
                if ((!(currentType instanceof classType)) && (!(currentType instanceof arrayType))) {
                    throw new semanticError(it.pos, "Type init is null");
                }
            } else {
                if (!it.init.type.equal(currentType)) {
                    throw new semanticError(it.pos, "Variables type doesn't match the definition");
                }
            }
        }
        currentScope.put(it.name, currentType, it.pos);
    }

    @Override
    public void visit(paramNode it) {
        if (global.getBasic(getName(it.typename.type, it.pos), it.pos) == null) {
            throw new semanticError(it.pos, "Parameters type doesn't exist");
        }
        currentScope.put(it.name, it.typename.type, it.pos);
    }

    @Override
    public void visit(varDefStmtNode it) {
        String name = getName(it.typename.type, it.pos);
        currentType = global.getBasic(name, it.pos);
        if (currentType == null) {
            throw new semanticError(it.pos, "Variables type doesn't exsit");
        }
        if (it.typename.type instanceof arrayType) {
            currentType = new arrayType(currentType, ((arrayType) it.typename.type).dim);
        }
        it.var.forEach(v -> v.accept(this));
        currentType = null;
    }

    @Override
    public void visit(blockStmtNode it) {
        currentScope = new Scope(currentScope);
        it.stmt.forEach(sm -> sm.accept(this));
        currentScope = currentScope.fatherScope;
    }

    @Override
    public void visit(branchStmtNode it) {
        it.cond.forEach(cd -> {
            cd.accept(this);
            if (! (cd.type instanceof boolType)) {
                throw new semanticError(it.pos, "If statement condition type is not Boolean");
            }
        });
        it.Stmt.forEach(st -> {
            currentScope = new Scope(currentScope);
            st.accept(this);
            currentScope = currentScope.fatherScope;
        });
    }

    @Override
    public void visit(exprStmtNode it) {
        it.expr.forEach(ex -> ex.accept(this));
    }

    @Override
    public void visit(whileStmtNode it) {
        it.cond.accept(this);
        if (! (it.cond.type instanceof boolType)) {
            throw new semanticError(it.pos, "While statement condition type is not Boolean");
        }
        currentScope = new Scope(currentScope);
        currentScope.inloop = true;
        it.stmt.accept(this);
        currentScope = currentScope.fatherScope;
    }

    @Override
    public void visit(forDefStmtNode it) {
        if (it.varDef != null) {
            it.varDef.accept(this);
        }
        if (it.cond != null) {
            it.cond.accept(this);
            if (! (it.cond.type instanceof boolType)) {
                throw new semanticError(it.pos, "For statement condition type is not Boolean");
            }
        }
        if (it.step != null) {
            it.step.accept(this);
        }
        if (it.stmt == null) {
            throw new semanticError(it.pos, "For statement suite is missing");
        }
        currentScope = new Scope(currentScope);
        currentScope.inloop = true;
        it.stmt.accept(this);
        currentScope = currentScope.fatherScope;
    }

    @Override
    public void visit(forExprStmtNode it) {
        if (it.init != null) {
            it.init.accept(this);
        }
        if (it.cond != null) {
            it.cond.accept(this);
            if (! (it.cond.type instanceof boolType)) {
                throw new semanticError(it.pos, "For statement condition type is not Boolean");
            }
        }
        if (it.step != null) {
            it.step.accept(this);
        }
        if (it.stmt == null) {
            throw new semanticError(it.pos, "For statement suite is missing");
        }
        currentScope = new Scope(currentScope);
        currentScope.inloop = true;
        it.stmt.accept(this);
        currentScope = currentScope.fatherScope;
    }

    @Override
    public void visit(returnStmtNode it) {
        if (!infunc) {
            throw new semanticError(it.pos, "Return doesn't in a function");
        }
        if (it.ret != null) {
            it.ret.accept(this);
            if (it.ret.type instanceof nullType) {
                if (!(retType instanceof nullType || retType instanceof voidType ||
                      retType instanceof arrayType || retType instanceof classType)) {
                    throw new semanticError(it.pos, "Return value is null");
                }
            } else {
                if (!(it.ret.type.equal(retType))) {
                    throw new semanticError(it.pos, "Return type wrong");
                }
            }
        } else {
            if (!(retType instanceof voidType || retType instanceof nullType) && !ismain) {
                throw new semanticError(it.pos, "No return value");
            }
        }
        ifret = true;
    }

    @Override
    public void visit(breakStmtNode it) {
        if (!currentScope.inloop) {
            throw new semanticError(it.pos, "Break doesn't in a loop");
        }
    }

    @Override
    public void visit(continueStmtNode it) {
        if (!currentScope.inloop) {
            throw new semanticError(it.pos, "Continue doesn't in a loop");
        }
    }

    @Override
    public void visit(varExprNode it) {
        it.type = currentScope.get(it.name, true);
        if (it.type == null) {
            throw new semanticError(it.pos, "Variable is not defined");
        }
    }

    @Override
    public void visit(thisExprNode it) {
        if (currentClass == null) {
            throw new semanticError(it.pos, "This doesn't in a class");
        }
        it.type = currentClass;
    }

    @Override
    public void visit(funcExprNode it) {
        Type func = null;
        if (currentClass != null) {
            func = global.getBasic(currentClass.className + "::" + it.name, it.pos);
        }
        if (func == null) {
            func = global.getBasic(it.name, it.pos);
        }
        if (func == null) {
            throw new semanticError(it.pos, "Function is not defined");
        }
        if (!(func instanceof funcType)) {
            throw new semanticError(it.pos, "The function name wrong");
        }
        if (it.args.size() != ((funcType) func).func.param.size()) {
            throw new semanticError(it.pos, "The parameters number wrong");
        }
        for (int i = 0; i < it.args.size(); i++) {
            it.args.get(i).accept(this);
            Type paramType = ((funcType) func).func.param.get(i).typename.type;
            if (it.args.get(i).type instanceof nullType || it.args.get(i).type == null) {
                if (!(paramType instanceof nullType || paramType instanceof classType || paramType instanceof arrayType)) {
                    throw new semanticError(it.pos, "The parameter type wrong");
                }
            } else {
                if (!paramType.equal(it.args.get(i).type)) {
                    throw new semanticError(it.pos, "The parameter type wrong");
                }
            }
        }
        it.type = ((funcType) func).func.typename.type;
    }

    @Override
    public void visit(memberVarExprNode it) {
        it.expr.accept(this);
        if (!(it.expr.type instanceof classType)) {
            throw new semanticError(it.pos, "Class wrong");
        }
        it.type = global.getBasic(((classType) it.expr.type).className + "::" + it.name, it.pos);
        if (it.type == null) {
            throw new semanticError(it.pos, "Member variable is not defined");
        }
    }

    @Override
    public void visit(memberFuncExprNode it) {
        it.expr.accept(this);
        if (!(it.expr.type instanceof classType || it.expr.type instanceof arrayType || it.expr.type instanceof stringType)) {
            throw new semanticError(it.pos, "Class wrong");
        }
        Type func;
        if (it.expr.type instanceof classType) {
            func = global.getBasic(((classType) it.expr.type).className + "::" + it.func.name, it.pos);
        } else if (it.expr.type instanceof arrayType) {
            func = global.getBasic("#array::" + it.func.name, it.pos);
        } else {
            func = global.getBasic("string::" + it.func.name, it.pos);
        }
        if (func == null) {
            throw new semanticError(it.pos, "Function is not defined");
        }
        if (!(func instanceof funcType)) {
            throw new semanticError(it.pos, "The function name wrong");
        }
        if (it.func.args.size() != ((funcType) func).func.param.size()) {
            throw new semanticError(it.pos, "The parameters number wrong");
        }
        for (int i = 0; i < it.func.args.size(); i++) {
            it.func.args.get(i).accept(this);
            if (!((funcType) func).func.param.get(i).typename.type.equal(it.func.args.get(i).type)) {
                throw new semanticError(it.pos, "The parameter type wrong");
            }
        }
        it.type = ((funcType) func).func.typename.type;
    }

    @Override
    public void visit(arrayExprNode it) {
        it.name.accept(this);
        if (!(it.name.type instanceof arrayType)) {
            throw new semanticError(it.pos, "Visit array but not array");
        }
        it.index.accept(this);
        if (!(it.index.type instanceof intType)) {
            throw new semanticError(it.pos, "Array index is not int");
        }
        if (((arrayType) it.name.type).dim == 1) {
            it.type = ((arrayType) it.name.type).elemType;
        } else if (((arrayType) it.name.type).dim > 1) {
            it.type = new arrayType(((arrayType) it.name.type).elemType, ((arrayType) it.name.type).dim - 1);
        }
    }

    @Override
    public void visit(newExprNode it) {
        if (it.dim == 0) {
            it.type = global.getBasic(getName(it.typename, it.pos), it.pos);
            if (it.type == null) {
                throw new semanticError(it.pos, "Wrong type int new expression");
            }
            return ;
        }
        for (ExprNode Expr : it.expr) {
            Expr.accept(this);
            if (!(Expr.type instanceof intType)) {
                throw new semanticError(it.pos, "New array index is not int");
            }
        }
        it.type = new arrayType(it.typename, it.dim);
    }

    @Override
    public void visit(prefixUnaryExprNode it) {
        it.expr.accept(this);
        if (it.opCode == prefixUnaryExprNode.prefixOpType.Inc || it.opCode == prefixUnaryExprNode.prefixOpType.Dec) {
            if(!it.expr.isAssign) {
                throw new semanticError(it.pos, "Right value participates in prefix inc or dec operation");
            }
            if (!(it.expr.type instanceof intType)) {
                throw new semanticError(it.pos, "Other types participate in prefix inc or dec operation");
            }
            it.type = IntType;
        } else {
            if (it.expr.type instanceof intType) {
                if (it.opCode == prefixUnaryExprNode.prefixOpType.Not) {
                    throw new semanticError(it.pos, "Int type participate in not operation");
                }
                it.type = IntType;
            } else if (it.expr.type instanceof boolType) {
                if (it.opCode != prefixUnaryExprNode.prefixOpType.Not) {
                    throw new semanticError(it.pos, "Not type participate in inv or neg operation");
                }
                it.type = BoolType;
            } else {
                throw new semanticError(it.pos, "Other types participate in unary operation");
            }
        }
    }

    @Override
    public void visit(suffixUnaryExprNode it) {
        it.expr.accept(this);
        if(!it.expr.isAssign) {
            throw new semanticError(it.pos, "Right value participates in suffix inc or dec operation");
        }
        if (!(it.expr.type instanceof intType)) {
            throw new semanticError(it.pos, "Other types participate in suffix inc or dec operation");
        }
        it.type = IntType;
    }

    @Override
    public void visit(binaryExprNode it) {
        it.lhs.accept(this);
        it.rhs.accept(this);
        if (it.lhs.type instanceof voidType || it.rhs.type instanceof voidType ||
            it.lhs.type instanceof funcType || it.rhs.type instanceof funcType) {
            throw new semanticError(it.pos, "Function participate in binary operation");
        }
        if (it.opCode == binaryExprNode.binaryOpType.Eq || it.opCode == binaryExprNode.binaryOpType.Neq) {
            if (it.lhs.type instanceof nullType) {
                var temp = it.lhs;
                it.lhs = it.rhs;
                it.rhs = temp;
            }
            if (it.rhs.type instanceof nullType && (it.lhs.type instanceof nullType
             || it.lhs.type instanceof arrayType || it.lhs.type instanceof classType)) {
                it.type = BoolType;
                return ;
            }
        }
        if (!it.lhs.type.equal(it.rhs.type)) {
            throw new semanticError(it.pos, "Type not matched");
        }
        switch (it.opCode) {
            case Eq, Neq -> it.type = BoolType;
            case Mul, Div, Mod, Sub,
                    Lshift, Rshift, Bitand, Bitxor, Bitor -> {
                if (!(it.lhs.type instanceof intType)) {
                    throw new semanticError(it.pos, "Not int type participate in calculate operation");
                }
                it.type = IntType;
            }
            case Add -> {
                if (!(it.lhs.type instanceof intType || it.lhs.type instanceof stringType)) {
                    throw new semanticError(it.pos, "Not int type participate in calculate operation");
                }
                it.type = it.lhs.type;
            }
            case And, Or -> {
                if (!(it.lhs.type instanceof boolType)) {
                    throw new semanticError(it.pos, "Not bool type participate int logic operation");
                }
                it.type = BoolType;
            }
            case Le, Ge, Leq, Geq -> {
                if (!(it.lhs.type instanceof intType || it.lhs.type instanceof stringType)) {
                    throw new semanticError(it.pos, "Not int or string type participate int order operation");
                }
                it.type = BoolType;
            }
        }
    }

    @Override
    public void visit(ternaryExprNode it) {
        it.cond.accept(this);
        if (!(it.cond.type instanceof boolType)) {
            throw new semanticError(it.pos, "Ternary operation condition is not bool type");
        }
        it.thenExpr.accept(this);
        it.elseExpr.accept(this);
        if (it.thenExpr.type instanceof voidType || it.elseExpr.type instanceof voidType ||
            it.thenExpr.type instanceof funcType || it.elseExpr.type instanceof funcType) {
            throw new semanticError(it.pos, "Function participate in ternary operation");
        }
        if (it.thenExpr.type instanceof nullType) {
            if (it.elseExpr.type instanceof nullType) {
                it.type = NullType;
            } else if (it.elseExpr.type instanceof arrayType ||
                       it.elseExpr.type instanceof classType) {
                it.type = it.elseExpr.type;
            } else {
                throw new semanticError(it.pos, "Ternary expressions not match");
            }
        } else {
            if (it.elseExpr.type instanceof nullType) {
                if (it.thenExpr.type instanceof arrayType ||
                    it.thenExpr.type instanceof classType) {
                    it.type = it.thenExpr.type;
                } else {
                    throw new semanticError(it.pos, "Ternary expressions not match");
                }
            } else {
                if (!it.thenExpr.type.equal(it.elseExpr.type)) {
                    throw new semanticError(it.pos, "Ternary expressions not match");
                } else {
                    it.type = it.thenExpr.type;
                }
            }
        }
    }

    @Override
    public void visit(assignExprNode it) {
        it.lhs.accept(this);
        it.rhs.accept(this);
        if (!it.lhs.isAssign) {
            throw new semanticError(it.pos, "Right value participate in assign");
        }
        if (it.rhs.type instanceof nullType) {
            if (!(it.lhs.type instanceof arrayType || it.lhs.type instanceof classType)) {
                throw new semanticError(it.pos, "Not array or class type assign a null");
            }
        } else {
            if (!it.lhs.type.equal(it.rhs.type)) {
                throw new semanticError(it.pos,  "Assign expressions not match");
            } else {
                it.type = it.lhs.type;
            }
        }
    }

    @Override
    public void visit(boolNode it) {
        it.type = BoolType;
    }

    @Override
    public void visit(numberNode it) {
        it.type = IntType;
    }

    @Override
    public void visit(strNode it) {
        it.type = StringType;
    }

    @Override
    public void visit(nullNode it) {
        it.type = NullType;
    }

    @Override
    public void visit(typeNode it) {

    }
}
