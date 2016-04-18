package com.leetcode;

public class ExcelSheetColumnTitle {

    public static String convertToTitle(int n) {
        String result = "";

        if (n == 1) {
            return "A";
        }

        while (n > 1) {
            int digit = n % 26;
            if (digit == 0) {
                digit += 26;
            }
            char sub = (char)('A' + digit - 1);
            result = sub + result;
            n /= 26;
        }

        return result;
    }

    public static void main(String[] args) {
        int[] tests = {1, 2, 3, 4, 234, 26, 27, 10};

        for (int test : tests) {
            System.out.println(test + " -> " + convertToTitle(test));
        }
    }
}
