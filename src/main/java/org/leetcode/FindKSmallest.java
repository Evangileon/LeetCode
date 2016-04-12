package org.leetcode;


public class FindKSmallest {

    // return the index of pivot after processing, because the index of pivot will change
    private int partition(int[] nums, int left, int right, int pivot) {
        int pivotV = nums[pivot];
        nums[pivot] = nums[right];
        nums[right] = pivotV;

        int i = left;
        for (int j = left; j < right; j++) {
            if (nums[j] <= pivotV) {
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
                i++;
            }
        }
        nums[right] = nums[i];
        nums[i] = pivotV;

        return i;
    }

    private int medianOfFive(int[] nums, int left, int right) {
        if (left > right) {
            return Integer.MIN_VALUE;
        }

        int largest = Integer.MIN_VALUE;
        int secondLarge = Integer.MIN_VALUE;
        int median = Integer.MIN_VALUE;

        for (int i = left; i <= right; i++) {
            int num = nums[i];
            if (largest < num) {
                largest = num;
            }
        }
        return 0;
    }

    private int betterSelectPivot(int[] nums, int left, int right) {
        int length = right - left + 1;
        int numGroups = length / 5;
        if (length > numGroups * 5) {
            numGroups++; // ceiling
        }

        int[] medians = new int[numGroups];
        for (int i = 0; i < numGroups; i++) {
            int tmpRight = (i + 1) * 5 - 1;
            medians[i] = medianOfFive(nums, left + i * 5, tmpRight > right ? right : tmpRight);
        }

        return findMedian(medians);
    }

    private int findKth(int[] nums, int left, int right, int k) {
        if (left > right) {
            return -1;
        }

        int pivot = betterSelectPivot(nums, left, right);
        pivot = partition(nums, left, right, pivot);
        int L = pivot - left + 1;

        if (k == L) {
            return pivot;
        } else if (k > L) {
            return findKth(nums, pivot + 1, right, k - L);
        } else {
            return findKth(nums, left, pivot, k);
        }
    }

    private int findMedian(int[] nums) {
        return findKth(nums, 0, nums.length - 1, (nums.length + 1) / 2);
    }

    public static void main(String[] args) {
        int[][] testCases = new int[][]{
                {1, 2, 3, 4, 5},
                {1, 2, 3, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 7},
                {5, 4, 3, 2, 1},
                {7, 2, 5, 3, 8, 1},
                {},
                {0}
        };

        FindKSmallest solution = new FindKSmallest();

        for (int[] testCase : testCases) {
            int medianIndex = solution.findMedian(testCase);
            if (medianIndex < 0) {
                System.out.println("No element");
            } else {
                System.out.println(testCase[medianIndex]);
            }
        }
    }
}
