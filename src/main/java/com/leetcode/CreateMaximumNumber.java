package com.leetcode;


import java.util.Arrays;

public class CreateMaximumNumber {
    private int[] maxSequence(int[] nums, int k) {
        int[] ans = new int[k];
        int n = nums.length;
        for (int i = 0, j = 0; i < n; i++) {
            while (n - i > k - j && j > 0 && ans[j - 1] < nums[i]) {
                j--;
            }

            if (j < k) {
                ans[j++] = nums[i];
            }
        }
        return ans;
    }

    private int[] merge(int[] seq1, int[] seq2, int k) {
        int[] ans = new int[seq1.length + seq2.length];

        for (int i = 0, j = 0, r = 0; r < k; ++r) {
            ans[r] = larger(seq1, i, seq2, j) ? seq1[i++] : seq2[j++];
        }

        return ans;
    }

    private boolean larger(int[] nums1, int i, int[] nums2, int j) {
        while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
            i++;
            j++;
        }
        return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
    }

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        if (nums1 == null || nums2 == null) {
            return null;
        }

        int[] max = null;

        for (int i = 0; i <= k; i++) {
            int j = k - i;
            if (i > nums1.length || j > nums2.length) {
                continue;
            }

            int[] seq1 = maxSequence(nums1, i);
            int[] seq2 = maxSequence(nums2, j);
            int[] ans = merge(seq1, seq2, k);
            if (max == null) {
                max = ans;
            } else if (larger(ans, 0, max, 0)) {
                max = ans;
            }
        }

        return max;
    }

    public static void main(String[] args) {

        int[] nums1 = {6, 7};
        int[] nums2 = {6, 0, 4};
        int k = 5;

        CreateMaximumNumber solution = new CreateMaximumNumber();
        System.out.println(Arrays.toString(solution.maxNumber(nums1, nums2, k)));
    }
}
