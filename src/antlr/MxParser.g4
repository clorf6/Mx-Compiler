parser grammar MxParser;

options {
    tokenVocab = MxLexer;
}

program: (func_stmt | class_stmt | var_stmt)* EOF;

func_stmt: typename Identifier parameters suite;
parameters: Lparenthesis typedargslist? Rparenthesis;
typedargslist: typename Identifier (Comma typename Identifier)*;
arglist: expression (Comma expression)*;

class_stmt: Class Identifier suite Semicolon;

var_stmt: typename var_def (Comma var_def)* Semicolon;
var_def: Identifier (Assign expression)?;

suite: Lbrace stmt* Rbrace;

stmt
    : suite             #block
    | var_stmt          #varStmt
    | branch_stmt       #branchStmt
    | loop_stmt         #loopStmt
    | flow_stmt         #flowStmt
    | expr_stmt         #exprStmt
    ;

branch_stmt: If Lparenthesis expression Rparenthesis suite
            (Else suite)?;

loop_stmt
    : while_stmt        #whileStmt
    | for_stmt          #forStmt
    ;

while_stmt: While Lparenthesis expression Rparenthesis suite;

for_stmt: For Lparenthesis (expression | var_def)? Semicolon
          expression? Semicolon expression? Rparenthesis suite;

flow_stmt: return_stmt | break_stmt | continue_stmt;

return_stmt: Return expression? Semicolon;

break_stmt: Break Semicolon;

continue_stmt: Continue Semicolon;

expr_stmt: (expression (Comma expression)*)? Semicolon;

expression
    : const_expr                                        #constExpr
    | Identifier                                        #varExpr
    | This                                              #thisExpr
    | func_expr                                         #funcExpr
    | expression Dot (Identifier | func_expr)           #memberExpr
    | expression Lbracket expression Rbracket           #arrayExpr
    | new_expr                                          #newExpr
    | <assoc=right> op = (Increment |
                          Decrement) expression         #prefixUnary
    | expression op = (Increment | Decrement)           #suffixUnary
    | <assoc=right> op = (Not | Inv | Sub) expression   #bitUnary
    | expression op = (Mul | Div | Mod) expression      #muldivmodBinary
    | expression op = (Add | Sub) expression            #addsubBinary
    | expression op = (Lshift | Rshift) expression      #shiftBinary
    | expression op = (Lessthan | Greaterthan |
      Lessthanequal | Greaterthanequal) expression      #compBinary
    | expression op = (Equal | Notequal) expression     #equalBinary
    | expression op = Bitand expression                 #bitandBinary
    | expression op = Bitxor expression                 #bitxorBinary
    | expression op = Bitor  expression                 #bitorBinary
    | expression op = And    expression                 #andBinary
    | expression op = Or     expression                 #orBinary
    | <assoc=right> expression Question
      expression Colon expression                       #ternaryExpr
    | <assoc=right> expression Assign expression        #assignExpr
    ;

const_expr: True | False | Number | Str | Null;
func_expr: Identifier Lparenthesis arglist? Rparenthesis;
new_expr: New typename;

typename: (basic_type | Identifier) (Lbracket expression Rbracket)* (Lbracket Rbracket)*;
basic_type: Bool | Int | Void | String;


