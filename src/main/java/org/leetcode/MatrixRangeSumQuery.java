package org.leetcode;


public class MatrixRangeSumQuery {
    private int[][] matrix;
    private long[][] tree;
    private int maxI;
    private int maxJ;
    private boolean initialized = false;

    public MatrixRangeSumQuery(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }

        this.matrix = matrix;
        tree = new long[matrix.length + 1][matrix[0].length + 1];
        maxI = matrix.length;
        maxJ = matrix[0].length;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                update(i, j, matrix[i][j]);
            }
        }
        initialized = true;
    }

    public void update(int i, int j, int val) {
        if (i < 0 || j < 0) {
            return;
        }

        int diff;
        if (!initialized) {
            diff = val;
        } else {
            diff = val - matrix[i][j];
            if (diff == 0) {
                return;
            }
            matrix[i][j] = val;
        }
        i++;
        j++;

        int j1;
        while (i <= maxI) {
            j1 = j;
            while (j1 <= maxJ) {
                tree[i][j1] += diff;
                j1 += (j1 & -j1);
            }
            i += (i & -i);
        }
    }

    // 1-indexed
    private long read(int x, int y) { // return sum from 1,1 to x,y.
        int sum = 0;
        while (x > 0) {
            int y1 = y;
            while (y1 > 0) {
                sum += tree[x][y1];
                y1 -= y1 & -y1;
            }
            x -= x & -x;
        }
        return sum;
    }

    // 0-indexed
    public long rangeSum(int r1, int c1, int r2, int c2) {
        return read(r2 + 1, c2 + 1) - read(r2 + 1, c1) - read(r1, c2 + 1) + read(r1, c1);
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}
        };

        MatrixRangeSumQuery solution = new MatrixRangeSumQuery(matrix);

        int[][] testCases = new int[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4}
        };

        for (int[] testCase : testCases) {
            System.out.println(String.format("Output for (%d, %d) - (%d, %d)",
                    testCase[0], testCase[1], testCase[2], testCase[3]));
            System.out.println(solution.rangeSum(testCase[0], testCase[1], testCase[2], testCase[3]));
            solution.update(2, 3, 2);
        }
    }
}
