import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        /*
        System.out.println("result = " + new Main().solution2("abcdcba"));
        System.out.println("result = " + new Main().solution("abcdcba"));
        System.out.println("result = " + new Main().solution("abcddcba"));
        System.out.println("result = " + new Main().solution("a"));

        */
        System.out.println("result = " + new Main().findLongestPalindrome("abcdcbac"));
    }

    private int solution2(String s) {
        int i = s.length() / 2;  // 8 ->4  7 -> 3
        char[] chs = s.toCharArray();
        int[] len = new int[chs.length];

        while(i >= 0) {
            int m = i, n = i;
            int l = -1;
            while (m >= 0 && n < chs.length && chs[m] == chs[n]) {
                l += 2;
                m--;
                n++;
            }
            len[i] = l;
            i--;
        }
        i = (s.length() / 2) + 1;
        while(i < s.length()) {
            int m = i, n = i;
            int l = -1;
            while (m >= 0 && n < chs.length && chs[m] == chs[n]) {
                l += 2;
                m--;
                n++;
            }
            len[i] = l;
            i--;
        }
        return 0;
    }

    private int solution(String s) {
        int length = s.length();
        if (length > 1) {
            for (int l = length; l > 0; l--) {
                for (int i = 0; i + l <= length; i++) {
                    String target = s.substring(i, l+i);
                    //System.out.println("trying " + target);
                    if (palindrome(target)) {
                        return l;
                    }
                }
            }
        }
        return 1;
    }
    private boolean palindrome(String s) {
        int l = s.length();
        String f = s.substring(0, l/2);
        String b = s.substring(l-f.length());
        return new StringBuilder(b).reverse().toString().equals(f);
    }

    public String findLongestPalindrome(String s) {
        if (s==null || s.length()==0)
            return "";

        char[] s2 = addBoundaries(s.toCharArray());
        int[] p = new int[s2.length];
        int c = 0, r = 0; // Here the first element in s2 has been processed.
        int m = 0, n = 0; // The walking indices to compare if two elements are the same.
        for (int i = 1; i<s2.length; i++) {
            if (i>r) {
                p[i] = 0; m = i-1; n = i+1;
            } else {
                int i2 = c*2-i;
                if (p[i2]<(r-i-1)) {
                    p[i] = p[i2];
                    m = -1; // This signals bypassing the while loop below.
                } else {
                    p[i] = r-i;
                    n = r+1; m = i*2-n;
                }
            }
            while (m>=0 && n<s2.length && s2[m]==s2[n]) {
                p[i]++; m--; n++;
            }
            if ((i+p[i])>r) {
                c = i; r = i+p[i];
            }
        }
        int len = 0; c = 0;
        for (int i = 1; i<s2.length; i++) {
            if (len<p[i]) {
                len = p[i]; c = i;
            }
        }
        char[] ss = Arrays.copyOfRange(s2, c-len, c+len+1);
        return String.valueOf(removeBoundaries(ss));
    }

    private static char[] addBoundaries(char[] cs) {
        if (cs==null || cs.length==0)
            return "||".toCharArray();

        char[] cs2 = new char[cs.length*2+1];
        for (int i = 0; i<(cs2.length-1); i = i+2) {
            cs2[i] = '|';
            cs2[i+1] = cs[i/2];
        }
        cs2[cs2.length-1] = '|';
        return cs2;
    }

    private static char[] removeBoundaries(char[] cs) {
        if (cs==null || cs.length<3)
            return "".toCharArray();

        char[] cs2 = new char[(cs.length-1)/2];
        for (int i = 0; i<cs2.length; i++) {
            cs2[i] = cs[i*2+1];
        }
        return cs2;
    }
}
