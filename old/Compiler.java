/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seed;

import java.util.HashMap;
import java.util.List;
import static seed.Bytecode.BR;
import static seed.Bytecode.BRF;
import static seed.Bytecode.BRT;
import static seed.Bytecode.CALL;
import static seed.Bytecode.GLOAD;
import static seed.Bytecode.GSTORE;
import static seed.Bytecode.HALT;
import static seed.Bytecode.IADD;
import static seed.Bytecode.ICONST;
import static seed.Bytecode.IEQ;
import static seed.Bytecode.ILT;
import static seed.Bytecode.IGT;
import static seed.Bytecode.IMUL;
import static seed.Bytecode.ISUB;
import static seed.Bytecode.LOAD;
import static seed.Bytecode.POP;
import static seed.Bytecode.PRINT;
import static seed.Bytecode.RET;
import static seed.Bytecode.STORE;
import static seed.Bytecode.SCONST;
import static seed.Bytecode.IDIV;
import static seed.Bytecode.MOD;
import static seed.Bytecode.NATIVECALL;

/**
 *
 * @author ADMINISTRATOR1
 */
public class Compiler {
    
    public class Marker {
        
        int green =-1;
        int red=-1;
        
        public void reset(){
            this.green=-1;
            this.red=-1;
        }
    }
    
    public class FunctionContext {
        
        String name;
        int nrargs=0;
        Object[] locals;
        int address;
        int lp=0; // local pointer
        HashMap <String,Integer> localScope;
        public FunctionContext(String name, int nrargs, int index){
            address = index;
            localScope=new HashMap <String,Integer>();
            locals = new Object[100];
            this.name=name;
            this.nrargs=nrargs;
            
        }
        public void define(String argName){
            localScope.put(argName, lp++);
        }
    }
   
    
    public FunctionContext currentFC;
    private List<Symbol> ast;
    public Object[] constantPool;
    private HashMap<String,Integer> integer_vars;
    private HashMap <String,Compiler.FunctionContext> functions;
    private int next_integer_index=0;
    private int idx =0;
    private int c_idx=0;
    public NativeRuntime nativeruntime;
    private boolean inFunctionContext=false;

    public int[] code;
    public Compiler(List<Symbol> ast, NativeRuntime nativeruntime){
        this.ast=ast;
        this.constantPool = new Object[1024];
        this.integer_vars = new  HashMap<String,Integer>();
        this.functions = new HashMap <String,Compiler.FunctionContext>();
        this.nativeruntime = nativeruntime;
     
        code=new int[1000]; 
    }
    
    
    public void compile(){
        
        for(Symbol symbol : ast){
            if(symbol.string_value.equals("if")||symbol.string_value.equals("while")||symbol.string_value.equals("return")) symbol.arity="statement";
            generateBytecode(symbol);
        }
        code[idx++]=HALT;
      
    }
    
    private void generateBytecode(Symbol s){
        
        if(s.arity=="binary"){
            
            if(s.string_value.equals("=")){
                Assignment vs = (Assignment) s;
                if(s.kind=="define"){
                    if (this.inFunctionContext){
                     
                        this.currentFC.define(vs.first.string_value);
                        this.generateBytecodeFromExpression(vs.second);
                        code[idx++]=STORE;
                        code[idx++]=currentFC.lp-1;
                    } else {
                          
                            integer_vars.put(vs.first.string_value, this.next_integer_index);
                            this.generateBytecodeFromExpression(vs.second);
                            code[idx++]=GSTORE;
                            code[idx++]=this.next_integer_index++;
                    }
                }else{
                  
                    if (this.inFunctionContext){
                     
                        int addr = this.currentFC.localScope.get(vs.first.string_value);
                        this.generateBytecodeFromExpression(vs.second);
                        code[idx++]=STORE;
                        code[idx++]=addr;
                        
                    }else {
                    int addr =  integer_vars.get(vs.first.string_value);
                    this.generateBytecodeFromExpression(vs.second);
                    code[idx++]=GSTORE;
                    code[idx++]=addr; 
                    }
                }
            } else if(s.string_value.equals("(")){
                 // System.err.println("function call!");
                 
                 LeftParenPrefix lpf = (LeftParenPrefix)s;
                 for(Symbol param : lpf.second){
                     this.generateBytecodeFromExpression(param);
                 }
                 if(nativeruntime.native_functions.containsKey(lpf.first.string_value)){
                     //vative function call
                     code[idx++]=NATIVECALL;
                     code[idx++]=nativeruntime.native_runtime_list.indexOf(lpf.first.string_value);
                     code[idx++]=nativeruntime.native_functions.get(lpf.first.string_value);
                     
                 } else {

                 FunctionContext fctx = this.functions.get(lpf.first.string_value);
                 code[idx++]=CALL;
                 code[idx++]=fctx.address;
                 code[idx++]=fctx.nrargs; 
                 }
            }
            
        } else if(s.arity.equals("statement")){
            
            if (s.string_value.equals("if")){
                Marker marker = new Marker();
                IfStatement ifs = (IfStatement)s;
                this.generateBytecodeFromExpression(ifs.first);
                code[idx++]=BRF;
                marker.green=idx;
                code[idx++]=0;
                
                for(Symbol line:ifs.second){
                    if(line.string_value.equals("if")||line.string_value.equals("while")||line.string_value.equals("return")) line.arity="statement";
                    this.generateBytecode(line);
                }
                
                code[idx++]=BR;
                marker.red=idx;
                code[idx++]=0;
                
                code[marker.green]=idx;
                if(ifs.third!=null){
                    for(Symbol line:ifs.third){
                        if(line.string_value.equals("if")||line.string_value.equals("while")||line.string_value.equals("return")) line.arity="statement";
                         this.generateBytecode(line);
                    }
                }
                code[marker.red]=idx;
            } else if(s.string_value.equals("while")){
                //while statement
                
                Marker marker = new Marker();
                WhileStatement ws = (WhileStatement)s;
                marker.red=idx;
                this.generateBytecodeFromExpression(ws.first);
                code[idx++]=BRF;
                marker.green=idx;
                code[idx++]=0;
                
                for(Symbol line:ws.second){
                    if(line.string_value.equals("if")||line.string_value.equals("while")||line.string_value.equals("return")) line.arity="statement";
                    this.generateBytecode(line);
                }
                code[idx++]=BR;
                code[idx++]=marker.red;
                code[marker.green]=idx;
                
            } else if(s.string_value.equals("return")){
                
                ReturnStatement rs = (ReturnStatement)s;
                this.generateBytecodeFromExpression(rs.first);
                code[idx++]=RET;
            }
        } else if(s.arity.equals("function")){
            Marker marker = new Marker();
            code[idx++]=BR;
            marker.green=idx;
            code[idx++]=0;
            this.inFunctionContext=true;
            FunctionPrefix fps = (FunctionPrefix)s;
            this.currentFC = new FunctionContext(fps.name,fps.first.size(),idx);
            functions.put(fps.name, currentFC);
            
            for (Symbol param : fps.first){
                currentFC.define(param.string_value);
            }
            
            for(Symbol line:fps.second){
                    if(line.string_value.equals("if")||line.string_value.equals("while")||line.string_value.equals("return")) line.arity="statement";
                    this.generateBytecode(line);
            }
            code[marker.green]=idx;
            this.inFunctionContext=false;
            //System.err.println("Functions...");
            
        } else if(s.string_value.equals("return")){
                
                ReturnStatement rs = (ReturnStatement)s;
                this.generateBytecodeFromExpression(rs.first);
                code[idx++]=RET;
            }
    }
    
    private void generateBytecodeFromExpression(Symbol s){
        Object literal=null;
        if (s.arity=="float" ||s.arity=="integer"||s.arity=="string"){
            if (s.originalToken.type=="string") 
                literal = s.string_value;
          //  else
           //     literal = Float.parseFloat(s.string_value);
            if (s.originalToken.type=="integer") literal = (Integer)Integer.parseInt(s.string_value);
            if (s.originalToken.type=="float") literal = (Float)Float.parseFloat(s.string_value);
            if (s instanceof Constant) literal= (Integer) Integer.parseInt(s.string_value);
            this.constantPool[c_idx]= literal;
            code[idx++]=SCONST;
            code[idx++]=c_idx++;
        } else if(s.arity=="binary"){
            if(s.string_value.equals("(")){
                // function call returned value
               // System.err.println("f call!!!!");
               this.generateBytecode(s);
            }else {
                Infix is = (Infix)s; // aici mai am de lucru cind nu e infix ci e lefparen .....
                this.generateBytecodeFromExpression(is.first);
                this.generateBytecodeFromExpression(is.second);
                if(is.value=="+"){
                    code[idx++]=IADD;
                }
                if(is.value=="*"){
                    code[idx++]=IMUL;
                }
                if(is.value=="-"){
                    code[idx++]=ISUB;
                } 
                if(is.value=="/"){
                    code[idx++]=IDIV;
                }
                if(is.value.equals("%")){
                    code[idx++]=MOD;
                }
                if(is.value==">"){
                    code[idx++]=IGT;
                }
                if(is.value=="<"){
                    code[idx++]=ILT;
                }
                if(is.value.equals("===")){
                    code[idx++]=IEQ;
                }
            }
        } else if (s.arity=="name"){
            // aici am de lucru sa deosebesc globalele de locale...
            if(inFunctionContext){
                code[idx++]=LOAD;
                code[idx++]=currentFC.localScope.get(s.string_value);
            }else {
                code[idx++]=GLOAD;
                code[idx++]=integer_vars.get(s.string_value);  
            }
           
        }
        
    }
    
}
