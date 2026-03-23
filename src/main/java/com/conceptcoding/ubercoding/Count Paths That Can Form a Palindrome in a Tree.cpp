// Time Complexity: O(n * 26) where n is the number of nodes
// Space Complexity: O(n) - for the adjacency list and hashmap
class Solution {
public:
    long long dfs(int index, int mask, unordered_map<int,int>& have, vector<vector<int>>& adj, string& s){
        long long r = 0;
        if(index!=0){
            mask^=1<<(s[index]-'a');

            for(int i=1<<25;i>0;i>>=1){
                if(have.count(mask^i)){
                    r+=have[mask^i];
                }
            }
            r+=have[mask];
            have[mask]++;
        }
        for(auto it:adj[index]){
            r+=dfs(it,mask,have,adj,s);
        }
        return r;
    }
    long long countPalindromePaths(vector<int>& parent, string s) {
        unordered_map<int,int> have;
        have[0]=1;
        int n = parent.size();
        vector<vector<int>> adj(n);
        for(int i=1;i<s.size();i++){
            adj[parent[i]].push_back(i);
        }
        return dfs(0,0,have,adj,s);
    }
};