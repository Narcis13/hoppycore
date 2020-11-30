/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ADMINISTRATOR1
 */
public class Parser {
    
    public HashMap<String,Symbol> symbol_table;
    private List<Token> tokens;
    private int token_nr=0;
    public Symbol token;
    public Scope scope;
    
    public Parser(List<Token> tokens){
         this.symbol_table= new HashMap<String,Symbol>();
         this.tokens = tokens;
         
         /*   symbols  */
                symbol("(end)",0);
                symbol("(name)",0);
                symbol(":",0);
                symbol(";",0);
                symbol(")",0);
                symbol("]",0);
                symbol("}",0);
                symbol(",",0);
                symbol("else",0);
                symbol("(literal)",0);
                symbol("integer",0);
                
         /*     constants           */
               new BooleanConstant("true",true,this);
               new BooleanConstant("false",false,this);
         
         /*        infix                */
               new Infix("+",50,this,false);
               new Infix("-",50,this,false);
               new Infix("*",60,this,false);
               new Infix("/",60,this,false);
               new Infix("%",60,this,false);
             // right associative
                new Infix("&&", 30,this,true);
                new Infix("||", 30,this,true);
                new Infix("===", 40,this,true);
                new Infix("!==", 40,this,true);
                new Infix("<", 40,this,true);
                new Infix("<=", 40,this,true);
                new Infix(">", 40,this,true);
                new Infix(">=", 40,this,true);
                
           /*         Assignment            */      
                new Assignment("=",this);
                
           /*         Statements             */  
                new VarStatement("var",this);
                new NewBlockStatement("{",this);
                new WhileStatement("while",this);
                new BreakStatement("break",this);
                new IfStatement("if",this);
                new ReturnStatement("return",this);
                
            /*      Prefixes           */
            
                  new Prefix("!",this);
                 // new Prefix("-",this);
                  new LeftParenPrefix("(",this);
                  new FunctionPrefix("function",this);
                  
            /*       Constants           */
                  new Constant("true",this,1);
                  new Constant("false",this,0);
                //  new Constant("null",this,null);
    }
    
    public void advance(String id){
            Symbol  o;
            if (id!="" && token.id != id) {
             throw new java.lang.Error("Expected "+id);
            }
            
            if (token_nr >= tokens.size()) {
                token = symbol_table.get("(end)");
                return;
            }
            
            Token t = tokens.get(token_nr);
            token_nr += 1;
            String v = t.value;
            String a = t.type;
            if (a == "name") {
                  o = scope.find(v);
                } else if (a == "operator") {
                    o = symbol_table.get(v);
                    if (o==null) {
                        throw new java.lang.Error("Unknown operator! ");
                    }
                } else if (a == "string" || a ==  "float" || a=="integer") {
                    o = symbol_table.get("(literal)");
                    o.arity = "literal";
                } else {
                   throw new java.lang.Error("Unexpected token! ");
                }
            o.originalToken=t;

            o.arity = a; 
            o.string_value = v;
            
            try{  
                token=(Symbol)o.clone();
  
            }catch(CloneNotSupportedException c){}  
    }
    
    public Scope new_scope()
   {
            Scope temp = this.scope;
            this.scope = new Scope(this.symbol_table);
            this.scope.parent = temp;
            return this.scope;
   }
    
    private void symbol(String id, int bp){
                            Symbol s = this.symbol_table.get(id);
                            if(s!=null){
                                if (bp >= s.lbp) {
                                    s.lbp = bp;
                                }
                            }else{
                                Symbol newSymbol = new Symbol(id);
                                this.symbol_table.put(id, newSymbol);
                            }
    }
    
    public Symbol expression(int rbp){
        Symbol left ;
        Symbol t = token;
        advance("");
        left = t.nud();
       
        while (rbp < token.lbp) {
            t = token;
            advance("");
            left = t.led(left);
        }
        return left;
       
    }
    
    public List<Symbol> block(){
        NewBlockStatement t = (NewBlockStatement)token;
        advance("{");
        return t.stdAsList();
    }
    
    
    private Symbol statement(){
       Symbol n=token,v;
       if (n.hasSTD){
            advance("");
            scope.reserve(n);
            return n.std();
       }
       v = expression(0);

        advance(";");
        return v;

    }
    
    public List<Symbol> statements(){
        List<Symbol> a  = new ArrayList<Symbol>();
        Symbol s;
        
        while (true) {
            if (token.id == "}" || token.id == "(end)") {
                break;
            }
            s = statement();
            if (s!=null) {
                a.add(s);
            }
        }
        return a.size() == 0 ? null : a;
        
    }
    
    public List<Symbol>  parse(){
           new_scope();
	
           advance("");
           List<Symbol> tree = statements();
           advance("(end)");
       return tree;
    }
    
}
