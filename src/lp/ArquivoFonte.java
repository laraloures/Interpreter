package lp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArquivoFonte {
    private FileReader arq;
    private BufferedReader dados;
    private Yylex yy;

    public ArquivoFonte(String string) {
        try {
            this.arq = new FileReader(string);
            this.dados = new BufferedReader(this.arq);
            this.yy = new Yylex(this.dados);
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public String proximaPalavra() {
        try {
            Yytoken yytoken = this.yy.yylex();
            if (yytoken == null) {
                return "EOF";
            }
            return yytoken.m_text;
        }
        catch (IOException iOException) {
            System.out.println(iOException);
            return "";
        }
    }
}