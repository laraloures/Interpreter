/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Expression;

/**
 *
 * @author Nelore
 */
public class ExpLogica extends Expressao{
    private Object exp1;
    private Object exp2;
    private String op;
    
    public ExpLogica (String op, Object exp1, Object exp2){
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    public double avalia(){
        Expressao ex1 = (Expressao)exp1;
        Expressao ex2 = (Expressao)exp2;
        System.out.println("Teste: "+op);
        switch(op) {
            case "<":
                if(ex1.avalia() < ex2.avalia()) 
                    return 1; 
            case ">":
                if(ex1.avalia() > ex2.avalia()) 
                    return 1;
            case "<=":
                if(ex1.avalia() <= ex2.avalia()) 
                    return 1;
            case ">=":
                if(ex1.avalia() >= ex2.avalia()) 
                    return 1;
            case "<>":
                if(ex1.avalia() != ex2.avalia()) 
                    return 1;
            case "=":   
                if(ex1.avalia() == ex2.avalia()) 
                    return 1;
            case "and":
                if(ex1.avalia()==1 && ex2.avalia()==1) 
                    return 1;
            case "or":
                if(ex1.avalia()==1 || ex2.avalia()==1) 
                    return 1;
        }
        return 0;
    }
}
