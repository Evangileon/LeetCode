package com.leetcode;


import java.util.Scanner;

public class Wombats {
    static class tRec {
        long[][] a;
        int n, cnt;
        long[][] f;
        long[] dp;

        public tRec(int n) {
            a = new long[n + 1][n + 1];
            f = new long[800000 + 1][n + 1];
            dp = new long[800000 + 1];
        }
    }

    long[] forSearch;

    int find(tRec g) {
        int l, r, c, i;
        l = 1;
        r = g.cnt;
        while (l < r) {

            c = (l + r) / 2;

            i = 1;
            while ((i <= g.n) && (forSearch[i] == g.f[c][i])) {
                i++;
            }

            if ((i <= g.n) && (g.f[c][i] < forSearch[i])) {
                l = c + 1;
            } else {
                r = c;
            }
        }

        return l;
    }

    void generateStates(tRec g) {
        int i, j;

        g.cnt = 1;
        for (i = 1; i <= g.n; i++) {
            g.f[1][i] = 0;
        }

        while (true) {
            i = g.n;
            while ((i > 1) && (g.f[g.cnt][i] == Math.min(g.f[g.cnt][i - 1], g.n - i + 1))) {
                i--;
            }

            if ((i == 1) && (g.f[g.cnt][i] == g.n)) {
                break;
            }

            g.cnt++;

            for (j = 1; j <= i - 1; j++) {
                g.f[g.cnt][j] = g.f[g.cnt - 1][j];
            }

            g.f[g.cnt][i] = g.f[g.cnt - 1][i] + 1;

            for (j = i + 1; j <= g.n; j++) {
                g.f[g.cnt][j] = 0;
            }
        }

        for (i = 1; i <= g.cnt; i++) {
            g.dp[i] = Integer.MIN_VALUE;
        }
    }

    void processTransition(tRec g, tRec g2) {
        int i, j;

        for (i = 1; i <= g.cnt; i++) {
            for (j = 1; j <= g.n - 1; j++) {
                forSearch[j] = Math.min(g.f[i][j], g.n - j);
            }

            j = find(g2);

            g2.dp[j] = Math.max(g2.dp[j], g.dp[i]);
        }
    }

    void calculateDP(tRec g) {
        int i, j, k;

        for (i = g.cnt; i >= 1; i--) {

            for (j = 1; j <= g.n; j++) {
                forSearch[j] = g.f[i][j];
            }

            if (g.n != 12) {

                for (j = 1; j <= g.n; j++) {
                    if ((g.f[i][j] > 0) && ((j == g.n) || (g.f[i][j + 1] < g.f[i][j]))) {
                        forSearch[j]--;
                        k = find(g);
                        g.dp[k] = Math.max(g.dp[k], g.dp[i]);
                        forSearch[j]++;
                    }
                }
            }

            for (j = 1; j <= g.n; j++) {
                for (k = 1; k <= g.f[i][j]; k++) {
                    g.dp[i] += g.a[j + k - 1][j];
                }
            }
        }
    }

    public static void main(String[] args) {

        Wombats solution = new Wombats();

        tRec[] f = new tRec[2];

        Scanner in = new Scanner(System.in);

        int n;

        n = in.nextInt();

        int[][][] a = new int[1 + n][1 + n][1 + n];
        long ans = 0;

        f[0] = new tRec(n);
        f[1] = new tRec(n);


        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                for (int k = 1; k <= i + 1 - j; k++) {
                    a[i][j][k] = in.nextInt();
                }
            }
        }

        f[n % 2].n = n;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                f[n % 2].a[i][j] = a[i][i + 1 - j][j];
            }
        }

        solution.generateStates(f[n % 2]);
        for (int i = 1; i <= f[n % 2].cnt; i++) {
            f[n % 2].dp[i] = 0;
        }

        solution.calculateDP(f[n % 2]);

        for (int k = n - 1; k >= 1; k--) {
            f[k % 2].n = k;
            for (int i = 1; i <= k; i++) {
                for (int j = 1; j <= i; j++) {
                    f[k % 2].a[i][j] = a[i + (n - k)][i + 1 - j][j];
                }
            }

            f[k % 2].cnt = 0;

            solution.generateStates(f[k % 2]);
            solution.processTransition(f[k % 2], f[(k + 1) % 2]);
            solution.calculateDP(f[k % 2]);
        }

        ans = 0;
        for (int i = 1; i <= f[1].cnt; i++) {
            ans = Math.max(ans, f[1].dp[i]);
        }
    }
}

