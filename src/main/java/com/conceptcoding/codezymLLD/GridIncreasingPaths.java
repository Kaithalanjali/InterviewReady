package com.conceptcoding.codezymLLD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GridIncreasingPaths {

    private int[][] grid;
    private int rows;
    private int cols;

    private final int[][] DIRS = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
    };

    public GridIncreasingPaths() {

    }

    //memo for lexicographically smallest path
    private String[][] bestPathMemo;
    private int[][] dp;

    public int getLongestPathLength(List<String> gridLines) {
        buildGrid(gridLines);

        int ans = 0;
        dp = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                ans = Math.max(ans, dfsLen(r, c));
            }
        }
        return ans;
    }

    private void buildGrid(List<String> gridLines) {
        rows = gridLines.size();
        // Split first line by comma to get actual column count
        String[] firstRow = gridLines.get(0).split(",");
        cols = firstRow.length;

        grid = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            String[] values = gridLines.get(r).split(",");
            for (int c = 0; c < cols; c++) {
                grid[r][c] = Integer.parseInt(values[c].trim());
            }
        }
    }

    private int dfsLen(int r, int c) {
        if (dp[r][c] != 0) {
            return dp[r][c];
        }

        int best = 1;

        for (int[] d : DIRS) {

            int nr = r + d[0];
            int nc = c + d[1];

            if (!isValid(nr, nc)) {
                continue;
            }

            if (grid[nr][nc] > grid[r][c]) {
                best = Math.max(best, 1 + dfsLen(nr, nc));
            }
        }
        dp[r][c] = best;
        return best;
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public String getOneLongestPath(List<String> gridLines) {
        buildGrid(gridLines);

        dp = new int[rows][cols];
        bestPathMemo = new String[rows][cols];

        int maxLen = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                maxLen = Math.max(maxLen, dfsLen(r, c));
            }
        }

        String answer = null;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (dfsLen(r, c) == maxLen) {
                    String candidate = dfsBestPath(r, c);

                    if (answer == null || candidate.compareTo(answer) < 0) {
                        answer = candidate;
                    }
                }
            }
        }
        return answer;
    }

    private String dfsBestPath(int r, int c) {
        if (bestPathMemo[r][c] != null) {
            return bestPathMemo[r][c];
        }

        String currentCell = cellString(r, c);

        int bestLen = dfsLen(r, c);

        if (bestLen == 1) {
            bestPathMemo[r][c] = currentCell;
            return currentCell;
        }

        String bestPath = null;

        for (int[] d : DIRS) {
            int nr = r + d[0];
            int nc = c + d[1];

            if (!isValid(nr, nc)) {
                continue;
            }

            if (grid[nr][nc] > grid[r][c] && dfsLen(nr, nc) == bestLen - 1) {

                String nextPath = dfsBestPath(nr, nc);
                String candidate = currentCell + " -> " + nextPath;

                if (bestPath == null || candidate.compareTo(bestPath) < 0) {
                    bestPath = candidate;
                }
            }
        }

        bestPathMemo[r][c] = bestPath;

        return bestPath;
    }

    private String cellString(int r, int c) {
        return "(" + r + "," + c + ")" + "=" + grid[r][c];
    }

    public List<String> getAllStrictlyIncreasingPaths(List<String> gridLines) {
        buildGrid(gridLines);

        List<String> result = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                List<String> path = new ArrayList<>();

                backtrack(r, c, path, result);
            }
        }
        Collections.sort(result);

        return result;
    }

    void backtrack(int r, int c, List<String> path, List<String> result) {
        path.add(cellString(r, c));

        //cool thing - String.join can be used to join list of strings with a delimiter
        result.add(String.join(" -> ", path));


        for (int[] d : DIRS) {
            int nr = r + d[0];
            int nc = c + d[1];

            if (!isValid(nr, nc)) {
                continue;
            }

            if (grid[nr][nc] > grid[r][c]) {
                backtrack(nr, nc, path, result);
            }
        }
        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {

        GridIncreasingPaths obj =
                new GridIncreasingPaths();

        List<String> grid =
                Arrays.asList(
                        "8,8,3",
                        "5,5,7",
                        "2,1,0"
                );

        System.out.println(
                obj.getLongestPathLength(grid));

        System.out.println(
                obj.getOneLongestPath(grid));

        System.out.println(
                obj.getAllStrictlyIncreasingPaths(grid));
    }

}
