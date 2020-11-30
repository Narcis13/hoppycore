/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seed;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
/**
 *
 * @author Narcis
 */
public class Scope {
     public HashMap<String, Symbol> def;
     public HashMap<String,Symbol> symbol_table;
      
      public Scope(HashMap<String,Symbol> symboltable)
        {
            this.def = new HashMap<String,Symbol>();
            this.symbol_table = symboltable;
        }

        public void define(Symbol n){
            Symbol t = this.def.get(n.originalToken.value);
            if (t!=null){
                throw new java.lang.Error("Symbol is already reserved or defined");
            }else {
                this.def.put(n.originalToken.value, n);
            }
        }
        
        public Symbol find(String n){
                Scope e = this;
                while(true){
                        Symbol o = e.def.get(n);
                           if (o!=null){
                                 return o;
                           }
                        e = e.parent;  
                        if(e==null){
                            //caut in symbol table
                            Symbol s = this.symbol_table.get(n);
                            if(s!=null){
                                return s;
                            }else{
                                return this.symbol_table.get("(name)");
                            }
                        }
               }
          //  return new Symbol(); // asta se sterge
        }
        
        public void pop(Scope scope){
            scope = this.parent;
        }
        
        public void reserve(Symbol n){
               if (n.arity != "name" || n.reserved) {
                    return;
               }
               Symbol t = this.def.get(n.originalToken.value);
               if (t!=null){
                   
               // throw new java.lang.Error("Symbol is already reserved or defined");
                return;
                }else {
                    n.reserved=true;
                    this.def.put(n.originalToken.value, n);
                }
        }
        public Scope parent;
}
