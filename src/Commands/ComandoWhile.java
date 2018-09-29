/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Expression.Expressao;

/**
 *
 * @author Loures
 */
public class ComandoWhile extends Comando implements Condicao{
    private int linhaEnd;
    private int linhaStart;
    
    private Expressao raiz;

    
    public ComandoWhile(int linhaStart, Expressao raiz){
        this.linhaStart = linhaStart;
        this.raiz = raiz;
    }
    
    public void setLinhaEnd(int linhaEnd) {
        this.linhaEnd = linhaEnd;
    }
    
    public int executa() {
        double condicaoWhile = raiz.avalia();
        System.out.println("condicaoWhile = "+condicaoWhile);
        //System.out.println("linhaStart = "+linhaStart);
        if(condicaoWhile == 1) { 
            //Se a condição for verdadeira, executa o bloco à partir do if
            return linhaStart +1;
        }
        //Se a condição for falsa, chama o bloco à partir do else
        return linhaEnd + 1;
    }

    @Override
    public int getLinhaEnd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

