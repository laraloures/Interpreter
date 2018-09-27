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
public class ComandoEndW extends Comando{

    private int linhaStart;
    private int linhaEnd;
    
    public ComandoEndW(int linhaStart, int linhaEnd) {
        this.linhaStart = linhaStart;
        this.linhaEnd = linhaEnd;
    }
    public int executa() {
        return linhaStart;
    }
    
}
