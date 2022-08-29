public class Token {
    public static int TIPO_INTEIRO = 0; //OK
    public static int TIPO_REAL = 1; //OK
    public static int TIPO_CHAR = 2; //OK
    public static int TIPO_IDENTIFICADOR = 3; //OK
    public static int TIPO_OPERADOR_RELACIONAL = 4; //OK
    public static int TIPO_OPERADOR_ARITMETICO = 5; //OK
    public static int TIPO_CARACTER_ESPECIAL = 6; //OK
    public static int TIPO_PALAVRA_RESERVADA = 7; //OK
    public static int TIPO_OPERADOR_LOGICO = 8; //OK
    public static int TIPO_FIM_CODIGO = 99;
    
    private int tipo; //tipo do token
    private String lexema; //conteúdo do token
    
    public Token(String lexema, int tipo){
        this.lexema = lexema;
        this.tipo = tipo;
    }
    
    public String getLexema(){
        return this.lexema;
    }
    
    public int getTipo(){
        return this.tipo;
    }
    
    @Override
    public String toString()
    {
        switch(this.tipo){
            case 0:
                return this.lexema + " - INTEIRO" ;
            case 1:
                return this.lexema + " - REAL";
            case 2:
                return this.lexema + " - CHAR";
            case 3:
                return this.lexema + " - IDENTIFICADOR";
            case 4:
                return this.lexema + " - OPERADOR_RELACIONAL";
            case 5:
                return this.lexema + " - OPERADOR_ARITMETICO";
            case 6:
                return this.lexema + " - CARACTER_ESPECIAL";
            case 7:
                return this.lexema + " - PALAVRA_RESERVADA";
            case 8:
                return this.lexema + " - TIPO_OPERADOR_LOGICO";    
            case 99:
                return this.lexema + " - FIM_CODIGO";    
        }
        return "";
    }
    
    
}