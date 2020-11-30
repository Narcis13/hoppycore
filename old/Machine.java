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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

/** A simple stack-based interpreter */
public class Machine {
	public static final int DEFAULT_STACK_SIZE = 1000;
        public static final int DEFAULT_CALL_STACK_SIZE = 1000;
	public static final int FALSE = 0;
	public static final int TRUE = 1;

	// registers
	int ip;             // instruction pointer register
	int sp = -1;  		// stack pointer register
	int callsp = -1;        // frame pointer register

	int startip = 0;	// where execution begins

	// memory
	int[] code;         // word-addressable code memory but still bytecodes.
	Object[] globals;      // global variable space
	Object[] stack;		// Operand stack, grows upwards
        protected Object[] constPool;
        
        LocalContext[] callstack;
        NativeRuntime nativeruntime;
	public boolean trace = false;

	public Machine(Compiler compiler, int nglobals) {
		this.code = compiler.code;
                this.nativeruntime = compiler.nativeruntime;
		this.startip = startip;
                this.constPool = compiler.constantPool;
		globals = new Object[nglobals];
		stack = new Object[DEFAULT_STACK_SIZE];
                callstack = new LocalContext[DEFAULT_CALL_STACK_SIZE];
	}

	public void exec() {
		ip = startip;
		cpu();
	}

	/** Simulate the fetch-decode execute cycle */
	protected void cpu() {
		int opcode = code[ip];
		int addr,offset,regnum;
                Object aa,bb;
                Float a=0.0f,b=0.0f;
                int x,y;
                String e,f;
               
		while (opcode!= HALT && ip < code.length) {
			if ( trace ) System.err.printf("%-35s", disInstr());
			ip++; //jump to next instruction or to operand
			switch (opcode) {
				case IADD:
                                        if(stack[sp] instanceof String || stack[sp-1] instanceof String){
                                            f = (String)stack[sp--];   			// 2nd opnd at top of stack
                                            e = (String)stack[sp--]; 			// 1st opnd 1 below top
                                            stack[++sp] = e + f; 
                                        } else if(stack[sp] instanceof Integer && stack[sp-1] instanceof Integer){
                                           y = (int)stack[sp--];   			// 2nd opnd at top of stack
                                           x = (int)stack[sp--]; 			// 1st opnd 1 below top
                                            stack[++sp] = x + y;   
 
                                           
                                        }  else {
                                            bb = stack[sp--];   			// 2nd opnd at top of stack
                                            aa = stack[sp--]; 	
                                           /* if(aa instanceof Integer) 
                                                a=((Integer) aa).floatValue();
                                            else*/
                                                a=((Float) aa);
                                           /* if(bb instanceof Integer) 
                                                b=((Integer) bb).floatValue();
                                            else*/
                                                b=((Float) bb);
                                            stack[++sp] = a + b;  
                                        }                                            // push result
					break;
				case ISUB:
					if(stack[sp] instanceof Integer && stack[sp-1] instanceof Integer){
                                           y = (int)stack[sp--];   			// 2nd opnd at top of stack
                                           x = (int)stack[sp--]; 			// 1st opnd 1 below top
                                            stack[++sp] = x - y;   
 
                                           
                                        }  else {
                                            bb = stack[sp--];   			// 2nd opnd at top of stack
                                            aa = stack[sp--]; 	
                                            if(aa instanceof Integer) 
                                                a=((Integer) aa).floatValue();
                                            else
                                                a=((Float) aa);
                                            if(bb instanceof Integer) 
                                                b=((Integer) bb).floatValue();
                                            else
                                                b=((Float) bb);
                                            stack[++sp] = a - b;  
                                        }
                                            break;
				case IMUL:
					if(stack[sp] instanceof Integer && stack[sp-1] instanceof Integer){
                                           y = (int)stack[sp--];   			// 2nd opnd at top of stack
                                           x = (int)stack[sp--]; 			// 1st opnd 1 below top
                                            stack[++sp] = x * y;   
 
                                           
                                        }  else {
                                            bb = stack[sp--];   			// 2nd opnd at top of stack
                                            aa = stack[sp--]; 	
                                            if(aa instanceof Integer) 
                                                a=((Integer) aa).floatValue();
                                            else
                                                a=((Float) aa);
                                            if(bb instanceof Integer) 
                                                b=((Integer) bb).floatValue();
                                            else
                                                b=((Float) bb);
                                            stack[++sp] = a * b;  
                                        }
                                            break;
                                case IDIV:
                                        bb = stack[sp--];   			// 2nd opnd at top of stack
                                        aa = stack[sp--]; 
                                        if(aa instanceof Integer) 
                                                a=((Integer) aa).floatValue();
                                            else
                                                a=((Float) aa);
                                            if(bb instanceof Integer) 
                                                b=((Integer) bb).floatValue();
                                            else
                                                b=((Float) bb);
                                            stack[++sp] = a / b;  
                                        
                                            break;
                                case MOD:
                                        bb = stack[sp--];   			// 2nd opnd at top of stack
                                        aa = stack[sp--]; 
                                        if(aa instanceof Integer) 
                                                a=((Integer) aa).floatValue();
                                            else
                                                a=((Float) aa);
                                            if(bb instanceof Integer) 
                                                b=((Integer) bb).floatValue();
                                            else
                                                b=((Float) bb);
                                            stack[++sp] = a % b;  
                                        
                                            break;                                            
				case ILT :
                                        bb = stack[sp--];   			// 2nd opnd at top of stack
                                        aa = stack[sp--]; 
                                        if(aa instanceof Integer) 
                                                a=((Integer) aa).floatValue();
                                            else
                                                a=((Float) aa);
                                            if(bb instanceof Integer) 
                                                b=((Integer) bb).floatValue();
                                            else
                                                b=((Float) bb);
					stack[++sp] = (a < b) ? TRUE : FALSE;
					break;
				case IGT :
                                        bb = stack[sp--];   			// 2nd opnd at top of stack
                                        aa = stack[sp--]; 
                                        if(aa instanceof Integer) 
                                                a=((Integer) aa).floatValue();
                                            else
                                                a=((Float) aa);
                                            if(bb instanceof Integer) 
                                                b=((Integer) bb).floatValue();
                                            else
                                                b=((Float) bb);
					stack[++sp] = (a > b) ? TRUE : FALSE;
					break;                                        
				case IEQ :
                                        bb = stack[sp--];   			// 2nd opnd at top of stack
                                        aa = stack[sp--]; 
                                        if (aa instanceof String && bb instanceof String){
                                            
                                            stack[++sp] = (aa.equals(bb)) ? TRUE : FALSE;
                                        }else{ 
                                            if(aa instanceof Integer) 
                                                a=((Integer) aa).floatValue();
                                            else
                                                a=((Float) aa);
                                            if(bb instanceof Integer) 
                                                b=((Integer) bb).floatValue();
                                            else
                                                b=((Float) bb);
                                            //if(Math.abs(sectionID - currentSectionID) < 0.00000001)
					stack[++sp] = (Math.abs(a - b) < 0.00000001) ? TRUE : FALSE;}
					break;
				case BR :
					ip = code[ip++];
					break;
				case BRT :
					addr = code[ip++];
					if ((Integer) stack[sp--]==TRUE ) ip = addr;
					break;
				case BRF :
					addr = code[ip++];
					if ( (Integer)stack[sp--]==FALSE ) ip = addr;
					break;
				case ICONST:
					stack[++sp] = code[ip++]; // push operand
					break;
                                     
				case LOAD : // load local or arg; 1st local is fp+1, args are fp-3, fp-4, fp-5, ...
					regnum = code[ip++];
					stack[++sp] = callstack[callsp].locals[regnum];
					break;
				case GLOAD :// load from global memory
					addr = code[ip++];
					stack[++sp] = globals[addr];
					break;
				case STORE :
					regnum = code[ip++];
					callstack[callsp].locals[regnum] = (Integer)stack[sp--];
					break;
				case GSTORE :
					addr = code[ip++];
					globals[addr] = stack[sp--];
					break;
				case PRINT :
					System.out.println(stack[sp--]);
					break;
				case POP:
					--sp;
					break;
				case CALL :
					addr = code[ip++];			// target addr of function
					int nargs = code[ip++];		// how many args got pushed
					callstack[++callsp] = new LocalContext(ip,nargs+100);
					// copy args into new context
					int firstarg = sp-nargs+1;
					for (int i=0; i<nargs; i++) {
						callstack[callsp].locals[i] = stack[firstarg+i];
					}
					sp -= nargs;
					ip = addr;					// jump to function
					break;
				case NATIVECALL :
					addr = code[ip++];
                                        Object ret = new Object();
                                        String nf_name = nativeruntime.native_runtime_list.get(addr);
                                        Method method = nativeruntime.runtime.get(nf_name);
					int nnargs = code[ip++];		// how many args got pushed
					//callstack[++callsp] = new LocalContext(ip,nargs+100);
					// copy args into new context
					int nfirstarg = sp-nnargs+1;
                                        Object[] arguments = new Object[nnargs];
					for (int i=0; i<nnargs; i++) {
						arguments[i] = stack[nfirstarg+i];
					}
                                            {
                                                try {
                                                  ret= method.invoke(nativeruntime, arguments);
                                                } catch (IllegalAccessException ex) {
                                                    Logger.getLogger(Machine.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (IllegalArgumentException ex) {
                                                    Logger.getLogger(Machine.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (InvocationTargetException ex) {
                                                    Logger.getLogger(Machine.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
					sp -= nnargs;
					stack[++sp] = ret;					// jump to function
					break;                                                                                
				case RET:
					ip = callstack[callsp--].returnip;
					break;
                                        
                                case SCONST :
                                       addr = (Integer) code[ip++];
                                       stack[++sp] = constPool[addr];
                                       break;
				default :
					throw new Error("invalid opcode: "+opcode+" at ip="+(ip-1));
			}
			if ( trace ) System.err.println(stackString());
			opcode = code[ip];
		}
		if ( trace ) System.err.printf("%-35s", disInstr());
		if ( trace ) System.err.println(stackString());
		/*if ( trace )*/ dumpDataMemory();
	}

	protected String stackString() {
		StringBuilder buf = new StringBuilder();
		buf.append("stack=[");
		for (int i = 0; i <= sp; i++) {
			Object o = stack[i];
			buf.append(" ");
			buf.append(o);
		}
		buf.append(" ]");
		return buf.toString();
	}

	protected String disInstr() {
		int opcode = code[ip];
		String opName = Bytecode.instructions[opcode].name;
		StringBuilder buf = new StringBuilder();
		buf.append(String.format("%04d:\t%-11s", ip, opName));
		int nargs = Bytecode.instructions[opcode].n;
		if ( nargs>0 ) {
			List<String> operands = new ArrayList<String>();
			for (int i=ip+1; i<=ip+nargs; i++) {
				operands.add(String.valueOf(code[i]));
			}
			for (int i = 0; i<operands.size(); i++) {
				String s = operands.get(i);
				if ( i>0 ) buf.append(", ");
				buf.append(s);
			}
		}
		return buf.toString();
	}

	protected void dumpDataMemory() {
		System.err.println("Data memory:");
		int addr = 0;
		for (Object o : globals) {
			System.err.printf("%04d: %s\n", addr, o);
			addr++;
		}
		System.err.println();
	}
        
        public void dumpCode(){
            int idx=0;
            int op=code[idx];
            System.err.println("Code:");
            while (op!= HALT && idx < code.length){
                String opName = Bytecode.instructions[op].name;
		StringBuilder buf = new StringBuilder();
		buf.append(String.format("%04d:\t%-11s", idx, opName));
		int nargs = Bytecode.instructions[op].n;
		if ( nargs>0 ) {
			List<String> operands = new ArrayList<String>();
			for (int i=idx+1; i<=idx+nargs; i++) {
				operands.add(String.valueOf(code[i]));
			}
			for (int i = 0; i<operands.size(); i++) {
				String s = operands.get(i);
				if ( i>0 ) buf.append(", ");
				buf.append(s);
			}
		}
              //  System.err.printf("%-35s", buf.toString());
                System.err.println(buf.toString());
                idx=idx+nargs+1;
                op=code[idx];
            }
        }
}
