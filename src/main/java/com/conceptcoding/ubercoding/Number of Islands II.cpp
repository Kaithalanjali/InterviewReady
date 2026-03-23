// Time Complexity: O(k * α(m*n)) where k is positions, m*n is grid size, α is inverse Ackermann
// Space Complexity: O(m * n) - parent and size matrices
class Solution {
public:
    bool isValid(int r, int c, int m, int n, vector<vector<int>>& grid) {
        return (r >= 0 && r < m && c >= 0 && c < n && grid[r][c] == 1);
    }
    pair<int, int> findUltimateParent(pair<int, int> p,
                                      vector<vector<pair<int, int>>>& parent) {
        if (parent[p.first][p.second] == p) {
            return p;
        }
        return parent[p.first][p.second] =
                   findUltimateParent(parent[p.first][p.second], parent);
    }
    void disjoint(pair<int, int> p1, pair<int, int> p2,
                  vector<vector<pair<int, int>>>& parent,
                  vector<vector<int>>& size, vector<int>& ans, int index,
                  int& totalComponents) {
        pair<int, int> up1 = findUltimateParent(p1, parent);
        pair<int, int> up2 = findUltimateParent(p2, parent);

        if (up1 == up2) {
            // ans[index] = totalComponents;
            return;
        } else if (size[up1.first][up1.second] > size[up2.first][up2.second]) {
            size[up1.first][up1.second] += size[up2.first][up2.second];
            parent[up2.first][up2.second] = {up1.first, up1.second};

        } else {
            size[up2.first][up2.second] += size[up1.first][up1.second];
            parent[up1.first][up1.second] = {up2.first, up2.second};
        }
        totalComponents--;
    }
    vector<int> numIslands2(int m, int n, vector<vector<int>>& positions) {
        vector<vector<int>> grid(m, vector<int>(n, 0));

        vector<vector<pair<int, int>>> parent(
            m, vector<pair<int, int>>(n, {-1, -1}));

        vector<int> ans;

        vector<vector<int>> size(m, vector<int>(n, 0));

        int totalComponents = 0;

        for (int i = 0; i < positions.size(); i++) {
            int r = positions[i][0];
            int c = positions[i][1];

            if (grid[r][c] == 1) {
                ans.push_back(totalComponents);
                continue;
            }
            size[r][c] = 1;
            parent[r][c] = {r, c};

            totalComponents++;
            grid[r][c] = 1;

            vector<vector<int>> dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

            for (int k = 0; k < 4; k++) {
                if (isValid(r + dir[k][0], c + dir[k][1], m, n, grid)) {
                    disjoint({r, c}, {r + dir[k][0], c + dir[k][1]}, parent,
                             size, ans, i, totalComponents);
                }
            }
            ans.push_back(totalComponents);
        }
        return ans;
    }
};