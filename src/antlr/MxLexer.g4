lexer grammar MxLexer;

Annotation: (BlockAnnotation | LineAnnotation) -> skip;

BlockAnnotation: '/*' .*? '*/';
LineAnnotation: '//' .*? (Newline | EOF);

Newline: ('\r' | '\n' | '\u2028' | '\u2029') -> skip;
Whitespace: (' ' | '\t' | '\u000B' | '\u000C' | '\u00A0') -> skip;

Lparenthesis: '(';
Rparenthesis: ')';
Lbracket: '[';
Rbracket: ']';
Lbrace: '{';
Rbrace: '}';
Dot: '.';
Comma: ',';
Semicolon: ';';
Question: '?';
Colon: ':';

Assign: '=';
Increment: '++';
Decrement: '--';
Not: '!';
Inv: '~';
Mul: '*';
Div: '/';
Mod: '%';
Add: '+';
Sub: '-';
Lshift: '<<';
Rshift: '>>';
Lessthan: '<';
Greaterthan: '>';
Lessthanequal: '<=';
Greaterthanequal: '>=';
Equal: '==';
Notequal: '!=';
Bitand: '&';
Bitxor: '^';
Bitor: '|';
And: '&&';
Or: '||';

Class: 'class';
If: 'if';
Else: 'else';
While: 'while';
For: 'for';
Return: 'return';
Break: 'break';
Continue: 'continue';

This: 'this';
True: 'true';
False: 'false';
Null: 'null';
New: 'new';
Bool: 'bool';
Int: 'int';
Void: 'void';
String: 'string';

Identifier: [A-Za-z][A-Za-z0-9_]*;

fragment Digit: [0-9];
fragment PosDigit: [1-9];
Number: (PosDigit Digit*) | '0';

fragment EscapeChar: '\\"' | '\\\\' | '\\n';
Str: '"' (EscapeChar | ~["\\\r\n\u2028\u2029])*? '"';