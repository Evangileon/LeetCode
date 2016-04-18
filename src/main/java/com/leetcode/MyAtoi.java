package com.leetcode;


public class MyAtoi {
    private int handleOverflow(int factor) {
        if (factor < 0) {
            return Integer.MIN_VALUE;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public int myAtoi(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        // handle white space
        int start = 0;
        while (str.charAt(start) == ' ') {
            start++;
        }

        int result = 0;
        int factor = 1;
        // handle plus or minus
        char first = str.charAt(start);
        if (first == '+') {
            start++;
        } else if (first == '-') {
            factor = -1;
            start++;
        }

        // validity
        if (start >= str.length()) {
            return 0;
        }

        // parse
        for (int i = start; i < str.length(); i++) {
            char digit = str.charAt(i);
            if (!(digit - '0' >= 0 && digit - '9' <= 0)) {
                return result * factor;
            }
            int prevResult = result;
            result *= 10;
            if (result < 0 || result / 10 != prevResult) {
                // overflow
                return handleOverflow(factor);
            }
            prevResult = result;
            result += digit - '0';
            if (result < 0 || result - (digit - '0') != prevResult) {
                // overflow
                return handleOverflow(factor);
            }
        }

        return result * factor;
    }

    public static void main(String[] args) {
        MyAtoi solution = new MyAtoi();
        System.out.println(solution.myAtoi("2147483648"));
    }
}
