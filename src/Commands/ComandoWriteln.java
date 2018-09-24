package Commands;


public class ComandoWriteln extends Comando {
   	
    public ComandoWriteln(int lin) {
       linha= lin;
    }
   
    public int executa() {
       System.out.println();
       return linha+1;
    }
}