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

    
    public ComandoIf(int linhaIf, Expressao raiz){
        this.linhaIf = linhaIf;
        this.raiz = raiz;
    }
    
    public void setLinhaEnd(int linhaEnd) {
        this.linhaEnd = linhaEnd;
    }
    
    public int executa() {
        double condicaoIf = raiz.avalia();
        if(condicaoIf == 1) { 
            //Se a condição for verdadeira, executa o bloco à partir do if
            return linhaIf + 1;
        }
        //Se a condição for falsa, chama o bloco à partir do else
        return linhaEnd + 1;
    }

    @Override
    public int getLinhaEnd() {
        return linhaEnd;
    }
    
}
