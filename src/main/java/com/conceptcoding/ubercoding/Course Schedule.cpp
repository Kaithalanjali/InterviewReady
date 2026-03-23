// Time Complexity: O(V + E) where V is number of courses and E is number of prerequisites
// Space Complexity: O(V + E) - adjacency list and visited arrays
class Solution {
public:
    bool dfs(int node, vector<int>& vis, vector<vector<int>>& adj,
             vector<int>& pathVis) {
        vis[node] = 1;
        pathVis[node] = 1;
        int ans = true;
        for (auto it : adj[node]) {
            if (vis[it] == 0) {
                if (dfs(it, vis, adj, pathVis) == false) {
                    ans = false;
                }
            } else if (pathVis[it] == 1) {
                return false;
            }
        }

        pathVis[node] = 0;
        return ans;
    }
    bool canFinish(int numCourses, vector<vector<int>>& prerequisites) {
        vector<vector<int>> adj(numCourses);
        for (int i = 0; i < prerequisites.size(); i++) {
            int first = prerequisites[i][1];
            int second = prerequisites[i][0];
            adj[first].push_back(second);
        }
        vector<int> pathVis(numCourses, 0);
        vector<int> vis(numCourses, 0);
        int ans = true;
        for (int i = 0; i < numCourses; i++) {
            if (vis[i] == 0) {
                if (dfs(i, vis, adj, pathVis)==false) {
                    ans = false;
                }
            }
        }
        return ans;
    }
};