package com.leetcode;


public class ReverseBits {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int ret = 0;
        while (n != 0) {
            int bit = n & 1;
            ret = ret << 1;
            ret |= bit;
            n = n >>> 1;
        }

        return ret;
    }

    public static void main(String[] args) {

    }
}
