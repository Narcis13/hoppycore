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
          //  tokens.Add(new Token(TokenType.Operator,"(",100));
            tokens.Add(new Token(TokenType.Literal,"2"));
            tokens.Add(new Token(TokenType.Operator,"+",50));
            tokens.Add(new Token(TokenType.Literal,"3"));
         //   tokens.Add(new Token(TokenType.Operator,")",0));
            tokens.Add(new Token(TokenType.Operator,"*",60));
            tokens.Add(new Token(TokenType.Literal,"4"));
            tokens.Add(new Token(TokenType.Operator,"(end)",0));

         //   Stack<Node> operands = new Stack<Node>();
         //   Stack<Token> operators = new Stack<Token>();

            
          Parser p = new Parser(tokens);
          Node n = p.parseExpression();

            //Console.WriteLine(operators.Peek().toString());
            sss++;
        }
    }
}
