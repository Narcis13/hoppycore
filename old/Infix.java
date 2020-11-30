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
public class Infix extends Symbol {
    public Parser parser;
    public Symbol first;
    public Symbol second;
    public boolean isRightAssociative;
    
    public Infix(String id, int bp, Parser parser, boolean isRightAssociative){
        super(id);
        this.lbp=bp;
        parser.symbol_table.put(id, this);
        this.parser=parser;
        this.isRightAssociative=isRightAssociative;
        
    }
    
    public Symbol led(Symbol left){
            this.first = left;
            int i = this.isRightAssociative ? 1 :0;
            this.second = this.parser.expression(this.lbp-i);
            this.arity = "binary";
            return this;
    }
    
        public Symbol nud(){
        
        scope.reserve(this);
            this.first = parser.expression(70);
            this.arity = "unary";
            return this;
       }
}
