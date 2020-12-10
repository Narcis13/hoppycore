using System;

namespace hoppy {

    public class Token {

        TokenType type =null;

        string value='';

        public Token(TokenType type, string value){
            this.type=type;
            this,value=value;
        }

        public string toString(){

            return "Token: "+this.type+" > "+this.value;

        }
    }
}