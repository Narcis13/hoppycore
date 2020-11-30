/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seed;

/**
 *
 * @author ADMINISTRATOR1
 */
public class ReturnStatement extends Symbol{
    
     Parser parser; 
     Symbol first;
    public ReturnStatement(String id, Parser parser){
         super(id);
         parser.symbol_table.put(id, this);
         this.parser=parser;
         this.arity = "statement";
         this.hasSTD = true;
     }
    
    public Symbol std() {
    
        if (parser.token.id != ";") {
            this.first = parser.expression(0);
        }
        parser.advance(";");
        if (parser.token.id != "}") {
            throw new java.lang.Error("Unreachable statement."); 
        }
        this.arity = "statement";
        return this;
    }
}
