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
public class BooleanConstant extends Symbol{
    
    public boolean value;
  
    public Parser parser;
   
    
    public BooleanConstant(String id, boolean v, Parser parser){
        super(id);
        this.id=id;
        this.lbp=0;
        this.value=value;
        parser.symbol_table.put(id, this);
        this.parser=parser;
    }
    
    public Symbol nud(){
            this.parser.scope.reserve(this);
            this.string_value = this.parser.symbol_table.get(this.id).value;
            this.arity = "literal";
        return this;
    }
}
