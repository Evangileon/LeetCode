package org.leetcode;


import java.util.ArrayList;
import java.util.List;

public class CountSmallerNumbersAfterSelf {
    private int merge(int[] nums, List<Integer> list, int[][] inverted, int left, int mid, int right) {
        int base = 0;
        int i = left;
        int j = mid + 1;

        for (int k = left; k <= right; k++) {
            if (i <= mid && (j > right || nums[inverted[0][i]] <= nums[inverted[0][j]])) {
                inverted[1][k] = inverted[0][i++];
                list.set(inverted[1][k], list.get(inverted[1][k]) + base);
            } else {
                inverted[1][k] = inverted[0][j++];
                base++;
            }
        }

        for (i = left; i <= right; i++) {
            inverted[0][i] = inverted[1][i];
        }

        return 0;
    }

    private int countSmaller(int[] nums, List<Integer> list, int[][] inverted, int left, int right) {
        if (left >= right) {
            return 0;
        }

        int mid = left + (right - left) / 2;
        countSmaller(nums, list, inverted, left, mid);
        countSmaller(nums, list, inverted, mid + 1, right);

        merge(nums, list, inverted, left, mid, right);
        return 0;
    }

    public List<Integer> countSmaller(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>();
        int[][] inverted = new int[2][nums.length];
        for (int i = 0; i < nums.length; i++) {
            list.add(0);
            inverted[0][i] = i;
        }

        countSmaller(nums, list, inverted, 0, nums.length - 1);
        return list;
    }

    public static void main(String[] args) {
        CountSmallerNumbersAfterSelf solution = new CountSmallerNumbersAfterSelf();
        int[] nums = {5, 2, 6, 1};
        System.out.println(solution.countSmaller(nums));
    }
}
