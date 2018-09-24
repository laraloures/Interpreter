/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Expression.Expressao;

/**
 *
 * @author Nelore
 */
public class ComandoIf extends Comando implements Condicao{

    private int linhaIf;
    private int linhaEnd;
    
    private Expressao raiz;

    
    public ComandoIf(int linhaEnd, Expressao raiz){
        this.linhaEnd = linhaEnd;
        this.raiz = raiz;
    }
    
    public void setLinhaEnd(int linhaEnd) {
        this.linhaEnd = linhaEnd;
    }
    
    public int executa() {
        double condicaoIf = raiz.avalia();
        if(condicaoIf == 1) { 
            //Se a condição for verdadeira, executa o bloco à partir do if
            return linhaEnd + 1;
        }
        //Se a condição for falsa, chama o bloco à partir do else
        return linhaEnd;
    }
    
}
