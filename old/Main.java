/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seed;

//import com.oracle.tools.packager.IOUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static seed.Bytecode.BR;
import static seed.Bytecode.BRF;
import static seed.Bytecode.CALL;
import static seed.Bytecode.GLOAD;
import static seed.Bytecode.GSTORE;
import static seed.Bytecode.HALT;
import static seed.Bytecode.IADD;
import static seed.Bytecode.ICONST;
import static seed.Bytecode.ILT;
import static seed.Bytecode.IMUL;
import static seed.Bytecode.ISUB;
import static seed.Bytecode.LOAD;
import static seed.Bytecode.PRINT;
import static seed.Bytecode.RET;
/**
 *
 * @author Narcis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static int[] hello = {
		ICONST, 7,
		ICONST, 2,
                ICONST, 3,
                IMUL,
		IADD,
		PRINT,
		HALT
	};

	static int[] loop = {
	// .GLOBALS 2; N, I
	// N = 10						ADDRESS
			ICONST, 10,				// 0
			GSTORE, 0,				// 2
	// I = 0
			ICONST, 0,				// 4
			GSTORE, 1,				// 6
	// WHILE I<N:
	// START (8):
			GLOAD, 1,				// 8
			GLOAD, 0,				// 10
			ILT,					// 12
			BRF, 24,				// 13
	//     I = I + 1
			GLOAD, 1,				// 15
			ICONST, 1,				// 17
			IADD,					// 19
			GSTORE, 1,				// 20
			BR, 8,					// 22
	// DONE (24):
	// PRINT "LOOPED "+N+" TIMES."
			HALT					// 24
	};

	static int[] factorial = {
//.def fact: ARGS=1, LOCALS=0		ADDRESS
//	IF N < 2 RETURN 1
			LOAD, -3,				// 0
			ICONST, 2,				// 2
			ILT,					// 4
			BRF, 10,				// 5
			ICONST, 1,				// 7
			RET,					// 9
//CONT:
//	RETURN N * FACT(N-1)
			LOAD, -3,				// 10
			LOAD, -3,				// 12
			ICONST, 1,				// 14
			ISUB,					// 16
			CALL, 0, 1,				// 17
			IMUL,					// 20
			RET,					// 21
//.DEF MAIN: ARGS=0, LOCALS=0
// PRINT FACT(10)
			ICONST, 5,				// 22    <-- MAIN METHOD!
			CALL, 0, 1,				// 24
			PRINT,					// 27
			HALT					// 28
	};
        
    static int[] suma ={
        LOAD,0,
        LOAD, 1,
        IADD,
        RET,
        
        ICONST ,9,
        ICONST , 4,
        CALL,0,2,
        PRINT,
        HALT
    }   ;
    public Scope scope;
    
    public Main() throws Exception{
      String sbis = String.join("\n"
    , "function kiki (){"
         , "while (6>8){"
        , "if(8<2) {"   
        ,"   var d=1;"
        ,"}"      
       // , "   var tt=0;"
       , "   }" 
    , "};"
    //   , "var x=fibo(1) ;"  
    //  , "var x=suma (9.4,40)-2;" 
        );
      
      String s = String.join("\n"
        , "var q=print('abc')+12;"
     //  , "if(c==='abcd') {"   
     //  ,"   c='xyz';"
     //  ,"}"      
     //   , "   return n*fibo(n-1);"
     //  , "   };" 
    //   , "var x=fibo(5.0) ;"  
     // , "var xx=8%4;" 
        ); 
NativeRuntime nativeRuntime = new NativeRuntime();
     /*
 Method method = nativeRuntime.runtime.get("print");
Object[] args = new Object[1];
args[0]="HELLOOOOOO!!!!!!";
Object qqq = method.invoke(nativeRuntime, args);
*/ 
  
        String script = readFileAsString("D:\\dev\\seed\\script.js");
        //System.out.println(script);
      //  List<Integer> o_lista = new ArrayList<Integer>();
       // Object[] lista ={11,"sdf",o_lista};
         long startTime = System.currentTimeMillis();
        Tokenizer tokenizer = new Tokenizer("=<>!+-*&|/%^", "=<>&|");
        Parser parser = new Parser(tokenizer.tokenize(script));
        List<Symbol> tree = parser.parse();
        Compiler compiler = new Compiler(tree,nativeRuntime);
        compiler.compile();
        
        
        	Machine vm = new Machine(compiler , 10);
		vm.trace = false;
         long compTime = System.currentTimeMillis();
         
		vm.exec();
                
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Time elapsed: "+elapsedTime+" Compilation time: "+(compTime-startTime));
                vm.dumpCode();
    

    }
    
  /*  private Scope new_scope()
   {
            Scope temp = this.scope;
            this.scope = new Scope("copil");
            this.scope.parent = temp;
            return this.scope;
   }*/
    
 public static String readFileAsString(String fileName)throws Exception
  {
    String data = "";
    data = new String(Files.readAllBytes(Paths.get(fileName)));
    return data;
  }
 
 public static void main(String[] args) {
        try{
        new Main();
        } catch(Exception ex){
            System.err.println(ex.toString());
        }
    }
    
}
