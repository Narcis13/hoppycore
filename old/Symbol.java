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
public class Symbol implements Cloneable{
    
    
    public Symbol nud(){
        return this;
    } 
    
    public Token originalToken;
    public boolean reserved = false;
    public int lbp = 0;
    public String id;
    public String arity;
     public String string_value;
     public String kind="symbol";
    public String value;
    public boolean hasSTD = false;
    
    public Symbol(String id){
        this.id=id;
        this.value=id;
        this.string_value=id;
    }
    
    public void setOriginalToken(Token t){
        this.originalToken = t;
    }
    public Symbol led(Symbol left){
        return null;
    }
    
    public Symbol std(){
        
        return null;
    }
    
    public Scope scope=null;
    public Object clone()throws CloneNotSupportedException{  
        return super.clone();  
    }  
}
