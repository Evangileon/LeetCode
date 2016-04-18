package com.leetcode;


import java.util.HashMap;

public class MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        if (s == null || s == null) {
            return "";
        }

        HashMap<Character, Integer> map = new HashMap<>();
        for (char c: t.toCharArray()) {
            map.put(c, 0);
        }

        int flag = 0;
        char[] sa = s.toCharArray();

        int start = 0;
        int end = 0;

        for (int i = 0; i < sa.length; i++) {
            char c = sa[i];
            if (!map.containsKey(c)) {
                continue;
            }

            if (map.get(c) == 0) {
                map.put(c, 1);
                flag++;
            } else {
                map.put(c, map.get(c) + 1);
            }

            if (flag == map.size()) {
                end = i;
                // try to move start to right
                while (start <= end) {
                    if (!map.containsKey(sa[start])) {
                        start++;
                    } else if (map.get(sa[start]) > 1) {
                        map.put(sa[start], map.get(sa[start]) - 1);
                        start++;
                    } else if (map.get(sa[start]) == 1) {
                        break;
                    }
                }
            }
        }

        if ((end - start + 1) >= map.size()) {
            return s.substring(start, end + 1);
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        MinimumWindowSubstring solution = new MinimumWindowSubstring();
        String window = "aafsdgfdghdfabc";
        String test = "abc";
        System.out.println(solution.minWindow(window, test));
    }
}
