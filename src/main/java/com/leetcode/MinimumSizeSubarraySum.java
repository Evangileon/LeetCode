package com.leetcode;


public class MinimumSizeSubarraySum {
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int i = 0;
        int j = 0;
        int minLength = 0;
        int sum = nums[0];

        while (j < nums.length) {
            if (sum >= s) {
                int length = j - i + 1;
                if (minLength == 0) {
                    minLength = length;
                } else {
                    minLength = Math.min(minLength, length);
                }
                if (minLength == 1) {
                    return 1;
                }

                sum -= nums[i++];
            } else {
                if (j < nums.length) {
                    sum += nums[j++];
                }
            }
        }

        return minLength;
    }

    public static void main(String[] args) {
        MinimumSizeSubarraySum solution = new MinimumSizeSubarraySum();
        int[] nums = {1, 2, 3, 4, 5};

        System.out.println(solution.minSubArrayLen(11, nums));
    }
}
