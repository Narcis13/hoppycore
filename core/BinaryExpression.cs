using System;

namespace hoppy {

    public class BinaryExpression : Node {

      public Node first = null;

      public Node second = null;

      public Token op = null;

      public BinaryExpression(Node f, Token op, Node s){

          this.arity="binary";
          this.type="binary_expression";

          this.first=f;
          this.second=s;
          this.op=op;




      }
    }
}