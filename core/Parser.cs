using System;
using  System.Collections.Generic;

namespace hoppy {

    public class Parser {

     private List<Token> tokens = new List<Token>();

     public Parser(List<Token> _tokens){

         this.tokens=_tokens;
     }

    private Stack<Node> operands = new Stack<Node>();
    private Stack<Token> operators = new Stack<Token>();
    public Node parseExpression(){

        foreach (Token t in tokens)
            {
                if(t.type==TokenType.Literal){
                    operands.Push(new Node(t));
                }
                else {
                    if(operators.Count>0){
                        if(operators.Peek().bindingPower<t.bindingPower){
                                operators.Push(t);
                        }
                        else {
                            //do something
                        while(operators.Peek().value!="("||operators.Count>0){
                            Node op1 = operands.Pop();
                            Node op2 = operands.Pop();
                            operands.Push(new BinaryExpression(op2,operators.Pop(),op1));
                        }

                        operators.Push(t);
                        }
                    }else {
                        operators.Push(t);
                    }
                }
                
            }
        return operands.Pop();    
    }


    }
}