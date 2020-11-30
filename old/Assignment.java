/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seed;

/**
 *
 * @author Narcis
 */
public class Assignment extends Symbol{
    public Parser parser;
    public Symbol first;
    public Symbol second;
    public boolean assignment=true;
    
    public Assignment(String id, Parser parser){
        super(id);
        this.lbp=10;
        parser.symbol_table.put(id, this);
        this.parser=parser;
        
        
    }
    
    public Symbol led(Symbol left){
            if (left.id != "." && left.id != "[" && left.arity != "name") {
                throw new java.lang.Error("Bad left value!");
            }
            this.first = left;
            
            this.second = this.parser.expression(9);
            this.arity = "binary";
            this.kind="assign";
            return this;
    }
}
