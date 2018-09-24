/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expression;

/**
 *
 * @author ntic
 */
public class ExpConstante extends Expressao{
    private double valor;
    public ExpConstante(double valor){
        this.valor = valor;
    }
    public double avalia(){
       return valor;
    }
    
}
