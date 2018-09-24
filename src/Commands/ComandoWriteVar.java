package Commands;

import Interpreter.Variaveis;


public class ComandoWriteVar extends Comando {
   
    char variavel;
   	
    public ComandoWriteVar(int lin, String txt) {
       linha= lin;
       variavel= txt.charAt(0);
    }
   
    public int executa() {
        System.out.println(""+Variaveis.var[variavel-97]);
        return linha+1;
    }
   
}