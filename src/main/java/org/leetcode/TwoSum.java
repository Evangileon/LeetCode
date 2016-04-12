package org.leetcode;


import java.util.Arrays;
import java.util.HashMap;

public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        if (nums == null) {
            return null;
        }
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int remain = target - num;
            if (map.containsKey(num)) {
                return new int[]{map.get(num), i + 1};
            }

            map.put(remain, i + 1);
        }

        return new int[]{1, 1};
    }

    public static void main(String[] args) {
        TwoSum solution = new TwoSum();
        int[] nums = {3, 2, 4};
        int target = 6;
        System.out.println(Arrays.toString(solution.twoSum(nums, target)));
    }
}
