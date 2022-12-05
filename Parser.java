import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;



public class Parser {

  static ArrayList tokens;
  static int cnt=0;
  static int i=0;
  Parser(){}
  
  
  public static void main(String[] args) throws IOException, FileNotFoundException {
    System.out.println("Enter file name");
    Scanner sc= new Scanner(System.in);
    String filename=sc.next();
    Lexer lex = new Lexer(new FileReader(filename));
    Token token = lex.yylex();
    
    while ( token.text != null ) {
      token = lex.yylex(); //get next token
      
    }
    Parser parser=new Parser();
    tokens = lex.tokens;
    
    cnt=lex.tokenscnt;
    System.out.println(parser.parse());
  }
  
  
  private boolean match(Token token , int index)  throws IOException, FileNotFoundException {
   
    if(index < tokens.size() && token.getClass().equals(tokens.get(index).getClass() )) {
      return true;
    }
    return false;
  }

  
  
  public boolean parse() throws IOException, FileNotFoundException {
    return this.expr();
  }

  private boolean expr() throws IOException, FileNotFoundException {
    if(match(new NumToken(0), i) )
    {
      i++;
       if(cnt == i)
          return true;
       else
       {
          i--;
           //System.out.println("called t  , at token "+tokens.get(i));
           return term();
       }
    } 
    else return false;
  }

  private boolean term() throws IOException, FileNotFoundException {
    int j=i;
    //System.out.println("called f  , at token ");
    boolean f1=factor();
    //System.out.println("f1= "+f1 + " i= "+ i);
    if(i == cnt)
    	return f1;
    int k=i;
    
    //System.out.println("called f+t  , at token ");
    boolean f2= f1 && match(new PlusToken() , i);
    i++;
    f2= f2 && term(); 
    if(i == cnt)
    	return f2;
    //System.out.println("f2= "+f2+" i= "+i );
    
    //System.out.println("f1 stopped at "+k+" f2 stopped at "+i);
    
    return f1 || f2;
  }
  
  private boolean factor() throws IOException, FileNotFoundException {
    if(match(new NumToken(0) , i))
    {
        i++;
        if(match(new AsteriskToken() , i))
        {
           i++;
    	    //System.out.println("called n*f");
           return factor();
        }
        else return true;
    }
    return false;
  }
  
  
  
}
