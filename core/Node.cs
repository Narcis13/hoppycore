using System;

namespace hoppy {

    public class Node {

        public Token originalToken =null;

        public string arity ="abstract";

        public string type="abstract";

        public string Value;

        public Node(Token t=null){
            this.originalToken = t;
            if(t!=null){
                this.type=t.type.ToString();
                this.Value=t.value;
            }
        }




    }
}