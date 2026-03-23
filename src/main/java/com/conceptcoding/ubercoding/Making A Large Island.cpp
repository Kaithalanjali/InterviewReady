// Time Complexity: O(n^2 * α(n^2)) where n is grid size, α is inverse Ackermann
// Space Complexity: O(n^2) - disjoint set arrays
class DisjointSet {
public:
    vector<int> parent;
    vector<int> islandSize;

    DisjointSet(int n) {
        parent.resize(n);
        islandSize.resize(n);

        for (int node = 0; node < n; node++) {
            parent[node] = node;
            islandSize[node] = 1;
        }
    }

    int findRoot(int node) {
        if (parent[node] == node) {
            return node;
        }
        return parent[node] = findRoot(parent[node]);
    }

    void unionNodes(int nodeA, int nodeB) {
        int rootA = findRoot(nodeA);
        int rootB = findRoot(nodeB);

        if (rootA == rootB) {
            return;
        }

        if (islandSize[rootA] < islandSize[rootB]) {
            parent[rootA] = rootB;
            islandSize[rootB] += islandSize[rootA];
        } else {
            parent[rootB] = rootA;
            islandSize[rootA] += islandSize[rootB];
        }
    }
};
class Solution {
public:
    bool isValid(int i, int j, vector<vector<int>>& grid, int n) {
        return (i >= 0 && j >= 0 && j < n && i < n && grid[i][j] == 1);
    }
    int largestIsland(vector<vector<int>>& grid) {
        int n = grid.size();

        DisjointSet ds(n * n);

        vector<int> dx = {1, -1, 0, 0};
        vector<int> dy = {0, 0, -1, 1};

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    int curNode = n * r + c;
                    for (int k = 0; k < 4; k++) {
                        if (isValid(r + dx[k], c + dy[k], grid, n)) {
                            int neighborNode = n * (r + dx[k]) + (c + dy[k]);
                            ds.unionNodes(curNode, neighborNode);
                        }
                    }
                }
            }
        }

        int maxIslandSize = 0;
        bool hasZero = false;
        unordered_set<int> uniqueRoots;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 0) {
                    hasZero = true;
                    int currentIslandSize = 1;

                    for (int k = 0; k < 4; k++) {
                        if (isValid(r + dx[k], c + dy[k], grid, n)) {
                            int neighborNode = n * (r + dx[k]) + (c + dy[k]);
                            int root = ds.findRoot(neighborNode);
                            uniqueRoots.insert(root);
                        }
                    }

                    for (int root : uniqueRoots) {
                        currentIslandSize += ds.islandSize[root];
                    }

                    uniqueRoots.clear();
                    maxIslandSize = max(maxIslandSize, currentIslandSize);
                }
            }
        }
        if (!hasZero) {
            return n * n;
        }
        return maxIslandSize;
    }
};