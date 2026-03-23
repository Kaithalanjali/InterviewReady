// Time Complexity: O(V + E) where V is numCourses and E is number of prerequisites
// Space Complexity: O(V + E) - adjacency list, indegree array, queue, and visited array
class Solution {
public:
    vector<int> findOrder(int numCourses, vector<vector<int>>& prerequisites) {
        vector<vector<int>> adj(numCourses);
        vector<int> indegree(numCourses,0);
        for(int i=0;i<prerequisites.size();i++){
            //[0,1]  === > 1->0
            int first = prerequisites[i][0];
            int second = prerequisites[i][1]; //prerequisites

            //1-->0
            adj[second].push_back(first);
            indegree[first]++;
        }
        queue<int> q;
        vector<int> ans;
        vector<int> vis(numCourses,-1);
        for(int i=0;i<numCourses;i++){
           if(indegree[i]==0){
            q.push(i);
           }
        }
        while(!q.empty()){
            int node = q.front();
            q.pop();
            vis[node]=1;
            ans.push_back(node);
            for(auto it:adj[node]){

                indegree[it]--;
                if(indegree[it]==0){
                    q.push(it);
                }
            }
        }
        for(int i=0;i<numCourses;i++){
            if(vis[i]==-1){
                return {};
            }
        }
        return ans;
    }
};