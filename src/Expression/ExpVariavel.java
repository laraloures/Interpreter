/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expression;

import Interpreter.Variaveis;

/**
 *
 * @author ntic
 */
public class ExpVariavel extends Expressao{
    private char nomeVar;
    public ExpVariavel(char nomeVar){
        this.nomeVar = nomeVar;
    }
    public double avalia(){
        return Variaveis.var[nomeVar-97];
    }
}
