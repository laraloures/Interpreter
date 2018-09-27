/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Interpreter.Variaveis;

/**
 *
 * @author Nelore
 */
public class ComandoEndFor extends Comando{

    private int linhaStart;
    private String tipo_for;
    private char variavel_loop;
    
    public ComandoEndFor(int linhaStart, String tipo_for, char variavel_loop) {
        this.linhaStart = linhaStart;
        this.tipo_for = tipo_for;
        this.variavel_loop = variavel_loop;
    }

    @Override
    public int executa() {
        if(tipo_for.equals("to")) {
            Variaveis.var[variavel_loop-97]++;
        } else {
            Variaveis.var[variavel_loop-97]--;
        }
        return linhaStart;
    }
    
}
