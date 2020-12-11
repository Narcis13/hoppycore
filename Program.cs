using System;
using  System.Collections.Generic;

namespace hoppy
{
    class Program
    {
        static void Main(string[] args)
        {
            int sss =6+5;
           // TokenType type = TokenType.Literal;
           // Token t = new Token(TokenType.Name,"test");

            List<Token> tokens = new List<Token>();
            tokens.Add(new Token(TokenType.Operator,"(start)",0));
            tokens.Add(new Token(TokenType.Literal,"2"));
            tokens.Add(new Token(TokenType.Operator,"*",60));
            tokens.Add(new Token(TokenType.Literal,"3"));
            tokens.Add(new Token(TokenType.Operator,"+",50));
            tokens.Add(new Token(TokenType.Literal,"4"));
            tokens.Add(new Token(TokenType.Operator,"(end)",0));

            Stack<Node> operands = new Stack<Node>();
            Stack<Token> operators = new Stack<Token>();

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
                        Console.WriteLine("Do something...");

                        while(operators.Peek().value!="(start)"){
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


            Console.WriteLine(operators.Peek().toString());
            sss++;
        }
    }
}
