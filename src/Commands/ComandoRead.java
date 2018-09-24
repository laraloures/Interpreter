package Commands;

import Interpreter.Variaveis;
import java.io.*;  

public class ComandoRead extends Comando {
   
    BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
    char variavel;
   	
    public ComandoRead(int lin, String txt) {
       linha= lin;
       variavel= txt.charAt(0);
    }
   
    public int executa() {    	
        try {
            float valor = Float.parseFloat(teclado.readLine());
            Variaveis.var[variavel-97] = valor;
        }
        catch( Exception e) {
            System.out.println("ERRO: "+e);
        }
        return linha+1;
    }
}