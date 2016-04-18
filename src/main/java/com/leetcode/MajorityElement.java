package com.leetcode;


import java.util.ArrayList;
import java.util.List;

public class MajorityElement {
    public List<Integer> majorityElement(int[] nums) {
        if (nums == null || nums.length < 1) {
            return new ArrayList<Integer>();
        }

        int major1 = 0;
        int major2 = 0;
        int count1 = 0;
        int count2 = 0;

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (major1 == num) {
                count1++;
            } else if (major2 == num) {
                count2++;
            } else if (count1 == 0) {
                major1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                major2 = num;
                count2 = 1;
            } else  {
                count1--;
                count2--;
            }
        }

        List<Integer> list = new ArrayList<>();
        if (count1 > nums.length / 3) {
            list.add(major1);
        }
        if (count2 > nums.length / 3) {
            list.add(major2);
        }
        return list;
    }

    public static void main(String[] args) {
        MajorityElement solution = new MajorityElement();
        int[] nums = {2, 2, 1, 3};
        System.out.println(solution.majorityElement(nums).toString());
    }
}
