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
 * @author Narcis
 */
public class VarStatement extends Symbol{
    
    //List<Symbol> result;
    Parser parser;
    
    public VarStatement(String id, Parser parser){
        super(id);
        parser.symbol_table.put(id, this);
        this.parser=parser;
        this.hasSTD = true;
    }
    
    public Symbol std(){
        List<Symbol> a = new ArrayList<Symbol>();
        Symbol n;
        Assignment t;
        FunctionPrefix fp=null;
        while (true) {
            n = parser.token;
            if (n.arity != "name") {
                throw new java.lang.Error("Expected a new variable name."); 
            }
            parser.scope.define(n);
            parser.advance("");
            if (parser.token.id == "=") {
                t = (Assignment)parser.token;
                parser.advance("=");
                t.first = n;
                t.second = parser.expression(0);
                if(t.second.arity=="function"){
                   fp=(FunctionPrefix)t.second;
                  fp.name=t.first.string_value;
                }
                if(fp!=null) t.second=fp;
                t.kind="define";
                t.arity = "binary";
                a.add(t);
            }
            if (parser.token.id != ",") {
                break;
            }
            parser.advance(",");
        }
        parser.advance(";");
        return a.size() == 0 ? null : a.get(0);
    }
}
