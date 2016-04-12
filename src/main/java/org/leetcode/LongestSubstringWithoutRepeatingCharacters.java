package org.leetcode;


public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int i = 0;
        int j = 0;
        char[] array = s.toCharArray();
        int n = array.length;
        int max = 0;
        int hash = 0;
        int count = 0;

        while (j < n) {
            char c = array[j];
            if (hashHas(hash, c)) {
                hash = hashDel(hash, array[i]);
                count--;
                i++;
            } else {
                hash = hashAdd(hash, c);
                count++;
                j++;
            }

            max = Math.max(count, max);
        }

        return max;
    }

    private int hashAdd(int hash, char c) {
        int cIndex = c - 'a';
        return hash | (1 << cIndex);
    }

    private int hashDel(int hash, char c) {
        int cIndex = c - 'a';
        return hash & (~(1 << cIndex));
    }

    private boolean hashHas(int hash, char c) {
        int cIndex = c - 'a';
        return (hash & (1 << cIndex)) != 0 ;
    }

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters solution = new LongestSubstringWithoutRepeatingCharacters();
        String s = "cdd";
        System.out.println(solution.lengthOfLongestSubstring(s));
    }
}
