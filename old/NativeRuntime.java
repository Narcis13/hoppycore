/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seed;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Narcis
 */
public class NativeRuntime {
    
    public HashMap<String,Method> runtime;
     public HashMap <String,Integer>  native_functions ;
    public List<String> native_runtime_list = new ArrayList<String>();

    public NativeRuntime(){
        
        this.runtime = new HashMap<String,Method>();
        this.native_functions = new HashMap<String,Integer>();
        
        native_functions.put("print", 1);
        this.native_runtime_list.add("print");
        
        Class noparams[] = {};
        Class oneparam[] = {String.class};
        
        try{
            
        this.runtime.put("integerList", this.getClass().getDeclaredMethod("integerList", noparams));
        this.runtime.put("print",this.getClass().getDeclaredMethod("print", oneparam));
        
        } catch(Exception ex){
            
        }
    }
        public List<Integer> integerList(){
        List<Integer> list = new ArrayList<Integer>();
        return list;
        
    }
    
    public int print(String s){
        System.out.println("From seed: "+s);
        return 1;
    }
}
