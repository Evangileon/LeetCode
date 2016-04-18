package com.leetcode;


public class RangeSumQuery {
    private int[] nums;
    private long[] tree;
    private int maxIndex;
    private boolean initialized = false;

    public RangeSumQuery(int[] nums) {
        if (nums == null) {
            return;
        }

        this.nums = nums;
        tree = new long[nums.length + 1];
        maxIndex = nums.length;

        for (int i = 0; i < nums.length; i++) {
            update(i, nums[i]);
        }
        initialized = true;
    }

    public void update(int i, int val) {
        if (i < 0) {
            return;
        }

        int diff;
        if (!initialized) {
            diff = val;
        } else {
            diff = val - nums[i];
            nums[i] = val;
        }

        i++;
        while (i <= maxIndex) {
            tree[i] += diff;
            i += (i & -i);
        }
    }

    public int sumRange(int i, int j) {
        if (i < 0 || j < 0 || i > j) {
            return 0;
        }

        j++;
        long sumI = 0;
        long sumJ = 0;
        while (i != j) {
            if (i > j) {
                sumI += tree[i];
                i -= (i & -i);
            } else {
                sumJ += tree[j];
                j -= (j & -j);
            }
        }

        return (int)(sumJ - sumI);
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 5};
        RangeSumQuery solution = new RangeSumQuery(nums);

        System.out.println(solution.sumRange(0, 2));
        solution.update(1, 2);
        System.out.println(solution.sumRange(0, 2));
    }
}
