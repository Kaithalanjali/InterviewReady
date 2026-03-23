// Time Complexity: O(m * n) where m and n are grid dimensions - visits each cell once
// Space Complexity: O(m * n) - visited array and recursion stack in worst case
class Solution {
public:
    void dfs(int i, int j, vector<vector<char>>& grid, vector<vector<int>>& vis,
             int m, int n) {
        vis[i][j] = 1;
        int dx[] = {0, 1, 0, -1};
        int dy[] = {1, 0, -1, 0};

        for (int k = 0; k < 4; k++) {
            int newI = i + dx[k];
            int newJ = j + dy[k];
            if (newI >= 0 && newJ >= 0 && newI < m && newJ < n) {
                if (vis[i + dx[k]][j + dy[k]] == 0 &&
                    grid[newI][newJ] =='1') {
                    dfs(i + dx[k], j + dy[k], grid, vis, m, n);
                }
            }
        }
    }
int numIslands(vector<vector<char>>& grid) {
    int ans = 0;
    int m = grid.size();
    int n = grid[0].size();
    vector<vector<int>> vis(m, vector<int>(n, 0));
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (vis[i][j] == 0 && grid[i][j] == '1') {
                ans++;
                dfs(i, j, grid, vis, m, n);
            }
        }
    }
    return ans;
}
}
;