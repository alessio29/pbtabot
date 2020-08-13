grammar PbtaBot;

@header {
package org.alessio29.pbtaBot.grammar;
}

expression
    : e1=expression op=('*'|'/'|'%') e2=expression
    | e1=expression op=('+'|'-') e2=expression
    | op=('+'|'-') e1=expression
    | t=term
    ;

term
    : i=INT
    | '(' e=expression ')'
    ;


INT: [0-9]+;
STRING: '"' StringChar* '"';
WS: [ \t\n\r] -> skip;

fragment StringChar: ~["\\\r\n];