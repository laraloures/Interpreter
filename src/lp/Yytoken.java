package lp;

class Yytoken {
    public int m_index;
    public String m_text;
    public int m_line;
    public int m_charBegin;
    public int m_charEnd;

    Yytoken(int n, String string, int n2, int n3, int n4) {
        this.m_index = n;
        this.m_text = new String(string);
        this.m_line = n2;
        this.m_charBegin = n3;
        this.m_charEnd = n4;
    }

    public String toString() {
        return "Token #" + this.m_index + ": " + this.m_text + " (line " + this.m_line + ")";
    }
}