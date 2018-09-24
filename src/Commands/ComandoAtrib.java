package Commands;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Expression.Expressao;
import Interpreter.Variaveis;
/**
 *
 * @author Aluno
 */
public class ComandoAtrib extends Comando{

    private int linha;
    private char var;
    private Expressao exp;
    
    
    public ComandoAtrib(int linha, char var, Expressao exp) {
        this.linha = linha;
        this.var = var;
        this.exp = exp;
    }
    
    @Override
    public int executa() {
        try{
            Variaveis.var[var-97] = (float)exp.avalia();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return 1;
        }
        return linha+1;
    }
    
}
