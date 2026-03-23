// Time Complexity: O(n) where n is the number of nodes - two DFS traversals
// Space Complexity: O(n) - adjacency list and recursion stack
class Solution {
public:
    void dfs2(int u, int p, vector<vector<pair<int, int>>>& graph,
              vector<int>& ans) {
        for (auto it : graph[u]) {
            int v = it.first;
            int w = it.second;
            if (v != p) {
                ans[v] = ans[u] + (w == 0 ? 1 : -1);
                dfs2(v, u, graph, ans);
            }
        }
    }
    int dfs1(int u, int p, vector<vector<pair<int, int>>>& graph) {
        int cost = 0;
        for (auto it : graph[u]) {
            int v = it.first;
            int w = it.second;

            if (v != p) {
                cost += w + dfs1(v, u, graph);
            }
        }
        return cost;
    }
    vector<int> minEdgeReversals(int n, vector<vector<int>>& edges) {
        // constructing graphs
        vector<vector<pair<int, int>>> graph(n);
        for (auto it : edges) {
            int u = it[0];
            int v = it[1];
            graph[u].push_back({v, 0});
            graph[v].push_back({u, 1});
        }
        vector<int> ans(n);
        ans[0] = dfs1(0, -1, graph);
        dfs2(0, -1, graph, ans);
        return ans;
    }
};