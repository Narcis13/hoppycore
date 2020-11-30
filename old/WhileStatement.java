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
public class WhileStatement extends Symbol{
    
    Parser parser;
    Symbol first;
    List<Symbol> second;
    
    public WhileStatement(String id, Parser parser){
        super(id);
        parser.symbol_table.put(id, this);
        this.parser=parser;
        this.hasSTD = true;
    }
    
     public Symbol std(){
         parser.advance("(");
        this.first = parser.expression(0);
        parser.advance(")");
        this.second = parser.block();
        this.arity = "statement";
        return this;
     }
}
