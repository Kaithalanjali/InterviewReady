/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode() : val(0), left(nullptr), right(nullptr) {}
 *     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
 *     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
 * };
 */
// Time Complexity: O(n log n) where n is number of nodes - traverse all nodes and sort
// Space Complexity: O(n) - map stores all nodes
class Solution {
public:
    void solve(TreeNode* root, map<int,map<int,vector<int>>>& mp, int row, int col){
        if(root==NULL){
            return;
        }
        mp[col][row].push_back(root->val);
        if(root->left!=NULL){
            solve(root->left,mp,row+1,col-1);
        }
        if(root->right!=NULL){
            solve(root->right,mp,row+1,col+1);
        }
    }
    vector<vector<int>> verticalTraversal(TreeNode* root) {
        map<int,map<int,vector<int>>> mp;
        solve(root,mp,0,0);
        vector<vector<int>> ans;
        for(auto it:mp){
           vector<int> tempvec;
            for(auto it2:it.second){
                //it2.first level it2.second vector of num
                vector<int> t= it2.second;
                sort(t.begin(),t.end());
                tempvec.insert(tempvec.end(),t.begin(),t.end());
            }
            ans.push_back(tempvec);
        }
        return ans;
    }
};