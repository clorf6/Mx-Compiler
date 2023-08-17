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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class IRBuilder implements ASTVisitor {
    globalScope global;
    Scope currentScope;
    AST.Type.Type currentType;
    Program program;

    Entity currentThis;
    IR.Type.classType currentClass;
    boolean getVal;
    Block currentBlock;
    Block BeginBlock, EndBlock;
    Function currentFunction;

    HashMap<labelType, Boolean> appear;
    int count;

    integerType i32Type = new integerType(32);
    integerType i8Type = new integerType(8);
    integerType i1Type = new integerType(1);
    Type StrType = new pointerType(i8Type);
    IR.Type.voidType VoidType = new IR.Type.voidType();

    public IRBuilder(Program program, globalScope global) {
        this.currentScope = this.global = global;
        this.program = program;
        this.getVal = true;
        this.appear = new HashMap<>();
        this.count = 0;
    }

    localVarEntity getTempVar(Type type, String name) {
        return new localVarEntity(type, name + "." + ++count);
    }

    globalVarEntity getConstant(Type type) {
        return new globalVarEntity(type, "__const_str" + ++count);
    }

    IR.Type.Type convertType(AST.Type.Type type, Position pos) {
        if (type instanceof intType) {
            return i32Type;
        } else if (type instanceof boolType) {
            return i1Type;
        } else if (type instanceof voidType || type instanceof nullType) {
            return VoidType;
        } else if (type instanceof stringType) {
            return StrType;
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

    public void init(Position pos) {
        for (var entry : global.basic.entrySet()) {
            AST.Type.Type type = entry.getValue();
            String name = entry.getKey();
            if (type instanceof funcType) {
                int flag = global.BuiltinFunc.getOrDefault(name, -1);
                if (flag >= 0) {
                    funcDefNode func = ((funcType) type).func;
                    Function function = newFunc(func);
                    if (flag == 1) function.param.add(0, new localVarEntity(StrType, "str"));
                    program.globalInsts.add(new declare(name, function.retType, function.param));
                }
            } else if (type instanceof classType) {
                IR.Type.classType nowClass = (IR.Type.classType) ((pointerType) convertType(type, pos)).elemType;
                if (global.getClass(nowClass.name) == null) {
                    program.globalInsts.add(new classdef(nowClass));
                    global.putClass(name, nowClass, pos);
                }
            }
        }
        program.globalInsts.add(new declare("malloc", StrType, new ArrayList<>(){{
            add(new localVarEntity(i32Type, "size"));
        }}));
        ArrayList<localVarEntity> stringOpParam = new ArrayList<>(){{
            add(new localVarEntity(StrType, "lhs"));
            add(new localVarEntity(StrType, "rhs"));
        }};
        program.globalInsts.add(new declare("string.add", StrType, stringOpParam));
        program.globalInsts.add(new declare("string.le", StrType, stringOpParam));
        program.globalInsts.add(new declare("string.leq", StrType, stringOpParam));
        program.globalInsts.add(new declare("string.ge", StrType, stringOpParam));
        program.globalInsts.add(new declare("string.geq", StrType, stringOpParam));
        program.globalInsts.add(new declare("string.eq", StrType, stringOpParam));
        program.globalInsts.add(new declare("string.neq", StrType, stringOpParam));
        /*To Do---string opearation*/
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
        currentFunction = program.funcs.get(0);
        currentBlock = currentFunction.block.get(0);
        currentType = it.typename.type;
        it.var.forEach(v -> v.accept(this));
        currentType = null;
        currentBlock = null;
        currentFunction = null;
    }

    @Override
    public void visit(classDefNode it) {
        currentScope = new Scope(currentScope);
        currentClass = global.getClass(it.name);
        int pos = 0;
        for (varDefNode vars : it.varDef) {
            Type type = convertType(vars.typename.type, it.pos);
            for (varNode var : vars.var) {
                currentClass.pos.put(var.name, pos++);
                localVarEntity now = new localVarEntity(new pointerType(type), it.name);
                now.member = true;
                currentScope.putVar(var.name, now, it.pos);
            }
        }
        boolean constructor = false;
        for (funcDefNode func : it.funcDef) {
            if (Objects.equals(func.name, it.name)) constructor = true;
            func.accept(this);
        }
        if (!constructor) {
            localVarEntity This = new localVarEntity(new pointerType(currentClass), "this");
            Function construct = new Function(it.name + "." + it.name, VoidType, new ArrayList<>() {{
                add(This);
            }});
            construct.block.get(0).add(new ret(new localVarEntity(VoidType, "void")));
            construct.block.remove(1);
            program.funcs.add(construct);
        }
        currentClass = null;
        currentScope = currentScope.fatherScope;
    }

    Function newFunc(funcDefNode it) {
        String funcName = it.name;
        ArrayList<localVarEntity> param = new ArrayList<>();
        if (currentClass != null) {
            funcName = currentClass.name + "." + it.name;
            param.add(new localVarEntity(new pointerType(currentClass), "this"));
        }
        if (it.param != null) {
            for (paramNode par : it.param) {
                par.accept(this);
                param.add(new localVarEntity(convertType(par.typename.type, it.pos), par.name));
            }
        }
        var retType = convertType(it.typename.type, it.pos);
        return new Function(funcName, retType, param);
    }

    @Override
    public void visit(funcDefNode it) {
        currentScope = new Scope(currentScope);
        currentFunction = newFunc(it);
        global.putFunc(currentFunction.name, currentFunction, it.pos);
        program.funcs.add(currentFunction);
        currentBlock = currentFunction.block.get(0);
        if (Objects.equals(currentFunction.name, "main")) {
            currentBlock.add(new call("_global_init", new ArrayList<>()));
        }
        for (localVarEntity par : currentFunction.param) {
            localVarEntity addr = new localVarEntity(new pointerType(par.type), par.name + ".addr");
            currentBlock.add(new alloca(addr, par.type, it.pos));
            currentBlock.add(new store(par, addr, it.pos));
            currentScope.putVar(par.name, addr, it.pos);
        }
        currentBlock = currentFunction.block.get(1);
        it.suite.accept(this);
        if (Objects.equals(currentFunction.name, "main")) {
            if (currentBlock.terminal == null) {
                currentBlock.add(new ret(new intEntity(0)));
                currentFunction.add(currentBlock, appear);
            }
        }
        if (currentFunction.retType instanceof IR.Type.voidType) {
            if (currentBlock.terminal == null) {
                currentBlock.add(new ret(new localVarEntity(VoidType, "void")));
                currentFunction.add(currentBlock, appear);
            }
        }
        currentFunction.block.get(0).inst.addAll(currentFunction.block.get(1).inst);
        currentFunction.block.remove(1);
        currentScope = currentScope.fatherScope;
    }

    @Override
    public void visit(varNode it) {
        Type type = convertType(currentType, it.pos);
        varEntity addr;
        if (currentScope instanceof globalScope) {
            addr = new globalVarEntity(new pointerType(type), it.name);
            currentScope.putVar(it.name, addr, it.pos);
            if (currentType instanceof intType) {
                if (it.init != null && it.init.entity instanceof intEntity) {
                    program.globalInsts.add(new global((globalVarEntity) addr, (intEntity) it.init.entity));
                    return ;
                } else program.globalInsts.add(new global((globalVarEntity) addr, new intEntity(0)));
            } else if (currentType instanceof boolType) {
                if (it.init != null && it.init.entity instanceof boolEntity) {
                    program.globalInsts.add(new global((globalVarEntity) addr, (boolEntity) it.init.entity));
                    return ;
                } else program.globalInsts.add(new global((globalVarEntity) addr, new boolEntity(false)));
            } else program.globalInsts.add(new global((globalVarEntity) addr, new nullEntity(addr.type)));
        } else {
            addr = new localVarEntity(new pointerType(type), it.name);
            currentScope.putVar(it.name, addr, it.pos);
            addr.name = it.name + "." + ++count;
            currentFunction.block.get(0).add(new alloca(addr, type, it.pos));
        }
        if (it.init != null) {
            it.init.accept(this);
            Entity val = it.init.entity;
            if (it.init.entity instanceof stringEntity) {
                val = storeStr((stringEntity) it.init.entity);
            }
            currentBlock.add(new store(val, addr, it.pos));
        }
    }

    public Entity storeStr(stringEntity now) {
        String str = now.val;
        str = str.replace("\n", "\\OA").replace("\"", "\22");
        globalVarEntity strEntity = getConstant(new
                pointerType(new IR.Type.arrayType(i8Type, str.length() + 1))
        );
        program.globalInsts.add(new constant(strEntity, new stringEntity(str)));

//        localVarEntity ret = getTempVar(StrType, "const_str");
//        currentBlock.add(new bitcast(ret, strEntity, pos));
//        return ret;
        return strEntity;
    }

    @Override
    public void visit(paramNode it) {

    }

    @Override
    public void visit(varDefStmtNode it) {
        if (currentBlock.terminal != null) return ;
        currentType = it.typename.type;
        it.var.forEach(v -> v.accept(this));
        currentType = null;
    }

    @Override
    public void visit(blockStmtNode it) {
        if (currentBlock.terminal != null) return ;
        if (it.newScope) currentScope = new Scope(currentScope);
        it.stmt.forEach(sm -> sm.accept(this));
        if (it.newScope) currentScope = currentScope.fatherScope;
    }

    public void enterStmt(Block now, Block end, StmtNode stmt, boolean newScope) {
        if (newScope) currentScope = new Scope(currentScope);
        currentBlock = now;
        if (stmt != null) stmt.accept(this);
        if (currentBlock.terminal == null) {
            currentBlock.add(new br(end.name, appear));
            currentFunction.add(currentBlock, appear);
        }
        if (newScope) currentScope = currentScope.fatherScope;
    }

    public Entity enterExpr(Block now, Block end, ExprNode expr, boolean newScope) {
        if (newScope) currentScope = new Scope(currentScope);
        currentBlock = now;
        if (expr != null) expr.accept(this);
        if (currentBlock.terminal == null) {
            currentBlock.add(new br(end.name, appear));
            currentFunction.add(currentBlock, appear);
        } else expr = null;
        if (newScope) currentScope = currentScope.fatherScope;
        if (expr != null) return expr.entity;
        else return null;
    }

    @Override
    public void visit(branchStmtNode it) {
        if (currentBlock.terminal != null) return ;
        Block endBlock = new Block("if.end" + ++count);
        for (int i = 0; i < it.cond.size(); i++) {
            it.cond.get(i).accept(this);
            int flag = 0;
            if (it.cond.get(i).entity instanceof boolEntity) {
                boolean val = ((boolEntity) it.cond.get(i).entity).val;
                if (val) flag = 1;
            } else {
                if (i + 1 >= it.Stmt.size()) flag = 1;
                else flag = 2;
            }
            if (flag == 1) {
                Block thenBlock = new Block("if.then" + ++count);
                currentBlock.add(new br(thenBlock.name, appear));
                currentFunction.add(currentBlock, appear);
                enterStmt(thenBlock, endBlock, it.Stmt.get(i), true);
                currentBlock = endBlock;
            } else if (flag == 2) {
                Block thenBlock = new Block("if.then" + ++count);
                Block elseBlock = new Block("if.else" + ++count);
                currentBlock.add(new br(it.cond.get(i).entity, thenBlock.name, elseBlock.name, appear, it.pos));
                currentFunction.add(currentBlock, appear);
                enterStmt(thenBlock, endBlock, it.Stmt.get(i), true);
                if (i + 1 == it.cond.size()) {
                    enterStmt(elseBlock, endBlock, it.Stmt.get(i + 1), true);
                    currentBlock = endBlock;
                } else currentBlock = elseBlock;
            }
        }
    }

    @Override
    public void visit(exprStmtNode it) {
        if (currentBlock.terminal != null) return ;
        it.expr.forEach(ex -> ex.accept(this));
    }

    @Override
    public void visit(whileStmtNode it) {
        if (currentBlock.terminal != null) return ;
        Block tempBegin = BeginBlock, tempEnd = EndBlock;
        Block condBlock = new Block("while.cond" + ++count);
        Block bodyBlock = new Block("while.body" + ++count);
        Block endBlock = new Block("while.end" + ++count);
        BeginBlock = condBlock;
        EndBlock = endBlock;
        currentBlock.add(new br(condBlock.name, appear));
        currentFunction.add(currentBlock, appear);
        currentBlock = condBlock;
        it.cond.accept(this);
        if (it.cond.entity instanceof boolEntity) {
            boolean val = ((boolEntity) it.cond.entity).val;
            if (val) currentBlock.add(new br(bodyBlock.name, appear));
            else currentBlock.add(new br(endBlock.name, appear));
            currentFunction.add(currentBlock, appear);
        } else {
            currentBlock.add(new br(it.cond.entity, bodyBlock.name, endBlock.name, appear, it.pos));
            currentFunction.add(currentBlock, appear);
        }
        enterStmt(bodyBlock, condBlock, it.stmt, true);
        currentBlock = endBlock;
        BeginBlock = tempBegin;
        EndBlock = tempEnd;
    }

    @Override
    public void visit(forDefStmtNode it) {
        if (currentBlock.terminal != null) return ;
        Block tempBegin = BeginBlock, tempEnd = EndBlock;
        currentScope = new Scope(currentScope);
        if (it.varDef != null) it.varDef.accept(this);
        Block condBlock = new Block("for.cond" + ++count);
        Block stepBlock = new Block("for.step" + ++count);
        Block bodyBlock = new Block("for.body" + ++count);
        Block endBlock = new Block("for.end" + ++count);
        BeginBlock = stepBlock;
        EndBlock = endBlock;
        currentBlock.add(new br(condBlock.name, appear));
        currentFunction.add(currentBlock, appear);
        currentBlock = condBlock;
        if (it.cond != null) {
            it.cond.accept(this);
            if (it.cond.entity instanceof boolEntity) {
                boolean val = ((boolEntity) it.cond.entity).val;
                if (val) currentBlock.add(new br(bodyBlock.name, appear));
                else currentBlock.add(new br(endBlock.name, appear));
                currentFunction.add(currentBlock, appear);
            } else {
                currentBlock.add(new br(it.cond.entity, bodyBlock.name, endBlock.name, appear, it.pos));
                currentFunction.add(currentBlock, appear);
            }
        } else {
            currentBlock.add(new br(bodyBlock.name, appear));
            currentFunction.add(currentBlock, appear);
        }
        if (it.stmt instanceof blockStmtNode) {
            ((blockStmtNode) it.stmt).newScope = false;
        }
        enterStmt(bodyBlock, stepBlock, it.stmt, false);
        enterExpr(stepBlock, condBlock, it.step, false);
        currentBlock = endBlock;
        currentScope = currentScope.fatherScope;
        BeginBlock = tempBegin;
        EndBlock = tempEnd;
    }

    @Override
    public void visit(forExprStmtNode it) {
        if (currentBlock.terminal != null) return ;
        Block tempBegin = BeginBlock, tempEnd = EndBlock;
        currentScope = new Scope(currentScope);
        if (it.init != null) it.init.accept(this);
        Block condBlock = new Block("for.cond" + ++count);
        Block stepBlock = new Block("for.step" + ++count);
        Block bodyBlock = new Block("for.body" + ++count);
        Block endBlock = new Block("for.end" + ++count);
        BeginBlock = stepBlock;
        EndBlock = endBlock;
        currentBlock.add(new br(condBlock.name, appear));
        currentFunction.add(currentBlock, appear);
        currentBlock = condBlock;
        if (it.cond != null) {
            it.cond.accept(this);
            if (it.cond.entity instanceof boolEntity) {
                boolean val = ((boolEntity) it.cond.entity).val;
                if (val) currentBlock.add(new br(bodyBlock.name, appear));
                else currentBlock.add(new br(endBlock.name, appear));
                currentFunction.add(currentBlock, appear);
            } else {
                currentBlock.add(new br(it.cond.entity, bodyBlock.name, endBlock.name, appear, it.pos));
                currentFunction.add(currentBlock, appear);
            }
        } else {
            currentBlock.add(new br(bodyBlock.name, appear));
            currentFunction.add(currentBlock, appear);
        }
        if (it.stmt instanceof blockStmtNode) {
            ((blockStmtNode) it.stmt).newScope = false;
        }
        enterStmt(bodyBlock, stepBlock, it.stmt, false);
        enterExpr(stepBlock, condBlock, it.step, false);
        currentBlock = endBlock;
        currentScope = currentScope.fatherScope;
        BeginBlock = tempBegin;
        EndBlock = tempEnd;
    }

    @Override
    public void visit(returnStmtNode it) {
        if (currentBlock.terminal != null) return ;
        if (it.ret != null) {
            it.ret.accept(this);
            currentBlock.add(new ret(it.ret.entity));
        } else {
            if (Objects.equals(currentFunction.name, "main")) currentBlock.add(new ret(new intEntity(0)));
            else currentBlock.add(new ret(new localVarEntity(VoidType, "void")));
        }
        currentFunction.add(currentBlock, appear);
    }

    @Override
    public void visit(breakStmtNode it) {
        if (currentBlock.terminal != null) return ;
        currentBlock.add(new br(EndBlock.name, appear));
        currentFunction.add(currentBlock, appear);
    }

    @Override
    public void visit(continueStmtNode it) {
        if (currentBlock.terminal != null) return ;
        currentBlock.add(new br(BeginBlock.name, appear));
        currentFunction.add(currentBlock, appear);
    }

    public Entity getValue(Entity now, Position pos) {
        Entity ret = getTempVar(((pointerType) now.type).elemType, "var");
        currentBlock.add(new load(ret, now, pos));
        return ret;
    }

    @Override
    public void visit(varExprNode it) {
        varEntity var = currentScope.getVar(it.name, true);
        if (!var.member) {
            it.addr = var;
        } else {
            localVarEntity This = currentFunction.param.get(0);
            IR.Type.classType now = (IR.Type.classType) ((pointerType) This.type).elemType;
            int pos = now.pos.get(it.name);
            Type type = now.typelist.get(pos);
            localVarEntity addr = getTempVar(new pointerType(type), "addr");
            ArrayList<Entity> idx = new ArrayList<>() {{
                add(new intEntity(0));
                add(new intEntity(pos));
            }};
            currentBlock.add(new getelementptr(addr, This, idx, it.pos));
            it.addr = addr;
        }
        if (it.getEntity) it.entity = getValue(it.addr, it.pos);
    }

    @Override
    public void visit(thisExprNode it) {
        it.entity = currentFunction.param.get(0);
    }

    @Override
    public void visit(funcExprNode it) {
        String funcName = it.name;
        if (currentClass != null) {
            funcName = currentClass.name + "." + it.name;
        }
        Function func = global.getFunc(funcName);
        it.args.forEach(arg -> arg.accept(this));
        ArrayList<Entity> args = new ArrayList<>();
        if (currentClass != null) {
            if (currentThis != null) args.add(currentThis);
            else args.add(currentFunction.param.get(0));
        }
        for (ExprNode arg : it.args) args.add(arg.entity);
        if (!(func.retType instanceof IR.Type.voidType))  {
            localVarEntity res = getTempVar(func.retType, "var");
            currentBlock.add(new call(res, funcName, args));
            it.entity = res;
        } else currentBlock.add(new call(funcName, args));
    }

    @Override
    public void visit(memberVarExprNode it) {
        it.expr.accept(this);
        IR.Type.classType now = (IR.Type.classType) ((pointerType) it.expr.entity.type).elemType;
        now = global.getClass(now.name);
        int pos = now.pos.get(it.name);
        Type type = now.typelist.get(pos);
        localVarEntity addr = getTempVar(new pointerType(type), "addr");
        ArrayList<Entity> idx = new ArrayList<>() {{
            add(new intEntity(0));
            add(new intEntity(pos));
        }};
        currentBlock.add(new getelementptr(addr, it.expr.entity, idx, it.pos));
        it.addr = addr;
        if (it.getEntity) it.entity = getValue(addr, it.pos);
    }

    @Override
    public void visit(memberFuncExprNode it) {
        it.expr.accept(this);
        IR.Type.classType tempClass = currentClass;
        currentClass = (IR.Type.classType) ((pointerType) it.expr.entity.type).elemType;
        currentClass = global.getClass(currentClass.name);
        currentThis = it.expr.entity;
        it.func.accept(this);
        currentThis = null;
        currentClass = tempClass;
    }

    @Override
    public void visit(arrayExprNode it) {
        it.name.accept(this);
        it.index.accept(this);
        Type type = ((pointerType) it.name.entity.type).elemType;
        varEntity addr = getTempVar(new pointerType(type), "array");
        ArrayList<Entity> idx = new ArrayList<>() {{
            add(it.index.entity);
        }};
        currentBlock.add(new getelementptr(addr, it.name.entity, idx, it.pos));
        it.addr = addr;
        if (it.getEntity) it.entity = getValue(addr, it.pos);
    }

    varEntity callMalloc(int size) {
        varEntity tmp = getTempVar(StrType, "var");
        ArrayList<Entity> param = new ArrayList<>() {{
            add(new intEntity(size));
        }};
        currentBlock.add(new call(tmp, "malloc", param));
        return tmp;
    }

    public void buildArray(newExprNode it, int Dim, Type type,
                             Entity length, localVarEntity lengthAddr, localVarEntity array) {
        int size = (Dim == 1) ? type.size() : 4;
        if (length instanceof intEntity) {
            int Size = ((intEntity) length).val * size + 4;
            currentBlock.add(new call(lengthAddr, "malloc", new ArrayList<>() {{
                add(new intEntity(Size));
            }}));
        } else {
            localVarEntity tmp1 = getTempVar(i32Type, "var"), tmp2 = getTempVar(i32Type, "var");
            currentBlock.add(new binary(tmp1, length, binaryExprNode.binaryOpType.Mul, new intEntity(size), it.pos));
            currentBlock.add(new binary(tmp2, tmp1, binaryExprNode.binaryOpType.Add, new intEntity(4), it.pos));
            currentBlock.add(new call(lengthAddr, "malloc", new ArrayList<>(){{
                add(tmp2);
            }}));
        }
        currentBlock.add(new store(length, lengthAddr, it.pos));

        //localVarEntity tmp = getTempVar(new pointerType(i32Type), "var");
        //currentBlock.add(new bitcast(tmp, lengthAddr, it.pos));
        //currentBlock.add(new getelementptr(array, tmp, new ArrayList<>() {{
        //    add(new intEntity(1));
        //}}, it.pos));
        currentBlock.add(new getelementptr(array, lengthAddr, new ArrayList<>() {{
            add(new intEntity(4));
        }}, it.pos));
    }

    @Override
    public void visit(newExprNode it) {
        Type type = convertType(it.typename, it.pos);
        if (it.dim == 0) {
            IR.Type.classType now = (IR.Type.classType) ((pointerType) type).elemType;
            now = global.getClass(now.name);

            //it.entity = getTempVar(type, "var");
            //currentBlock.add(new bitcast(it.entity, callMalloc(now.size() + 8), it.pos));
            it.entity = callMalloc(now.size() + 8);

            currentBlock.add(new call(now.name + "." + now.name, new ArrayList<>() {{
                add(it.entity);
            }}));
        } else {
            int Dim = it.expr.size();
            it.expr.get(0).accept(this);
            Entity length = it.expr.get(0).entity;
            localVarEntity lengthAddr = getTempVar(StrType, "var");
            localVarEntity array = getTempVar(convertType(new arrayType(it.typename, Dim), it.pos),
                    "array");
            buildArray(it, 0, type, length, lengthAddr, array);
            it.entity = array;
            for (int i = 1; i < Dim; i++) {
                localVarEntity preArray = array;
                Entity preLength = length;
                it.expr.get(i).accept(this);
                Block condBlock = new Block("new.cond");
                Block bodyBlock = new Block("new.body");
                Block stepBlock = new Block("new.step");
                Block endBlock = new Block("new.end");
                localVarEntity stepAddr = new localVarEntity(new pointerType(i32Type), "_step");
                currentBlock.add(new alloca(stepAddr, i32Type, it.pos));
                currentBlock.add(new store(new intEntity(0), stepAddr, it.pos));
                currentBlock.add(new br(condBlock.name, appear));
                currentFunction.add(currentBlock, appear);
                currentBlock = condBlock;
                localVarEntity step = new localVarEntity(i32Type, "step");
                currentBlock.add(new load(step, stepAddr, it.pos));
                localVarEntity cond = getTempVar(i1Type, "cond");
                currentBlock.add(new icmp(cond, binaryExprNode.binaryOpType.Le, step, preLength, it.pos));
                currentBlock.add(new br(cond, bodyBlock.name, endBlock.name, appear, it.pos));
                currentFunction.add(currentBlock, appear);
                currentBlock = bodyBlock;
                length = it.expr.get(i).entity;
                if (!(length instanceof intEntity)) throw new internalError(it.pos, "Wrong type of length");
                lengthAddr = getTempVar(StrType, "var");
                array = getTempVar(convertType(new arrayType(it.typename, Dim - i), it.pos),
                        "array");
                buildArray(it, i, type, length, lengthAddr, array);
                localVarEntity arrayAddr = getTempVar(preArray.type, "addr");
                currentBlock.add(new getelementptr(arrayAddr, preArray, new ArrayList<>() {{add(step);}}, it.pos));
                currentBlock.add(new store(array, arrayAddr, it.pos));
                currentBlock.add(new br(stepBlock.name, appear));
                currentFunction.add(currentBlock, appear);
                currentBlock = stepBlock;
                localVarEntity next = getTempVar(i32Type, "step");
                currentBlock.add(new binary(next, step, binaryExprNode.binaryOpType.Add, new intEntity(1), it.pos));
                currentBlock.add(new store(next, stepAddr, it.pos));
                currentBlock.add(new br(condBlock.name, appear));
                currentFunction.add(currentBlock, appear);
                currentBlock = endBlock;
            }
        }
    }

    @Override
    public void visit(prefixUnaryExprNode it) {
        it.expr.accept(this);
        switch (it.opCode) {
            case Not -> {
                if (it.expr.entity instanceof boolEntity) {
                    it.entity = ((boolEntity) it.expr.entity).Not();
                } else {
                    Entity tmp = getTempVar(it.expr.entity.type, "prefixUnary");
                    currentBlock.add(new binary(tmp, it.expr.entity, binaryExprNode.binaryOpType.Bitxor, new boolEntity(true), it.pos));
                    it.entity = tmp;
                }
            }
            case Inv -> {
                if (it.expr.entity instanceof intEntity) {
                    it.entity = ((intEntity) it.expr.entity).Inv();
                } else {
                    Entity tmp = getTempVar(it.expr.entity.type, "prefixUnary");
                    currentBlock.add(new binary(tmp, it.expr.entity, binaryExprNode.binaryOpType.Bitxor, new intEntity(-1), it.pos));
                    it.entity = tmp;
                }
            }
            case Sub -> {
                if (it.expr.entity instanceof intEntity) {
                    it.entity = ((intEntity) it.expr.entity).Neg();
                } else {
                    Entity tmp = getTempVar(it.expr.entity.type, "prefixUnary");
                    currentBlock.add(new binary(tmp, new intEntity(0), binaryExprNode.binaryOpType.Sub, it.expr.entity, it.pos));
                    it.entity = tmp;
                }
            }
            case Inc -> {
                Entity tmp = getTempVar(it.expr.entity.type, "prefixUnary");
                currentBlock.add(new binary(tmp, it.expr.entity, binaryExprNode.binaryOpType.Add, new intEntity(1), it.pos));
                currentBlock.add(new store(tmp, it.expr.addr, it.pos));
                it.entity = tmp;
            }
            case Dec -> {
                Entity tmp = getTempVar(it.expr.entity.type, "prefixUnary");
                currentBlock.add(new binary(tmp, it.expr.entity, binaryExprNode.binaryOpType.Sub, new intEntity(1), it.pos));
                currentBlock.add(new store(tmp, it.expr.addr, it.pos));
                it.entity = tmp;
            }
            default -> throw new internalError(it.pos, "Prefix Unary Operation Wrong");
        }
    }

    @Override
    public void visit(suffixUnaryExprNode it) {
        it.expr.accept(this);
        switch (it.opCode) {
            case Inc -> {
                Entity tmp = getTempVar(it.expr.entity.type, "suffixUnary");
                currentBlock.add(new binary(tmp, it.expr.entity, binaryExprNode.binaryOpType.Add, new intEntity(1), it.pos));
                currentBlock.add(new store(tmp, it.expr.addr, it.pos));
            }
            case Dec -> {
                Entity tmp = getTempVar(it.expr.entity.type, "suffixUnary");
                currentBlock.add(new binary(tmp, it.expr.entity, binaryExprNode.binaryOpType.Sub, new intEntity(1), it.pos));
                currentBlock.add(new store(tmp, it.expr.addr, it.pos));
            }
        }
        it.entity = it.expr.entity;
    }

    @Override
    public void visit(binaryExprNode it) {
        it.lhs.accept(this);
        switch(it.opCode) {
            case Mul, Div, Mod, Add, Sub,
                    Lshift, Rshift, Bitand, Bitor, Bitxor -> {
                it.rhs.accept(this);
                if (it.lhs.entity instanceof stringEntity && it.rhs.entity instanceof stringEntity) {
                    String l = ((stringEntity) it.lhs.entity).val;
                    String r = ((stringEntity) it.rhs.entity).val;
                    it.entity = new stringEntity(l + r);
                } else if (it.lhs.entity instanceof intEntity && it.rhs.entity instanceof intEntity) {
                    int l = ((intEntity) it.lhs.entity).val;
                    int r = ((intEntity) it.rhs.entity).val;
                    int val = switch (it.opCode) {
                        case Mul -> l * r;
                        case Div -> l / r;
                        case Mod -> l % r;
                        case Add -> l + r;
                        case Sub -> l - r;
                        case Lshift -> l << r;
                        case Rshift -> l >> r;
                        case Bitand -> l & r;
                        case Bitor -> l | r;
                        case Bitxor -> l ^ r;
                        default -> throw new internalError(it.pos, "Unexpected value: " + it.opCode);
                    };
                    it.entity = new intEntity(val);
                } else if (it.lhs.entity instanceof boolEntity && it.rhs.entity instanceof boolEntity) {
                    boolean l = ((boolEntity) it.lhs.entity).val;
                    boolean r = ((boolEntity) it.rhs.entity).val;
                    boolean val = switch (it.opCode) {
                        case Bitand -> l & r;
                        case Bitor -> l | r;
                        case Bitxor -> l ^ r;
                        default -> throw new internalError(it.pos, "Unexpected value: " + it.opCode);
                    };
                    it.entity = new boolEntity(val);
                } else {
                    if (it.lhs.entity.isString() || it.lhs.entity instanceof stringEntity) {
                        Entity l = it.lhs.entity, r = it.rhs.entity;
                        if (l instanceof stringEntity) l = storeStr((stringEntity) l);
                        if (r instanceof stringEntity) r = storeStr((stringEntity) r);
                        /*To Do--调用string_plus，l,r*/
                    } else {
                        Entity tmp = getTempVar(it.lhs.entity.type, "binaryCalc");
                        currentBlock.add(new binary(tmp, it.lhs.entity, it.opCode, it.rhs.entity, it.pos));
                        it.entity = tmp;
                    }
                }
            }
            case Le, Leq, Ge, Geq, Eq, Neq -> {
                it.rhs.accept(this);
                if (it.lhs.entity instanceof stringEntity && it.rhs.entity instanceof stringEntity) {
                    String l = ((stringEntity) it.lhs.entity).val;
                    String r = ((stringEntity) it.rhs.entity).val;
                    boolean val = switch (it.opCode) {
                        case Le -> l.compareTo(r) < 0;
                        case Leq -> l.compareTo(r) <= 0;
                        case Ge -> l.compareTo(r) > 0;
                        case Geq -> l.compareTo(r) >= 0;
                        case Eq -> l.compareTo(r) == 0;
                        case Neq -> l.compareTo(r) != 0;
                        default -> throw new internalError(it.pos, "Unknown Type in string comp");
                    };
                    it.entity = new boolEntity(val);
                } else if (it.lhs.entity instanceof intEntity && it.rhs.entity instanceof intEntity) {
                    int l = ((intEntity) it.lhs.entity).val;
                    int r = ((intEntity) it.rhs.entity).val;
                    boolean val = switch (it.opCode) {
                        case Le -> l < r;
                        case Leq -> l <= r;
                        case Ge -> l > r;
                        case Geq -> l >= r;
                        case Eq -> l == r;
                        case Neq -> l != r;
                        default -> throw new internalError(it.pos, "Unknown Type in int comp");
                    };
                    it.entity = new boolEntity(val);
                } else if (it.lhs.entity instanceof boolEntity && it.rhs.entity instanceof boolEntity) {
                    boolean l = ((boolEntity) it.lhs.entity).val;
                    boolean r = ((boolEntity) it.rhs.entity).val;
                    boolean val = switch (it.opCode) {
                        case Eq -> l == r;
                        case Neq -> l != r;
                        default -> throw new internalError(it.pos, "Unknown Type in int comp");
                    };
                    it.entity = new boolEntity(val);
                } else {
                    if (it.lhs.entity.isString() || it.lhs.entity instanceof stringEntity) {
                        Entity l = it.lhs.entity, r = it.rhs.entity;
                        if (l instanceof stringEntity) l = storeStr((stringEntity) l);
                        if (r instanceof stringEntity) r = storeStr((stringEntity) r);
                        /*To Do--调用string_comp，l,r*/
                    } else {
                        Entity tmp = getTempVar(i1Type, "binaryComp");
                        currentBlock.add(new icmp(tmp, it.opCode, it.lhs.entity, it.rhs.entity, it.pos));
                        it.entity = tmp;
                    }
                }
            }
            case And -> {
                if (!((boolEntity) it.lhs.entity).val) it.entity = new boolEntity(false);
                else {
                    it.rhs.accept(this);
                    if (!((boolEntity) it.rhs.entity).val) it.entity = new boolEntity(false);
                    it.entity = new boolEntity(true);
                }
            }
            case Or -> {
                if (((boolEntity) it.lhs.entity).val) it.entity = new boolEntity(true);
                else {
                    it.rhs.accept(this);
                    if (((boolEntity) it.rhs.entity).val) it.entity = new boolEntity(true);
                    it.entity = new boolEntity(false);
                }
            }
        }
    }

    @Override
    public void visit(ternaryExprNode it) {
        it.cond.accept(this);
        if (it.cond.entity instanceof boolEntity) {
            if (((boolEntity) it.cond.entity).val) {
                it.thenExpr.accept(this);
                it.entity = it.thenExpr.entity;
            } else {
                it.elseExpr.accept(this);
                it.entity = it.elseExpr.entity;
            }
        } else {
            Block trueBlock = new Block("cond.true" + ++count);
            Block falseBlock = new Block("cond.false" + ++count);
            Block endBlock = new Block("cond.end" + ++count);
            currentBlock.add(new br(it.cond.entity, trueBlock.name, falseBlock.name, appear, it.pos));
            currentFunction.add(currentBlock, appear);
            Entity trueEntity = enterExpr(trueBlock, endBlock, it.thenExpr, true);
            Entity falseEntity = enterExpr(falseBlock, endBlock, it.elseExpr, true);
            currentBlock = endBlock;
            Entity ret = getTempVar(trueEntity.type, "var");
            currentBlock.add(new phi(ret, new ArrayList<>() {{
                add(trueEntity);
                add(falseEntity);
            }}, new ArrayList<>() {{
                add(trueBlock.name);
                add(falseBlock.name);
            }}, it.pos));
            it.entity = ret;
        }
    }

    @Override
    public void visit(assignExprNode it) {
        it.lhs.getEntity = false;
        it.lhs.accept(this);
        it.rhs.accept(this);
        currentBlock.add(new store(it.rhs.entity, it.lhs.addr, it.pos));
    }

    @Override
    public void visit(boolNode it) {
        it.entity = new boolEntity(it.val);
    }

    @Override
    public void visit(numberNode it) {
        it.entity = new intEntity((int) it.val);
    }

    @Override
    public void visit(strNode it) {
        it.entity = new stringEntity(it.val);
    }

    @Override
    public void visit(nullNode it) {
        it.entity = new nullEntity();
    }

    @Override
    public void visit(typeNode it) {

    }

}
