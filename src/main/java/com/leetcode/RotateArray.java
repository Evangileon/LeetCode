package com.leetcode;

import java.util.Arrays;

public class RotateArray {
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    public static void rotate(int nums[], int n, int k) {
        if (nums == null || n == 0) {
            return;
        }

        k = k % n;

        if (k == 0) {
            return;
        }

        int gcdNK = gcd(n, k);
        for (int i = 0; i < gcdNK; i++) {
            int slot = nums[i];
            int temp;

            int j = i;
            while (true) {
                j = (j + k) % n;
                temp = nums[j];
                nums[j] = slot;
                slot = temp;
                if (j == i) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        int nums[] = {1, 2};
        int k = 1;
        System.out.println(Arrays.toString(nums));
        rotate(nums, nums.length, k);
        System.out.println(Arrays.toString(nums));
    }
}
