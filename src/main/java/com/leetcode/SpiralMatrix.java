package com.leetcode;


import java.util.ArrayList;
import java.util.List;

public class SpiralMatrix {
    public List<Integer> spiralOrder(int[][] matrix) {

        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new ArrayList<>();
        }

        List<Integer> list = new ArrayList<>(matrix.length * matrix[0].length);
        spiralOrder(matrix, 0, 0, matrix.length - 1, matrix[0].length - 1, list);
        return list;
    }

    private void spiralOrder(int[][] matrix, int k, int l, int m, int n, List<Integer> list) {

        if ((k > m) || (l > n)) {
            return;
        }

        // top
        for (int j = l; j <= n; j++) {
            list.add(matrix[k][j]);
        }
        k++;
        // right
        for (int i = k; i <= m; i++) {
            list.add(matrix[i][n]);
        }
        n--;
        // bottom
        if (k <= m) {
            for (int j = n; j >= l; j--) {
                list.add(matrix[m][j]);
            }
            m--;
        }
        // left
        if (l <= n) {
            for (int i = m; i >= k; i--) {
                list.add(matrix[i][l]);
            }
            l++;
        }

        spiralOrder(matrix, k, l, m, n, list);
    }

    public static void main(String[] args) {
        int[][] matrix = {{2, 5, 8}, {4, 0, -1}};
        SpiralMatrix solution = new SpiralMatrix();
        System.out.println(solution.spiralOrder(matrix));
    }
}
