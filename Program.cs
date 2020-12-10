using System;


namespace hoppy
{
    class Program
    {
        static void Main(string[] args)
        {
            int sss =6+5;
            TokenType type = TokenType.Literal;
            Token t = new Token(TokenType.Name,'test');
            Console.WriteLine("Hello World! "+type);
            sss++;
        }
    }
}
