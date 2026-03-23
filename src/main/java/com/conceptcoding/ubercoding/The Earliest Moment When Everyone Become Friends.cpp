// Time Complexity: O(E log E + E * α(N)) where E is edges, N is people, α is inverse Ackermann
// Space Complexity: O(N) - parent and size arrays
class Solution {
public:
    int findParent(int node, vector<int>& parent){
        if(parent[node]==node){
            return node;
        }
        return parent[node] = findParent(parent[node], parent);
    }
    bool disjoint(int a, int b, vector<int>& parent, vector<int>& size,int n){
        int ultP_a = findParent(a,parent);
        int ultP_b = findParent(b,parent);

        if(ultP_a == ultP_b){
            return false;
        }

        if(size[ultP_a]<size[ultP_b]){
            parent[ultP_a] = ultP_b;
            size[ultP_b]+=size[ultP_a];
            if(size[ultP_b]==n){
                return true;
            }
        }else if(size[ultP_a]>=size[ultP_b]){
            parent[ultP_b] = ultP_a;
            size[ultP_a]+=size[ultP_b];
            if(size[ultP_a]==n){
                return true;
            }
        }
        return false;
    }
    static bool comp(vector<int> log1,vector<int> log2){
        return log1[0]<log2[0];
    }
    int earliestAcq(vector<vector<int>>& logs, int n) {
        sort(logs.begin(),logs.end(),comp);
        vector<int> parent(n);
        vector<int> size(n,1);
        for(int i=0;i<n;i++){
            parent[i]=i;
        }
        int ans = INT_MAX;
        for(int i=0;i<logs.size();i++){
            int timeStamp = logs[i][0];
            int a = logs[i][1];
            int b = logs[i][2];

            if(disjoint(a,b,parent,size,n)){
                ans = timeStamp;
            }
        }
        if(ans==INT_MAX){
            return -1;
        }
        return ans;
    }
};