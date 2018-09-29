package Interpreter;

import Commands.*;
import Expression.*;
import java.util.*;
import lp.ArquivoFonte;

public class Interpretador {
    private ArquivoFonte arq;
    private Vector comandos;   
    private String palavraAtual;
    private Stack pilha;
    private Stack pilhaLinha;
    //private String op;
    private Expressao raizArvoreExpressao;
    
    public Interpretador(String nome) {
        arq= new ArquivoFonte(nome);
        comandos= new Vector();
        
        //test
        pilhaLinha = new Stack();
    }
   
    public void listaArquivo() {
        String palavra;
        do {
            palavra= arq.proximaPalavra();
        } while (!palavra.equals("EOF"));
   }
   
    public void leArquivo() {
       String comandoAtual;
       int linha= 0;
       do {
            comandoAtual= arq.proximaPalavra();
            if(comandoAtual.equals("endp")){
                trataComandoEndp(linha);
                linha++;
            }else if(comandoAtual.equals("writeln")){
                trataComandoWriteln(linha);
                linha++;
            }else if(comandoAtual.equals("read")){
                trataComandoRead(linha);
                linha++;
            }else if(comandoAtual.equals("writeStr")){
                trataComandoWriteStr(linha);
                linha++;
            }else if(comandoAtual.equals("writeVar")){
                trataComandoWriteVar(linha);
                linha++;
            }else if(comandoAtual.equals("if")) {
                pilhaLinha.push(linha);
                trataComandoIf(linha);
                linha++; //linha++ provisória aqui
            } else if(comandoAtual.equals("else")){
                int startIf = (Integer)pilhaLinha.pop();
                pilhaLinha.push(linha);
                trataComandoElse(startIf, linha);
                linha++;
            } else if(comandoAtual.equals("endif")){
                int linhaStart = (Integer) pilhaLinha.pop();
                trataComandoEndif(linha, linhaStart);
                linha++;
            }else if(comandoAtual.equals("while")){
                pilhaLinha.push(linha);
                trataComandoWhile(linha);
                linha++;
            } else if(comandoAtual.equals("endw")){
                int linhaStart = (Integer) pilhaLinha.pop();
                trataComandoEndW(linha, linhaStart);
                linha++;
            }else if(comandoAtual.equals("for")){
                pilhaLinha.push(linha);
                trataComandoFor(linha);
                linha++;
            }else if(comandoAtual.equals("endfor")){
                int linhaStart = (Integer) pilhaLinha.pop();
                trataComandoEndFor(linhaStart, linha);
                //trataComandoFor(linha);
                linha++;
            /*  // Endfor repetido
            } else if(comandoAtual.equals("endfor")){
                int linhaStart = (Integer) pilhaLinha.pop();
                trataComandoEndFor(linha, linhaStart);
                linha++;
            */
            }else if((comandoAtual.charAt(0)>=97 && comandoAtual.charAt(0) <=122)) {
                if(arq.proximaPalavra().equals(":=")){
                    trataComandoAtrib(linha, comandoAtual.charAt(0));
                    linha++;
                }
            }
       } while (!comandoAtual.equals("endp"));
    }
   
    private void trataComandoEndp(int lin) {
        ComandoEndp c= new ComandoEndp(lin);
        comandos.addElement(c);
    }
   	   	
    private void trataComandoWriteln(int lin) {
        ComandoWriteln c= new ComandoWriteln(lin);
        comandos.addElement(c);
    }
    
    private void trataComandoRead(int lin) {
        if(!verificaPalavra(arq.proximaPalavra(), "(")) return;
        String aux = arq.proximaPalavra();
        char var = aux.charAt(0);
        if(!Character.isLetter(var)) return;
        if(!verificaPalavra(arq.proximaPalavra(), ")")) return;
        if(!verificaPalavra(arq.proximaPalavra(), ";")) return;
        ComandoRead c= new ComandoRead(lin,aux);
        comandos.addElement(c);
    }
    
    private void trataComandoWriteStr(int lin) {
        if(!verificaPalavra(arq.proximaPalavra(), "(")) return;
        String texto = arq.proximaPalavra();
        if(!verificaPalavra(arq.proximaPalavra(), ")")) return;
        if(!verificaPalavra(arq.proximaPalavra(), ";")) return;
        
        ComandoWriteStr c= new ComandoWriteStr(lin, texto);
        comandos.addElement(c);
        
    }
    
    private void trataComandoWriteVar(int lin) {
        if(!verificaPalavra(arq.proximaPalavra(), "(")) return;
        String aux = arq.proximaPalavra();
        char var = aux.charAt(0);
        if(!Character.isLetter(var)) return;
        if(!verificaPalavra(arq.proximaPalavra(), ")")) return;
        if(!verificaPalavra(arq.proximaPalavra(), ";")) return;
        ComandoWriteVar c= new ComandoWriteVar(lin, aux);
        comandos.addElement(c);
    }
    
    private void trataComandoAtrib(int linha, char var) {
        trataExpressao();
        ComandoAtrib ca = new ComandoAtrib(linha, var, raizArvoreExpressao);
        comandos.addElement(ca);
    }
    
    private void trataComandoIf(int linha){
        trataExpressao();
        ComandoIf ci = new ComandoIf(linha, raizArvoreExpressao);
        comandos.addElement(ci);
    }
    
    private void trataComandoElse(int linhaIf, int linhaElse){
        ComandoIf getIf = (ComandoIf)comandos.get(linhaIf);
        getIf.setLinhaEnd(linhaElse);
        ComandoElse ce = new ComandoElse(linhaElse);
        comandos.addElement(ce);
    }
    
    private void trataComandoEndif(int linha, int linhaStart){
        Condicao cmd= (Condicao) comandos.elementAt(linhaStart);
	cmd.setLinhaEnd(linha); 
        
        ComandoEndIf cei = new ComandoEndIf(linha);
        comandos.addElement(cei);
    }
    
    private void trataComandoWhile(int lin) {
        trataExpressao();
        //System.out.println("palavraAtual = ["+palavraAtual+"]");
        ComandoWhile cw= new ComandoWhile(lin, raizArvoreExpressao);
        comandos.addElement(cw);  
     }
    private void trataComandoEndW(int linhaEnd, int linhaStart){
        //Comando cmd = (Comando) comandos.elementAt(linhaStart);
        //Condicao x = (Condicao) comandos.elementAt(linhaStart);
	//x.setLinhaEnd(linhaEnd); 
        ComandoWhile cw = (ComandoWhile) comandos.elementAt(linhaStart);
        cw.setLinhaEnd(linhaEnd);
        ComandoEndW cew = new ComandoEndW(linhaStart, linhaEnd);
        comandos.addElement(cew);
        
    }
    private void trataComandoFor(int lin) {
       //trataExpressaoLogica();
       palavraAtual = arq.proximaPalavra();
       char variavelLoop = palavraAtual.charAt(0);
       palavraAtual = arq.proximaPalavra(); // pega o := (para atrib)
       palavraAtual = arq.proximaPalavra(); // pega o valor pra atribuição
       double valor_atrib = Double.parseDouble(palavraAtual);
       palavraAtual = arq.proximaPalavra(); // pega o "to" ou "downto"
       String tipo_for = palavraAtual;
       trataExpressao();
       ComandoFor c= new ComandoFor(lin, variavelLoop, valor_atrib, tipo_for, raizArvoreExpressao);
       comandos.addElement(c);  
    }
    
     private void trataComandoEndFor(int linhaStart, int linhaEnd){
        ComandoFor getFor = (ComandoFor) comandos.elementAt(linhaStart);
        getFor.setLinhaEnd(linhaEnd);
        ComandoEndFor endFor = new ComandoEndFor(linhaStart, getFor.getTipo_for(), getFor.getVariavel_loop());
        comandos.addElement(endFor);
        
    }
    
    private boolean verificaPalavra(String str, String chave) {
        return str.equals(chave);
    }
    
    private void trataExpressao() {
        palavraAtual= arq.proximaPalavra();
        pilha= new Stack();
        //expressao();
        expressaoLogica();
        //gambiarra inserida abaixo
        //System.out.println("palavraAtual = "+palavraAtual);
        //if(palavraAtual.equals("then") || palavraAtual.equals("do")) {
          //  arq.proximaPalavra();
        //}
        raizArvoreExpressao= (Expressao) pilha.pop();
    }
    
	
   private void expressaoLogica() {
      expressaoComparativa();
      while ( palavraAtual.equals("and") || palavraAtual.equals("or") || palavraAtual.equals("not") ) {
         String op= palavraAtual;
         palavraAtual= arq.proximaPalavra();
         expressaoComparativa();
         Object exp1= pilha.pop();
         Object exp2= pilha.pop();
         pilha.push(new ExpLogica(op,exp1,exp2));
      }  
   }

   private void expressaoComparativa() {
      expressao();
      while ( palavraAtual.equals("<") || palavraAtual.equals(">") || palavraAtual.equals(">=") || 
              palavraAtual.equals("<=") || palavraAtual.equals("<>") || palavraAtual.equals("=")  ) {
         String op= palavraAtual;
         palavraAtual= arq.proximaPalavra();
         expressao();
         Object exp2= pilha.pop();
         Object exp1= pilha.pop();
         pilha.push(new ExpLogica(op,exp1,exp2));
      }  
   }

   private void expressao() {
      termo();
      while ( palavraAtual.equals("+") || palavraAtual.equals("-") ) {
         String op= palavraAtual;
         palavraAtual= arq.proximaPalavra();
         termo();
         Object exp2= pilha.pop();
         if( pilha.empty() ){
            pilha.push(new ExpConstante(0.0));
         }
         Object exp1= pilha.pop();
         pilha.push(new ExpBinaria(op,exp1,exp2));
      }  
   }

   private void termo() {
      fator();
      while ( palavraAtual.equals("*") || palavraAtual.equals("/") ) {
         String op= palavraAtual;
         palavraAtual= arq.proximaPalavra();
         fator();
         Object exp2= pilha.pop();
         Object exp1= pilha.pop();
         pilha.push(new ExpBinaria(op,exp1,exp2));
      }  
   }


   private void fator() {
      /*if ( palavraAtual.equals("sqrt") ) {
         palavraAtual= arq.proximaPalavra();
         palavraAtual= arq.proximaPalavra();
         pilha.push(new ExpSqrt( palavraAtual ));
         palavraAtual= arq.proximaPalavra();        
         palavraAtual= arq.proximaPalavra();
      } */
         
      if ( palavraAtual.charAt(0) >= '0' && palavraAtual.charAt(0) <= '9'  ) {
         pilha.push(new ExpConstante( Double.parseDouble(palavraAtual) ));
         palavraAtual= arq.proximaPalavra();
      }
      
      else if ( palavraAtual.charAt(0) >= 'a' && palavraAtual.charAt(0) <= 'z'  ) {
         pilha.push(new ExpVariavel( palavraAtual.charAt(0) ));
         palavraAtual= arq.proximaPalavra();
      } 
                
      else if ( palavraAtual.equals("(") ) {
         palavraAtual= arq.proximaPalavra();
         expressaoLogica();
         
         if ( palavraAtual.equals(")") ) {
            palavraAtual= arq.proximaPalavra();
         }  
      }
         
   }
   
   public void executa() {
      Comando cmd;
      int pc= 0;
      do {
          cmd= (Comando) comandos.elementAt(pc);
          //System.out.println("pc ="+pc);
          pc= cmd.executa();
      } while (pc != -1);
   }   


    
  /*  
    private void expressao() {
        termo();
        while (palavraAtual.equals("+") || palavraAtual.equals("-")) {
            String op = palavraAtual;
            palavraAtual= arq.proximaPalavra();
            termo();
            Object exp1= pilha.pop();
            Object exp2= pilha.pop();
            pilha.push(new ExpBinaria(op,exp1,exp2));
          }  
    }

    private void termo() {
        fator();
        while (palavraAtual.equals("*") || palavraAtual.equals("/")) {
            String op = palavraAtual;
            palavraAtual= arq.proximaPalavra();
            fator();
            Object exp1= pilha.pop();
            Object exp2= pilha.pop();
            pilha.push(new ExpBinaria(op,exp1,exp2));
        }  
    }

    private void trataExpressaoLogica() {
        palavraAtual= arq.proximaPalavra();
        pilha= new Stack();
        expressaoLogica();
        raizArvoreExpressao= (Expressao) pilha.pop();
    }
    
    private void expressaoLogica() {
        expressaoComparativa();
        if (palavraAtual.equals("and") || palavraAtual.equals("or") || palavraAtual.equals("not")) {
            String op = palavraAtual;
            palavraAtual= arq.proximaPalavra();
            expressaoComparativa();
            Object exp2= pilha.pop();
            Object exp1= pilha.pop();
            pilha.push(new ExpLogica(op,exp1,exp2));
          }  
    }
    
    private void expressaoComparativa() {
        expressaoRelacional();
        if (palavraAtual.equals(">") || palavraAtual.equals("<") || palavraAtual.equals("<=") || palavraAtual.equals(">=") || palavraAtual.equals("<>") || palavraAtual.equals("=")) {
            String op = palavraAtual;
            palavraAtual= arq.proximaPalavra();
            expressaoRelacional();
            Object exp2= pilha.pop();
            Object exp1= pilha.pop();
            pilha.push(new ExpLogica(op,exp1,exp2));
            
            if(!palavraAtual.equals("do"))
            
                palavraAtual = arq.proximaPalavra();
        }  
    }
    
    private void expressaoRelacional() {
        termoLogico();
        while (palavraAtual.equals("+") || palavraAtual.equals("-")) {
            String op = palavraAtual;
            palavraAtual= arq.proximaPalavra();
            termoLogico();
            Object exp1= pilha.pop();
            Object exp2= pilha.pop();
            if( pilha.empty() ){
               pilha.push(new ExpConstante(0.0));
            }
            pilha.push(new ExpBinaria(op,exp1,exp2));
          }  
    }

    private void termoLogico() {
        fatorLogico();
        while (palavraAtual.equals("*") || palavraAtual.equals("/")) {
            String op = palavraAtual;
            palavraAtual= arq.proximaPalavra();
            fatorLogico();
            Object exp1= pilha.pop();
            Object exp2= pilha.pop();
            pilha.push(new ExpBinaria(op,exp1,exp2));
        }  
    }

    private void fatorLogico(){
        if (palavraAtual.charAt(0)>=97 && palavraAtual.charAt(0) <=122) { // fazer verificacao de variavel
            char ch = palavraAtual.charAt(0);
            pilha.push(new ExpVariavel(ch));
            palavraAtual= arq.proximaPalavra();
        } else if (Character.isDigit(palavraAtual.charAt(0))){ // verificar se e um valor
            pilha.push(new ExpConstante(Double.parseDouble(palavraAtual)));
            palavraAtual= arq.proximaPalavra();
        } else if (verificaPalavra(palavraAtual, "(")){
            palavraAtual= arq.proximaPalavra();
            expressaoLogica();
            if (verificaPalavra(palavraAtual, ")")){
                palavraAtual= arq.proximaPalavra();
            }
        } else if (verificaPalavra(palavraAtual, "then")) {
            palavraAtual = arq.proximaPalavra();
        }   
    
    }
    
    private void fator() {
        if (palavraAtual.charAt(0)>=97 && palavraAtual.charAt(0) <=122) { // fazer verificacao de variavel
            char ch = palavraAtual.charAt(0);
            pilha.push(new ExpVariavel(ch));
            palavraAtual= arq.proximaPalavra();
        }
        else if (Character.isDigit(palavraAtual.charAt(0))){ // verificar se e um valor
            pilha.push(new ExpConstante(Double.parseDouble(palavraAtual)));
            palavraAtual= arq.proximaPalavra();
        }   
        else if (verificaPalavra(palavraAtual, "(")){
            palavraAtual= arq.proximaPalavra();
            expressao();
            if (verificaPalavra(palavraAtual, ")")){
                palavraAtual= arq.proximaPalavra();
            }
        } else if (verificaPalavra(palavraAtual, "then")) {
            palavraAtual = arq.proximaPalavra();
        }   
    }
    */
    
}
