package org.leetcode;

import java.util.ArrayList;
import java.util.List;

public class UglyNumber2 {
    public int nthUglyNumber(int n) {
        // 1 2 3 4 5 6 8 9 10 12 15 16 18 20 24 25 27 30 32 36 40 45 48 50 54 60
        List<Integer> list = new ArrayList<>();

        list.add(1);

        int min = 1;
        int i = 0, j = 0, k = 0;

        while (n-- > 1) {
            int ui = list.get(i) * 2;
            int uj = list.get(j) * 3;

            int uk = list.get(k) * 5;

            int minTemp = Math.min(ui, uj);
            min = Math.min(minTemp, uk);

            if (ui == min) {
                i++;
            }
            if (uj == min) {
                j++;
            }
            if (uk == min) {
                k++;
            }

            list.add(min);
        }

        return list.get(list.size() - 1);
    }

    public static void main(String[] args) {
        UglyNumber2 solution = new UglyNumber2();

        System.out.println(solution.nthUglyNumber(7));
    }
}
