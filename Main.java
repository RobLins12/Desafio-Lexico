public class Main {
	public static void main(String[] args) {
        Lexico lexico = new Lexico("/workspace/Desafio-Lexico/codigo.txt");
        Token t = null;
        while((t = lexico.nextToken()) != null){
            System.out.println(t.toString());
        }

    }

}