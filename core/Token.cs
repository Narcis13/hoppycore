using System;

namespace hoppy {

    public class Token {

        public TokenType type ;

        public string value="";

        public int line=0;

        public int col =0;

        public int bindingPower=0;
        public Token(TokenType type, string value){
            this.type=type;
            this.value=value;
        }

        public Token(TokenType type, string value, int bp){
            this.type=type;
            this.value=value;
            this.bindingPower=bp;
        }

        public string toString(){

            return "Token: "+this.type+" > "+this.value;

        }

        public void setPosition(int l, int c){
            this.col=c;
            this.line=l;
        }
    }
}