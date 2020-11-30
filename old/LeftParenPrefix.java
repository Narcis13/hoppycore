/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seed;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMINISTRATOR1
 */
public class LeftParenPrefix extends Symbol{
    
    public Parser parser;
    public Symbol first;
    public List<Symbol> second;
    
    
    public LeftParenPrefix(String id,Parser parser){
        super (id);
        parser.symbol_table.put(id, this);
        this.parser=parser;
        this.lbp=80;
    }
    
    public Symbol nud(){
        
        Symbol e = parser.expression(0);
        parser.advance(")");
        return e;
    }
    
     public Symbol led(Symbol left){
            List<Symbol> a = new ArrayList<Symbol>();
            
            this.arity = "binary";
            this.first = left;
            this.second = a;
            if ((left.arity != "unary" || left.id != "function") &&
                    left.arity != "name" && left.id != "(" &&
                    left.id != "&&" && left.id != "||" && left.id != "?") {
                throw new java.lang.Error("Expected a variable name.");
            }
            if (parser.token.id != ")") {
            while (true) {
                a.add(parser.expression(0));
                if (parser.token.id != ",") {
                    break;
                }
                parser.advance(",");
            }
            }
                parser.advance(")");
           
            return this;
    }
}
