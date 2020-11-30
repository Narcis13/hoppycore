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
public class Constant extends Symbol{
     public Parser parser;
    public Symbol first;
    public Object value;
    
    public Constant(String id,Parser parser,Object val){
        super (id);
        this.parser = parser;
        parser.symbol_table.put(id, this);
        this.value=val;
    }
    
    public Symbol nud(){
        
        parser.scope.reserve(this);
            this.string_value=value.toString();
            this.arity = "integer";
            return this;
    }
}
