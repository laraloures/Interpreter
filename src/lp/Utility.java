package lp;

class Utility {
    private static final String[] errorMsg = new String[]{"Error: Unmatched end-of-comment punctuation.", "Error: Unmatched start-of-comment punctuation.", "Error: Unclosed string.", "Error: Illegal character."};
    public static final int E_ENDCOMMENT = 0;
    public static final int E_STARTCOMMENT = 1;
    public static final int E_UNCLOSEDSTR = 2;
    public static final int E_UNMATCHED = 3;

    Utility() {
    }

    public static void assert_method(boolean bl) {
        if (!bl) {
            throw new Error("Error: Assertion failed.");
        }
    }

    public static void error(int n) {
        System.out.println(errorMsg[n]);
    }
}