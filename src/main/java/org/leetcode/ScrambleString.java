package org.leetcode;


public class ScrambleString {
    public boolean isScramble(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        int len = s1.length();
        boolean st[][][] = new boolean[s1.length()][s2.length()][len + 1];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                st[i][j][0] = true;
            }
        }

        for (int k = 1; k <= len; k++) {
            for (int i = 0; i <= len - k; i++) {
                for (int j = 0; j <= len - k; j++) {
                    if (k == 1) {
                        st[i][j][1] = s1.charAt(i) == s2.charAt(j);
                        continue;
                    }

                    int h = k / 2;
                    st[i][j][k] = (st[i][j][h] && st[i + h][j + h][h])
                            || (st[i][j + h][h] && st[i + h][j][h]);
                }
            }
        }

        return st[0][0][len];
    }

    public static void main(String[] args) {
        ScrambleString solution = new ScrambleString();
        String s1 = "abb";
        String s2 = "bba";

        System.out.println(String.format("%s, %s: %b", s1, s2, solution.isScramble(s1, s2)));
    }
}
