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
public class Token {
    public String value;
    public String type;
    public int from;
    public int to;
    
    
    public Token(String type,String value,int from, int to){
        this.from=from;
        this.to=to;
        this.type=type;
        this.value=value;
    }
}
