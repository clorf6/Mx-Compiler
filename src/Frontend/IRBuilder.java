package Frontend;

import AST.*;
import AST.Type.*;
import AST.Type.arrayType;
import AST.Type.classType;
import AST.Type.voidType;
import IR.Type.Type;
import Utils.Error.internalError;
import Utils.Position;
import Utils.Scope.*;
import IR.*;
import IR.Type.*;
import IR.Entity.*;
import IR.Instruction.*;

import java.lang.reflect.AnnotatedArrayType;
import java.util.ArrayList;

public class IRBuilder implements ASTVisitor {
    globalScope global;
    Scope currentScope;
    AST.Type.Type currentType;
    Program program;

    public IRBuilder(Program program, globalScope global) {
        this.currentScope = this.global = global;
        this.program = program;
    }

    IR.Type.Type convertType(AST.Type.Type type, Position pos) {
        if (type instanceof intType) {
            return new integerType(32);
        } else if (type instanceof boolType) {
            return new integerType(1);
        } else if (type instanceof voidType) {
            return new IR.Type.voidType();
        } else if (type instanceof stringType) {
            return new pointerType(new integerType(8));
        } else if (type instanceof classType) {
            ArrayList<Type> varType = new ArrayList<>();
            for (AST.Type.Type ty : ((classType) type).vars) {
                varType.add(convertType(ty, pos));
            }
            return new pointerType(new IR.Type.classType(((classType) type).className, varType));
        } else if (type instanceof arrayType) {
            Type ret = convertType(((arrayType) type).elemType, pos);
            for (int i = 1; i <= ((arrayType) type).dim; i++) {
                ret = new pointerType(ret);
            }
            return ret;
        } else {
            throw new internalError(pos, "Unknown type");
        }
    }

    ArrayList<localVarEntity> getParam(funcDefNode func, Position pos) {
        ArrayList<localVarEntity> param = new ArrayList<>();
        for (paramNode par : func.param) {
            param.add(new localVarEntity(convertType(par.typename.type, pos), par.name));
        }
        return param;
    }

    public void init(Position pos) {
        for (var entry : global.basic.entrySet()) {
            AST.Type.Type type = entry.getValue();
            if (type instanceof funcType) {
                funcDefNode func = ((funcType) type).func;
                var param = getParam(func, pos);
                var retType = convertType(func.typename.type, pos);
                program.funcs.add(new Function(func.name, retType, param));
                program.globalInsts.add(new declare(func.name, retType, param));
            } else if (type instanceof classType) {
                program.globalInsts.add(new classdef
                        ((IR.Type.classType) ((pointerType) convertType(type, pos)).elemType));
            }
        }
    }

    @Override
    public void visit(rootNode it) {
        init(it.pos);
        it.Def.forEach(def -> def.accept(this));
        program.funcs.get(0).block.get(0).add(
            new ret(new localVarEntity(new IR.Type.voidType(), null))
        );
    }

    @Override
    public void visit(varDefNode it) {
        currentType = it.typename.type;
        it.var.forEach(v -> v.accept(this));
        currentType = null;
    }

    @Override
    public void visit(classDefNode it) {

    }

    @Override
    public void visit(funcDefNode it) {

    }

    @Override
    public void visit(varNode it) {
        globalVarEntity now = new globalVarEntity(convertType(currentType, it.pos), it.name);
        if (it.init != null) it.init.accept(this);
        if (currentScope instanceof globalScope) {
            if (currentType instanceof intType) {
                if (it.init != null) {
                    program.globalInsts.add(new global(now, (intEntity) it.init.entity));
                } else {
                    program.globalInsts.add(new global(now, new intEntity(0)));
                }
            } else if (currentType instanceof boolType) {
                if (it.init != null) {
                    program.globalInsts.add(new global(now, (boolEntity) it.init.entity));
                } else {
                    program.globalInsts.add(new global(now, new boolEntity(false)));
                }
            } else {
                if (it.init != null) {

                } else {
                    program.globalInsts.add(new global(now, new nullEntity(now.type)));
                }
            }
        } else {

        }
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
}
