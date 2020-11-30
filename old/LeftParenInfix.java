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
public class LeftParenInfix extends Symbol
{
    
    public Parser parser;
    public Symbol first;
    public Symbol second;

    
    public LeftParenInfix(String id, int bp, Parser parser){
        super(id);
        this.lbp=bp;
        parser.symbol_table.put(id, this);
        this.parser=parser;
        
        
    }
    
    public Symbol led(Symbol left){

            return this;
    }
    
}
