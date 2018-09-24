package lp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import lp.Utility;
import lp.Yytoken;

class Yylex {
    private final int YY_BUFFER_SIZE = 512;
    private final int YY_F = -1;
    private final int YY_NO_STATE = -1;
    private final int YY_NOT_ACCEPT = 0;
    private final int YY_START = 1;
    private final int YY_END = 2;
    private final int YY_NO_ANCHOR = 4;
    private final int YY_BOL = 128;
    private final int YY_EOF = 129;
    private int comment_count = 0;
    private BufferedReader yy_reader;
    private int yy_buffer_index;
    private int yy_buffer_read;
    private int yy_buffer_start;
    private int yy_buffer_end;
    private char[] yy_buffer;
    private int yychar;
    private int yyline;
    private boolean yy_at_bol;
    private int yy_lexical_state;
    private boolean yy_eof_done = false;
    private final int YYINITIAL = 0;
    private final int COMMENT = 1;
    private final int[] yy_state_dtrans = new int[]{0, 34};
    private boolean yy_last_was_cr = false;
    private final int YY_E_INTERNAL = 0;
    private final int YY_E_MATCH = 1;
    private String[] yy_error_string = new String[]{"Error: Internal error.\n", "Error: Unmatched input.\n"};
    private int[] yy_acpt = new int[]{0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 0, 4, 0, 0};
    private int[] yy_cmap;
    private int[] yy_rmap;
    private int[][] yy_nxt;

    Yylex(Reader reader) {
        this();
        if (null == reader) {
            throw new Error("Error: Bad input stream initializer.");
        }
        this.yy_reader = new BufferedReader(reader);
    }

    Yylex(InputStream inputStream) {
        this();
        if (null == inputStream) {
            throw new Error("Error: Bad input stream initializer.");
        }
        this.yy_reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    private Yylex() {
        this.yy_cmap = this.unpackFromString(1, 130, "20:8,15:2,17,20:2,16,20:18,15,20,18,20:5,3,4,9,7,1,8,22,10,21:10,14,2,12,11,13,20:2,23:26,5,19,6,20,24,20,23:26,20:5,0:2")[0];
        this.yy_rmap = this.unpackFromString(1, 35, "0,1:12,2,3,4,5,6,7,8,1:6,9,10,1,11,6,9,12,13,14")[0];
        this.yy_nxt = this.unpackFromString(15, 25, "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,27,16,17,28:2,18,28,19,28,-1:36,20,-1,21,-1:22,22,-1:24,23,-1:28,16,-1,16,-1:8,17:16,-1,25,29,17:5,-1:21,18,31,-1:23,19,-1,19:2,-1:21,26,-1:20,24,-1:8,17:14,32,17,33,30,29,17:5,-1,17:14,32,17,33,25,29,17:5,-1:15,33,-1,33,-1,17,-1:5,1,-1:24");
        this.yy_buffer = new char[512];
        this.yy_buffer_read = 0;
        this.yy_buffer_index = 0;
        this.yy_buffer_start = 0;
        this.yy_buffer_end = 0;
        this.yychar = 0;
        this.yyline = 0;
        this.yy_at_bol = true;
        this.yy_lexical_state = 0;
    }

    private void yybegin(int n) {
        this.yy_lexical_state = n;
    }

    private int yy_advance() throws IOException {
        int n;
        if (this.yy_buffer_index < this.yy_buffer_read) {
            return this.yy_buffer[this.yy_buffer_index++];
        }
        if (0 != this.yy_buffer_start) {
            int n2 = this.yy_buffer_start;
            int n3 = 0;
            while (n2 < this.yy_buffer_read) {
                this.yy_buffer[n3] = this.yy_buffer[n2];
                ++n2;
                ++n3;
            }
            this.yy_buffer_end -= this.yy_buffer_start;
            this.yy_buffer_start = 0;
            this.yy_buffer_read = n3;
            this.yy_buffer_index = n3;
            n = this.yy_reader.read(this.yy_buffer, this.yy_buffer_read, this.yy_buffer.length - this.yy_buffer_read);
            if (-1 == n) {
                return 129;
            }
            this.yy_buffer_read += n;
        }
        while (this.yy_buffer_index >= this.yy_buffer_read) {
            if (this.yy_buffer_index >= this.yy_buffer.length) {
                this.yy_buffer = this.yy_double(this.yy_buffer);
            }
            if (-1 == (n = this.yy_reader.read(this.yy_buffer, this.yy_buffer_read, this.yy_buffer.length - this.yy_buffer_read))) {
                return 129;
            }
            this.yy_buffer_read += n;
        }
        return this.yy_buffer[this.yy_buffer_index++];
    }

    private void yy_move_end() {
        if (this.yy_buffer_end > this.yy_buffer_start && '\n' == this.yy_buffer[this.yy_buffer_end - 1]) {
            --this.yy_buffer_end;
        }
        if (this.yy_buffer_end > this.yy_buffer_start && '\r' == this.yy_buffer[this.yy_buffer_end - 1]) {
            --this.yy_buffer_end;
        }
    }

    private void yy_mark_start() {
        int n = this.yy_buffer_start;
        while (n < this.yy_buffer_index) {
            if ('\n' == this.yy_buffer[n] && !this.yy_last_was_cr) {
                ++this.yyline;
            }
            if ('\r' == this.yy_buffer[n]) {
                ++this.yyline;
                this.yy_last_was_cr = true;
            } else {
                this.yy_last_was_cr = false;
            }
            ++n;
        }
        this.yychar = this.yychar + this.yy_buffer_index - this.yy_buffer_start;
        this.yy_buffer_start = this.yy_buffer_index;
    }

    private void yy_mark_end() {
        this.yy_buffer_end = this.yy_buffer_index;
    }

    private void yy_to_mark() {
        this.yy_buffer_index = this.yy_buffer_end;
        this.yy_at_bol = this.yy_buffer_end > this.yy_buffer_start && ('\r' == this.yy_buffer[this.yy_buffer_end - 1] || '\n' == this.yy_buffer[this.yy_buffer_end - 1] || '\u07ec' == this.yy_buffer[this.yy_buffer_end - 1] || '\u07ed' == this.yy_buffer[this.yy_buffer_end - 1]);
    }

    private String yytext() {
        return new String(this.yy_buffer, this.yy_buffer_start, this.yy_buffer_end - this.yy_buffer_start);
    }

    private int yylength() {
        return this.yy_buffer_end - this.yy_buffer_start;
    }

    private char[] yy_double(char[] arrc) {
        char[] arrc2 = new char[2 * arrc.length];
        int n = 0;
        while (n < arrc.length) {
            arrc2[n] = arrc[n];
            ++n;
        }
        return arrc2;
    }

    private void yy_error(int n, boolean bl) {
        System.out.print(this.yy_error_string[n]);
        System.out.flush();
        if (bl) {
            throw new Error("Fatal Error.\n");
        }
    }

    private int[][] unpackFromString(int n, int n2, String string) {
        int n3 = -1;
        int n4 = 0;
        int n5 = 0;
        int[][] arrn = new int[n][n2];
        int n6 = 0;
        while (n6 < n) {
            int n7 = 0;
            while (n7 < n2) {
                if (n4 != 0) {
                    arrn[n6][n7] = n5;
                    --n4;
                } else {
                    int n8 = string.indexOf(44);
                    String string2 = n8 == -1 ? string : string.substring(0, n8);
                    string = string.substring(n8 + 1);
                    n3 = string2.indexOf(58);
                    if (n3 == -1) {
                        arrn[n6][n7] = Integer.parseInt(string2);
                    } else {
                        String string3 = string2.substring(n3 + 1);
                        n4 = Integer.parseInt(string3);
                        string2 = string2.substring(0, n3);
                        arrn[n6][n7] = n5 = Integer.parseInt(string2);
                        --n4;
                    }
                }
                ++n7;
            }
            ++n6;
        }
        return arrn;
    }

    public Yytoken yylex() throws IOException {
        int n = 4;
        int n2 = this.yy_state_dtrans[this.yy_lexical_state];
        int n3 = -1;
        int n4 = -1;
        boolean bl = true;
        this.yy_mark_start();
        int n5 = this.yy_acpt[n2];
        if (0 != n5) {
            n4 = n2;
            this.yy_mark_end();
        }
        do {
            int n6 = bl && this.yy_at_bol ? 128 : this.yy_advance();
            n3 = -1;
            n3 = this.yy_nxt[this.yy_rmap[n2]][this.yy_cmap[n6]];
            if (129 == n6 && bl) {
                return null;
            }
            if (-1 != n3) {
                n2 = n3;
                bl = false;
                n5 = this.yy_acpt[n2];
                if (0 == n5) continue;
                n4 = n2;
                this.yy_mark_end();
                continue;
            }
            if (-1 == n4) {
                throw new Error("Lexical Error: Unmatched Input.");
            }
            n = this.yy_acpt[n4];
            if (0 != (2 & n)) {
                this.yy_move_end();
            }
            this.yy_to_mark();
            switch (n4) {
                case -2: 
                case 1: {
                    break;
                }
                case 2: {
                    return new Yytoken(0, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -3: {
                    break;
                }
                case 3: {
                    return new Yytoken(1, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -4: {
                    break;
                }
                case 4: {
                    return new Yytoken(2, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -5: {
                    break;
                }
                case 5: {
                    return new Yytoken(3, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -6: {
                    break;
                }
                case 6: {
                    return new Yytoken(4, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -7: {
                    break;
                }
                case 7: {
                    return new Yytoken(5, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -8: {
                    break;
                }
                case 8: {
                    return new Yytoken(6, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -9: {
                    break;
                }
                case 9: {
                    return new Yytoken(7, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -10: {
                    break;
                }
                case 10: {
                    return new Yytoken(8, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -11: {
                    break;
                }
                case 11: {
                    return new Yytoken(9, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -12: {
                    break;
                }
                case 12: {
                    return new Yytoken(10, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -13: {
                    break;
                }
                case 13: {
                    return new Yytoken(12, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -14: {
                    break;
                }
                case 14: {
                    return new Yytoken(14, this.yytext(), this.yyline, this.yychar, this.yychar + 1);
                }
                case -15: {
                    break;
                }
                case 15: {
                    System.out.println("Illegal character: <" + this.yytext() + ">");
                    Utility.error(3);
                }
                case -16: {
                    break;
                }
                case -17: 
                case 16: {
                    break;
                }
                case 17: {
                    String string = this.yytext().substring(1, this.yytext().length());
                    Utility.error(2);
                    Utility.assert_method(string.length() == this.yytext().length() - 1);
                    return new Yytoken(41, string, this.yyline, this.yychar, this.yychar + string.length());
                }
                case -18: {
                    break;
                }
                case 18: {
                    return new Yytoken(18, this.yytext(), this.yyline, this.yychar, this.yychar + this.yytext().length());
                }
                case -19: {
                    break;
                }
                case 19: {
                    return new Yytoken(19, this.yytext(), this.yyline, this.yychar, this.yychar + this.yytext().length());
                }
                case -20: {
                    break;
                }
                case 20: {
                    return new Yytoken(13, this.yytext(), this.yyline, this.yychar, this.yychar + 2);
                }
                case -21: {
                    break;
                }
                case 21: {
                    return new Yytoken(11, this.yytext(), this.yyline, this.yychar, this.yychar + 2);
                }
                case -22: {
                    break;
                }
                case 22: {
                    return new Yytoken(15, this.yytext(), this.yyline, this.yychar, this.yychar + 2);
                }
                case -23: {
                    break;
                }
                case 23: {
                    return new Yytoken(16, this.yytext(), this.yyline, this.yychar, this.yychar + 2);
                }
                case -24: {
                    break;
                }
                case -25: 
                case 24: {
                    break;
                }
                case 25: {
                    String string = this.yytext().substring(1, this.yytext().length() - 1);
                    Utility.assert_method(string.length() == this.yytext().length() - 2);
                    return new Yytoken(17, string, this.yyline, this.yychar, this.yychar + string.length());
                }
                case -26: {
                    break;
                }
                case 26: {
                    return new Yytoken(18, this.yytext(), this.yyline, this.yychar, this.yychar + this.yytext().length());
                }
                case -27: {
                    break;
                }
                case 28: {
                    System.out.println("Illegal character: <" + this.yytext() + ">");
                    Utility.error(3);
                }
                case -28: {
                    break;
                }
                case 29: {
                    String string = this.yytext().substring(1, this.yytext().length());
                    Utility.error(2);
                    Utility.assert_method(string.length() == this.yytext().length() - 1);
                    return new Yytoken(41, string, this.yyline, this.yychar, this.yychar + string.length());
                }
                case -29: {
                    break;
                }
                case 30: {
                    String string = this.yytext().substring(1, this.yytext().length() - 1);
                    Utility.assert_method(string.length() == this.yytext().length() - 2);
                    return new Yytoken(17, string, this.yyline, this.yychar, this.yychar + string.length());
                }
                case -30: {
                    break;
                }
                case 32: {
                    String string = this.yytext().substring(1, this.yytext().length());
                    Utility.error(2);
                    Utility.assert_method(string.length() == this.yytext().length() - 1);
                    return new Yytoken(41, string, this.yyline, this.yychar, this.yychar + string.length());
                }
                case -31: {
                    break;
                }
                default: {
                    this.yy_error(0, false);
                }
                case -1: 
            }
            bl = true;
            n2 = this.yy_state_dtrans[this.yy_lexical_state];
            n3 = -1;
            n4 = -1;
            this.yy_mark_start();
            n5 = this.yy_acpt[n2];
            if (0 == n5) continue;
            n4 = n2;
            this.yy_mark_end();
        } while (true);
    }
}