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
public class ExpBinaria extends Expressao{
    private Object exp1;
    private Object exp2;
    private String op;
    public ExpBinaria (String op, Object exp1, Object exp2){
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    public double avalia(){
        Expressao ex1 = (Expressao)exp1;
        Expressao ex2 = (Expressao)exp2;
        switch(op) {
            case "+":
                return (ex1.avalia()+ex2.avalia());
            case "-":
                return (ex1.avalia()-ex2.avalia());    
            case "/":
                return (ex1.avalia()/ex2.avalia());
            case "*":
                return (ex1.avalia()*ex2.avalia());
        }
        return 0;
    }
    
}
