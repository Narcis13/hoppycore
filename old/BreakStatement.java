/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seed;

import java.util.List;

/**
 *
 * @author ADMINISTRATOR1
 */
public class BreakStatement extends Symbol{
    Parser parser; 
    public BreakStatement(String id, Parser parser){
         super(id);
         parser.symbol_table.put(id, this);
         this.parser=parser;
         this.hasSTD = true;
     }
    
    public Symbol std() {
    
           parser.advance(";");
        if (parser.token.id != "}") {
            throw new java.lang.Error("Unreachable statement.");
        }
        this.arity = "statement";
        return this;
    }
}
