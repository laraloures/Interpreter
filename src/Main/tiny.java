package Main;
import Interpreter.Interpretador;

public class tiny {
   
   public static void main(String[] args) {
      Interpretador inter;
      
      
      if (args.length == 1) {
        //inter.listaArquivo();
         inter = new Interpretador(args[0]); // esta pegando o caminho de onde esta o .tiny de exemplo
         inter.leArquivo();
         inter.executa();
      }
      else if ((args.length == 2) && (args[1].equals("-l"))) {
         inter = new Interpretador(args[0]); // esta pegando o caminho de onde esta o .tiny de exemplo
         inter.listaArquivo();
      } else if (args.length == 0) {
         String endereco_arq = "./src/testes/while.tiny";
         inter= new Interpretador(endereco_arq);  
         //inter.listaArquivo();
         inter.leArquivo();
         inter.executa();
      }
   }
}
