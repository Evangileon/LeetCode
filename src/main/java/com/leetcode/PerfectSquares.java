package com.leetcode;


public class PerfectSquares {
    public int numSquares(int n) {
        if (n <= 0) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 1; i * i <= n; i++) {
            int j = i * i;
            int subNum = numSquares(n - j);
            min = Math.min(subNum, min);
        }

        if (min == Integer.MAX_VALUE) {
            return 0;
        }
        return min + 1;
    }

    public static void main(String[] args) {
        PerfectSquares solution = new PerfectSquares();
        System.out.println(solution.numSquares(53));
    }
}
