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
// Time Complexity: O(n) - visits all nodes in inorder traversal
// Space Complexity: O(n) - stores all nodes in vector and recursion stack
class Solution {
public:
    void inorderTraversal(TreeNode* root, vector<int>& ans){
        if(root==NULL){
            return;
        }
        if(root->left!=NULL){
            inorderTraversal(root->left,ans);
        }
        ans.push_back(root->val);
        if(root->right!=NULL){
            inorderTraversal(root->right,ans);
        }
    }
    int kthSmallest(TreeNode* root, int k) {
        vector<int> ans;
        inorderTraversal(root,ans);
        return ans[k-1];
    }
};