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
public class IfStatement extends Symbol {
    
    Parser parser;
    Symbol first;
    List<Symbol> second;
    List<Symbol> third;
    
    public IfStatement(String id, Parser parser){
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

        if (parser.token.id == "else") {
            parser.scope.reserve(parser.token);
            parser.advance("else");
            this.third = parser.block();
        } else {
            this.third = null;
        }
        this.arity = "statement";
        return this;
     }
    
}
