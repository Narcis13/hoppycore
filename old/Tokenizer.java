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
public class Tokenizer {
    
    public String prefix="<>+-&";
    public String suffix="=>&:";
    private boolean isFloat = false;
    List<Token> result;
    
    public Tokenizer(String prefix, String suffix){
        this.prefix=prefix;
        this.suffix=suffix;
        this.result = new ArrayList<Token>();
    }
    
    public List<Token> tokenize(String source){
       
        int from,i =0;
        String str;
        while ( i < source.length()){
            char c = source.charAt(i);   
            from=i;
            
                    if (c <= ' ') {
                        i += 1;
                        c = source.charAt(i);
                     }else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                        str =String.valueOf(c);
                        i += 1;
                        for (;;) {
                            c= ( i < source.length())?  source.charAt(i) : Character.MIN_VALUE;
                            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_') {
                                str += String.valueOf(c);
                                i += 1;
                            } else {
                                break;
                            }
                        }
                        result.add(new Token("name",str,from,i));
                      

// number.

// A number cannot start with a decimal point. It must start with a digit,
// possibly '0'.

                        }else if (c >= '0' && c <= '9') {
                            
                            str = String.valueOf(c);
                            i += 1;

                // Look for more digits.

                            for (;;) {
                                c= ( i < source.length())?  source.charAt(i) : Character.MIN_VALUE;
                                if (c < '0' || c > '9') {
                                    break;
                                }
                                i += 1;
                                str += String.valueOf(c);
                            }

                // Look for a decimal fraction part.

                            if (c == '.') {
                                isFloat=true;
                                i += 1;
                                str += String.valueOf(c);
                                for (;;) {
                                    c= ( i < source.length())?  source.charAt(i) : Character.MIN_VALUE;
                                    if (c < '0' || c > '9') {
                                        break;
                                    }
                                    i += 1;
                                    str += String.valueOf(c);
                                }
                            }
                         result.add(new Token(isFloat? "float":"integer",str,from,i));   
                         isFloat=false;
                        }else if (c == '\'' || c == '"'){
                            
                                str = "";
                                char q = c;
                                i += 1;
                                for (;;) {
                                    c= ( i < source.length())?  source.charAt(i) : Character.MIN_VALUE;

                    // Look for the closing quote.

                                    if (c == q) {
                                        break;
                                    }


                                    str += String.valueOf(c);
                                    i += 1;
                                }
                                i += 1;
                                result.add(new Token("string",str,from,i));  
                               
                        }else if (c == '/' && source.charAt(i + 1) == '/'){
                            i += 1;
                            for (;;) {
                                c= ( i < source.length())?  source.charAt(i) : Character.MIN_VALUE;
                                if (c == '\n' || c == '\r' || c== Character.MIN_VALUE) {
                                    break;
                                }
                                i += 1;
                            }
                        }else if (prefix.indexOf(c) >= 0){
                            
                                str = String.valueOf(c);
                                i += 1;
                                while (true) {
                                    c = ( i < source.length())?  source.charAt(i) : Character.MIN_VALUE;
                                    if (i >= source.length() || suffix.indexOf(c) < 0) {
                                        break;
                                    }
                                    str += String.valueOf(c);
                                    i += 1;
                                }
                                result.add(new Token("operator",str,from,i));


                    // single-character operator

                            } else {
                                i += 1;
                                result.add(new Token("operator",String.valueOf(c),from,i));
                                c= ( i < source.length())?  source.charAt(i) : Character.MIN_VALUE;
                            }
                        }
            
            
            //Process char
       
        
        return this.result;
    }
}
