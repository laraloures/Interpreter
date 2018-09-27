/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

/**
 *
 * @author Aluno
 */
public class ComandoElse extends Comando implements Condicao{

    private int linhaEnd;
    private int linhaElse;
    
    public ComandoElse(int linhaElse){
        this.linhaElse = linhaElse;
    }
    
    public void setLinhaEnd(int linhaEnd){
        this.linhaEnd = linhaEnd;
    }
    
    public int executa() {
        return linhaEnd+1;
    }

    @Override
    public int getLinhaEnd() {
        return linhaEnd;
    }
}
