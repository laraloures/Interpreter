/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Expression.Expressao;
import Interpreter.Variaveis;

/**
 *
 * @author Loures
 */
public class ComandoFor extends Comando implements Condicao{
    private int linhaEnd;
    private int linhaStart;
    private char variavel_loop;
    private double valor_atrib;
    private String tipo_for;
    private Expressao raiz;
    
    //variáveis de controle internas
    private boolean var_initialized = false;

    
    //new ComandoFor(lin, variavelLoop, valor_atrib, tipo_for, raizArvoreExpressao)
    public ComandoFor(int linhaStart, char variavel_loop, double valor_atrib, String tipo_for, Expressao raiz){
        this.linhaStart = linhaStart;
        this.variavel_loop = variavel_loop;
        this.valor_atrib = valor_atrib;
        this.tipo_for = tipo_for;
        this.raiz = raiz;
    }
    
    public void setLinhaEnd(int linhaEnd) {
        this.linhaEnd = linhaEnd;
    }
    
    public String getTipo_for(){
        return tipo_for;
    }
    
    public char getVariavel_loop(){
        return variavel_loop;
    }
    
    public int executa() {
        if(!var_initialized) {
            Variaveis.var[variavel_loop - 97] = (float)valor_atrib;
            var_initialized = true;
        }
        
        if(tipo_for.equals("to")){
            //for de incremento
            if(Variaveis.var[variavel_loop - 97] <= raiz.avalia()) {
                return linha+1;
            } else {
                var_initialized = false;
                return linhaEnd+1;
            }
        } else if (tipo_for.equals("downto")){
            //for de decremento
            if(Variaveis.var[variavel_loop - 97] >= raiz.avalia()) {
                return linha+1;
            } else {
                var_initialized = false;
                return linhaEnd+1;
            }
        }
        return linhaEnd+1;
    }

    @Override
    public int getLinhaEnd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
