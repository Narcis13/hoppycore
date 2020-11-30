/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seed;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMINISTRATOR1
 */
public class FunctionPrefix extends Symbol {
    
    public Parser parser;
    public List<Symbol> first;
    public List<Symbol> second;
    public String name = "anonymous";
    
    public FunctionPrefix(String id, Parser parser){
        super(id);
        parser.symbol_table.put(id, this);
        this.parser=parser;
    }
    
    public Symbol nud(){
        List<Symbol> a = new ArrayList<Symbol>();
        parser.new_scope();
        if (parser.token.arity == "name") {
            parser.scope.define(parser.token);
            this.name = parser.token.string_value;
            parser.advance("");
        }
        parser.advance("(");
        
        if (parser.token.id != ")") {
            while (true) {
                if (parser.token.arity != "name") {
                    throw new java.lang.Error("Expected a parameter name."); 
                }
                parser.scope.define(parser.token);
                a.add(parser.token);
                parser.advance("");
                if (parser.token.id != ",") {
                    break;
                }
                parser.advance(",");
            }
        }
        
                this.first = a;
        parser.advance(")");
        parser.advance("{");
        this.second = parser.statements();
        parser.advance("}");
        this.arity = "function";
        parser.scope=parser.scope.parent;
        return this;
       
    }
}
