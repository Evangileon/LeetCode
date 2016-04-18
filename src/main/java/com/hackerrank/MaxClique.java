package com.hackerrank;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MaxClique {
    private int atMostKr1Free(int n, int r1) {
        int r = r1 - 1;
        if (r == 0) {
            return 0;
        }
        return (r - 1) * n * n / (2 * r);
    }

    int maxClique(int n, int m) {
        if (n < 2) {
            return n;
        }

        int l = 2;
        int upper = Math.min(n, m);
        int r = upper;
        int mid;

        while (l <= r) {
            mid = l + (r - l) / 2;

            if (atMostKr1Free(n, mid) > m) {
                r = mid - 1;
            } else if (mid < upper && atMostKr1Free(n, mid + 1) <= m) {
                l = mid + 1;
            } else {
                return mid;
            }
        }

        return l;
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        MaxClique solution = new MaxClique();
        try {
            line = reader.readLine();
            int numTests = Integer.parseInt(line);

            for (int i = 0; i < numTests; i++) {
                line = reader.readLine();
                String[] strs = line.split(" ");
                int n = Integer.parseInt(strs[0]);
                int m = Integer.parseInt(strs[1]);
                System.out.println(solution.maxClique(n, m));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
