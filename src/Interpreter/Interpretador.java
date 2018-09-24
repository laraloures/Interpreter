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
    private String op;
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
                trataComandoElse(linha, startIf);
                linha++;
            } else if(comandoAtual.equals("endif")){
                int linhaStart = (Integer) pilhaLinha.pop();
                trataComandoEndif(linha, linhaStart);
                linha++;
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
        trataExpressaoLogica();
        ComandoIf ci = new ComandoIf(linha, raizArvoreExpressao);
        comandos.addElement(ci);
    }
    
    private void trataComandoElse(int linhaIf, int linhaElse){
        ComandoIf getIf = (ComandoIf)comandos.get(linhaElse);
        getIf.setLinhaEnd(linhaIf+1);
        ComandoElse ce = new ComandoElse(linhaIf);
        comandos.addElement(ce);
    }
    
    private void trataComandoEndif(int linhaStart, int linhaEnd){
        Comando cmd = (Comando) comandos.elementAt(linhaEnd);
        Condicao x = (Condicao) comandos.elementAt(linhaEnd);
	x.setLinhaEnd(linhaStart); 
        
    }
      
    private boolean verificaPalavra(String str, String chave) {
        return str.equals(chave);
    }
    
    public void executa() {
      Comando cmd;
      int pc= 0;
      do {
         cmd= (Comando) comandos.elementAt(pc);
         pc= cmd.executa();
      } while (pc != -1);
   }   
    
    private void trataExpressao() {
        palavraAtual= arq.proximaPalavra();
        pilha= new Stack();
        expressao();
        raizArvoreExpressao= (Expressao) pilha.pop();
    }
    
    private void trataExpressaoLogica() {
        palavraAtual= arq.proximaPalavra();
        pilha= new Stack();
        expressaoLogica();
        raizArvoreExpressao= (Expressao) pilha.pop();
    }
    
    
    private void expressaoLogica() {
        termoLogico();
        if (palavraAtual.equals("and") || palavraAtual.equals("or")) {
            System.out.println(palavraAtual);
            op= palavraAtual;
            palavraAtual= arq.proximaPalavra();
            if(palavraAtual.equals("("))
                palavraAtual = arq.proximaPalavra();
            termoLogico();
            Object exp1= pilha.pop();
            Object exp2= pilha.pop();
            System.out.println(op);
            pilha.push(new ExpLogica(op,exp1,exp2));
          }  
    }
    
    private void expressao() {
        termo();
        while (palavraAtual.equals("+") || palavraAtual.equals("-")) {
            op= palavraAtual;
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
            op= palavraAtual;
            palavraAtual= arq.proximaPalavra();
            fator();
            Object exp1= pilha.pop();
            Object exp2= pilha.pop();
            pilha.push(new ExpBinaria(op,exp1,exp2));
        }  
    }

    private void termoLogico() {
        fatorLogico();
        if (palavraAtual.equals(">") || palavraAtual.equals("<") || palavraAtual.equals("<=") || palavraAtual.equals(">=") || palavraAtual.equals("<>") || palavraAtual.equals("=")) {
            op= palavraAtual;
            palavraAtual= arq.proximaPalavra();
            fatorLogico();
            Object exp1= pilha.pop();
            Object exp2= pilha.pop();
            System.out.println(op);
            pilha.push(new ExpLogica(op,exp1,exp2));
            palavraAtual = arq.proximaPalavra();
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
}
