package Commands;

public class ComandoEndIf extends Comando {

    public ComandoEndIf(int lin) {
       linha = lin;
    }
   
    public int executa() {
       return linha + 1;
    }
    
}
