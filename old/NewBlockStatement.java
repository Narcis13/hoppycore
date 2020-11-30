/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seed;

import java.util.List;

/**
 *
 * @author Narcis
 */
public class NewBlockStatement extends Symbol {
    
    Parser parser; 
    public NewBlockStatement(String id, Parser parser){
         super(id);
         parser.symbol_table.put(id, this);
         this.parser=parser;
         this.hasSTD = true;
     }
    
    public List<Symbol> stdAsList() {
    
        parser.new_scope();
        List<Symbol> a = parser.statements();
        parser.advance("}");
        parser.scope=parser.scope.parent;
        return a;
    }
     
     
}
