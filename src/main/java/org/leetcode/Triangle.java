package org.leetcode;


import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Triangle {
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle.size() == 0) {
            return 0;
        }

        int[] buf1 = new int[triangle.size() + 1];
        int[] buf2 = new int[triangle.size() + 1];
        int[] bufOrig;
        int[] bufDest;
        for (int i = 0; i < buf1.length; i++) {
            buf1[i] = 0;
            buf2[i] = 0;
        }
        // Generate an iterator. Start just after the last element.
        ListIterator<List<Integer>> rowItor = triangle.listIterator(triangle.size());

        boolean odd = false;
        // Iterate in reverse.
        while(rowItor.hasPrevious()) {
            List<Integer> row = rowItor.previous();
            int index = 0;
            for (int e: row) {
                if (odd) {
                    bufOrig = buf2;
                    bufDest = buf1;
                } else {
                    bufOrig = buf1;
                    bufDest = buf2;
                }
                int localMin =Math.min(bufOrig[index], bufOrig[index + 1]) + e;
                bufDest[index] = localMin;
                index++;
            }
            odd = !odd;
        }

        if (odd) {
            return buf2[0];
        } else {
            return buf1[0];
        }
    }

    public static void main(String[] args) {
        Triangle solution = new Triangle();

        List<List<Integer>> triangle = new LinkedList<>();
        List<Integer> row = new LinkedList<>();
        row.add(-10);
        triangle.add(row);

        System.out.println(solution.minimumTotal(triangle));
    }
}
