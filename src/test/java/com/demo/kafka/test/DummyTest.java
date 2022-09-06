package com.demo.kafka.test;

import java.util.Arrays;

public class DummyTest {

    public static void main(String[] args) {
        char[][] board = new char[][]{
                new char[]{'A', 'B', 'C', 'E'},
                new char[]{'S', 'F', 'C', 'S'},
                new char[]{'A', 'D', 'E', 'E'}
        };

        String word = "ABCCED";
//        String word = "SEE";
//        String word = "ABCB";

//        boolean b = DummyTest.exist(board, word);
//        System.out.println("res: " + b);

        int[] nums = new int[]{1,2,3,4};
        int[] p = productExceptSelf(nums);
//        System.out.println(Arrays.toString(p));

        System.out.println(multiply(4, 5));
    }

    public static int multiply(int n, int m) {
        int res = 0;
        int b = m;

        while (b > 0) {
            res += n;
            b--;
        }

        return res;
    }

    public static int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];

        int prefix = 1;
        for (int i = 0; i < nums.length; i++) {
            res[i] = prefix;
            prefix = prefix * nums[i];
        }

        int postfix = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] = res[i] * postfix;
            postfix = postfix * nums[i];
        }

        return res;
    }

    public static boolean exist(char[][] board, String word) {
        int cols = board[0].length;
        int rows = board.length;
        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (dfs(i, j, 0, board, visited, word)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean dfs(int i, int j, int index, char[][] board, boolean[][] visited, String word) {
        int cols = board[0].length;
        int rows = board.length;

        if (index == word.length()) return true;

        if (i < 0 || j < 0 || i >= rows || j >= cols) return false;
        if (visited[i][j]) return false;
        if (word.charAt(index) != board[i][j]) return false;

        visited[i][j] = true;

        boolean top = dfs(i - 1, j, index + 1, board, visited, word);
        boolean down = dfs(i + 1, j, index + 1, board, visited, word);
        boolean left = dfs(i, j - 1, index + 1, board, visited, word);
        boolean right = dfs(i, j + 1, index + 1, board, visited, word);
        boolean res = top || down || left || right;

        visited[i][j] = false;
        return res;
    }
}
