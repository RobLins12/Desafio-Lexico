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
        return (c == '='); 
    }

    //Identificar se char é Operador Relacional
    private boolean isOpA(char c){
        return (c == '+') || (c == '-') || (c == '*') ||(c == '/') ||(c == '%'); 
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
            
                default:
                    break;
            }
        }     
        
        return token;
  }   
}