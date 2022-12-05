// Source: https://swaminathanj.github.io/fsm/jlex.pdf
import java.util.*;
class Token {
  String text;
  Token(){}
  Token(String t) { text = t; }
}


class NumToken extends Token {
  public final int value;

  public NumToken(int value) {
    this.value = value;
  }

  public String toString() { return new Integer(this.value).toString(); }
}

class PlusToken extends Token {
  public String toString() { return "+"; }
}
class AsteriskToken extends Token {
  public String toString() { return "*"; }
}

%%

%public
%class Lexer
%{
static int tokenscnt=0;
ArrayList<Token> tokens = new ArrayList<>();
%}
%type void
digit = [0-9]
num = {digit}+
whitespace = [ \t\n]
%type Token
%eofval{
return new Token(null);
%eofval}

%%

"+" {  tokens.add(new PlusToken()); tokenscnt++;  }
{whitespace}+ { /* skip white spaces */ }
"*" {tokens.add(new AsteriskToken()); tokenscnt++;  }
{num} { tokens.add(new NumToken(0));tokenscnt++;  }
