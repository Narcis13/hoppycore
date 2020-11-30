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
public class Prefix extends Symbol{
    
    public Parser parser;
    public Symbol first;
    
    
    public Prefix(String id,Parser parser){
        super (id);
        parser.symbol_table.put(id, this);
        this.parser=parser;
    }
    
    public Symbol nud(){
        
        scope.reserve(this);
            this.first = parser.expression(70);
            this.arity = "unary";
            return this;
    }
}
