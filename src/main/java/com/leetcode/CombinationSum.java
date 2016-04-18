package com.leetcode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null) {
            List<List<Integer>> list = new ArrayList<>();
            return list;
        }

        Arrays.sort(candidates);
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> template = new ArrayList<>();
        for (int i = 0; i < candidates.length; i++) {
            if (i != 0 && candidates[i - 1] == candidates[i]) {
                continue;
            }

            if (candidates[i] > target) {
                break;
            }
            combinationSum(candidates, target, i, list, template, 0);
        }
        return list;
    }

    private void combinationSum(int[] candidates, int target, int i, List<List<Integer>> list, List<Integer> template, int sum) {
        if (i == candidates.length) {
            return;
        }

        for (int j = i; j < candidates.length; j++) {
            if (j != i && candidates[j - 1] == candidates[j]) {
                continue;
            }

            int candidate = candidates[j];
            if (sum + candidate > target) {
                return;
            } else if (sum + candidate == target) {
                List<Integer> newComb = new ArrayList<>(template);
                newComb.add(candidate);
                list.add(newComb);
            } else {
                sum += candidate;
                template.add(candidate);
                combinationSum(candidates, target, j, list, template, sum);
                sum -= candidate;
                template.remove(template.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        CombinationSum solution = new CombinationSum();
        int[] candidates = {1, 2};
        int target = 2;
        System.out.println(Arrays.toString(solution.combinationSum(candidates, target).toArray()));
    }
}
