import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.StackWalker.Option;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tarci
 */
public class Lexico {
    private char[] conteudo;
    private int indiceConteudo;

    public Lexico(String caminhoCodigoFonte){
        try {
            String conteudoStr;
            conteudoStr = new String(Files.readAllBytes(Paths.get(caminhoCodigoFonte)));
            this.conteudo = conteudoStr.toCharArray();
            this.indiceConteudo = 0;                        
        } catch (IOException ex) {
            ex.printStackTrace();
        }        
    }
    
    //Retorna próximo char
    private char nextChar(){
        return this.conteudo[this.indiceConteudo++];
    }
    
    //Verifica existe próximo char ou chegou ao final do código fonte
    private boolean hasNextChar(){
        return indiceConteudo < this.conteudo.length;
    }
    
    //Retrocede o índice que aponta para o "char da vez" em uma unidade
    private void back(){
        this.indiceConteudo--;
    }
    
    //Identificar se char é letra minúscula    
    private boolean isLetra(char c){
        return (c >= 'a') && (c <= 'z');
    }
    
    //Identificar se char é dígito
    private boolean isDigito(char c){
        return (c >= '0') && (c <= '9');
    }

    //Identificar se char é um espaço
    private boolean isSpace(char c){
        return (c == ' ') || (c == '\n') || (c == '\t') || (c == '\r');
    }

    //Identificar se char é Ponto
    private boolean isFloat(char c){
        return (c == '.'); 
    }

    //Identificar se char é Operador Relacional
    private boolean isOpR(char c){
        return (c == '=') || (c == '!') || (c == '>') || (c == '<'); 
    }

    //Identificar se char é Operador Relacional
    private boolean isOpA(char c){
        return (c == '+') || (c == '-') || (c == '*') ||(c == '/') ||(c == '%'); 
    }

    private boolean isOpL(char c){
        return (c == '&') || (c == '|') ; 
    }

    //Pega o caractere aspas
    public static char aspas(){
        String asc = "'";
        char i [] = asc.toCharArray();
        return i[0];
    }

    //Identificar se char é uma Aspa simples
    private boolean isAspas(char c){
        return (c == aspas()); 
    }

    //Identificar se é palavra reservada
    private boolean isReservada(String s){
        return (s.equals("public"))  || (s.equals("private")) || (s.equals("protected"))
        || (s.equals("abstract"))  || (s.equals("class")) || (s.equals("extends"))
        || (s.equals("final"))  || (s.equals("implements")) || (s.equals("interface"))
        || (s.equals("native"))  || (s.equals("new")) || (s.equals("static"))
        || (s.equals("strictfp"))  || (s.equals("synchronized")) || (s.equals("transient"))
        || (s.equals("volatile"))  || (s.equals("break")) || (s.equals("case"))
        || (s.equals("continue"))  || (s.equals("default")) || (s.equals("do"))
        || (s.equals("else"))  || (s.equals("for")) || (s.equals("if"))
        || (s.equals("instanceof"))  || (s.equals("return")) || (s.equals("switch"))
        || (s.equals("while"))  || (s.equals("assert")) || (s.equals("catch"))
        || (s.equals("finally"))  || (s.equals("throw")) || (s.equals("throws"))
        || (s.equals("try"))  || (s.equals("import")) || (s.equals("package"))
        || (s.equals("boolean"))  || (s.equals("byte")) || (s.equals("char"))
        || (s.equals("double"))  || (s.equals("float")) || (s.equals("int"))
        || (s.equals("long"))  || (s.equals("short")) || (s.equals("super"))
        || (s.equals("this"))  || (s.equals("void")) || (s.equals("const"))
        || (s.equals("goto")); 
    }
    
    //Identificar se char é caracter especial
    private boolean isEspecial(char c) {
        return (c == '@') || (c == '#') || (c == '$') || (c == '(') || (c == ')') 
        || (c == '[') || (c == ']') || (c == '{') || (c == '}');
    }   


    
    //Método retorna próximo token válido ou retorna mensagem de erro.
    public Token nextToken(){
        Token token = null;
        char c = nextChar();
        String lex  = "";
        int Option = 0;
        int realOrinte = 0;

        if (isDigito(c)) {
            Option = 1;
        }
        if (isLetra(c)) {
            Option = 2;
        }
        if (isAspas(c)) {
            Option = 3;
        }
        if (isOpA(c)) {
            Option = 4;
        }
        if (isOpR(c)) {
            Option = 5;
        }
        if (isEspecial(c)) {
            Option = 6;
        }
        if (isOpL(c)) {
            Option = 7;
        }
        while (hasNextChar()) {
            switch (Option) {
                case 1:
                if (isDigito(c)) {
                    lex += c;
                }
                if (isFloat(c)) {
                    lex += c;
                    realOrinte = 1;
                }
                if (isSpace(c)) {
                    if (realOrinte == 0) {
                        token = new Token(lex, Token.TIPO_INTEIRO);
                        lex = "";
                        return token;
                    } else {
                        token = new Token(lex, Token.TIPO_REAL);
                        lex = "";
                        return token;
                    }
                }
                c = nextChar();
                break;
                
                case 2:
                if (isLetra(c)) {
                    lex += c;
                }
                if (isDigito(c)) {
                    lex += c;
                }
                if (c == '$' || c == '_') {
                    lex += c;
                }
                if (isSpace(c)) {
                    if (isReservada(lex)) {
                    token = new Token(lex, Token.TIPO_PALAVRA_RESERVADA);
                    lex = "";
                    return token;
                    }
                    if (!isReservada(lex)) {
                        token = new Token(lex, Token.TIPO_IDENTIFICADOR);
                        lex = "";
                        return token;
                    }
                }
                c = nextChar();
                break;

                case 3:
                if (isLetra(c)) {
                    lex += c;
                }
                if (isAspas(c)) {
                    lex += c;
                }
                if (isSpace(c)) {
                    token = new Token(lex, Token.TIPO_CHAR);
                    lex = "";
                    return token;
                }
                c = nextChar();
                break;

                case 4:
                if (isOpA(c)) {
                    lex += c;
                }
                if (isSpace(c)) {
                    token = new Token(lex, Token.TIPO_OPERADOR_ARITMETICO);
                    lex = "";
                    return token;
                }
                c = nextChar();
                break;
                
                case 5:
                if (isOpR(c)) {
                    lex += c;
                }
                if (isSpace(c)) {
                    token = new Token(lex, Token.TIPO_OPERADOR_RELACIONAL);
                    lex = "";
                    return token;
                }
                c = nextChar();
                break;

                case 6:
                if (isEspecial(c)) {
                    lex += c;
                }
                if (isSpace(c)) {
                    token = new Token(lex, Token.TIPO_CARACTER_ESPECIAL);
                    lex = "";
                    return token;
                }
                c = nextChar();
                break;

                case 7:
                if (isOpL(c)) {
                    lex += c;
                }
                if (isSpace(c)) {
                    token = new Token(lex, Token.TIPO_OPERADOR_LOGICO);
                    lex = "";
                    return token;
                }
                c = nextChar();
                break;

                default:
                    break;
            }
        }     
        
        return token;
  }

    
}