package com.leetcode;


public class WordSearch {
    public boolean exist(char[][] board, String word) {
        if (board == null || word == null || board.length == 0) {
            return false;
        }

        int m = board.length;
        int n = board[0].length;
        int[][] mask = new int[m][];
        for (int i = 0; i < m; i++) {
            mask[i] = new int[n];
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (exist(board, mask, i, j, word, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean exist(char[][] board, int[][] mask, int i, int j, String word, int k) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
            return false;
        }

        // visit k;
        if (word.charAt(k) != board[i][j] || mask[i][j] == 1) {
            return false;
        }

        if (k == word.length() - 1) {
            return true;
        }

        // hit k
        mask[i][j] = 1;
        if (exist(board, mask, i - 1, j, word, k + 1)) return true;
        if (exist(board, mask, i + 1, j, word, k + 1)) return true;
        if (exist(board, mask, i, j - 1, word, k + 1)) return true;
        if (exist(board, mask, i, j + 1, word, k + 1)) return true;
        mask[i][j] = 0;
        return false;
    }

    public static void main(String[] args) {
        WordSearch solution = new WordSearch();
        char[][] board = {{'A'}};
        String word = "A";

        System.out.println(solution.exist(board, word));
    }
}
