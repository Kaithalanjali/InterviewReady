// Time Complexity: O(m * n * 4^L) where m,n are board dimensions and L is word length
// Space Complexity: O(L) - recursion stack depth equal to word length
class Solution {
public:
    bool isValid(int i,int j,vector<vector<char>>& board){
        int m =board.size();
        int n =board[0].size();
        return (i>=0 && i<m && j>=0 && j<n);
    }

    bool solve(vector<vector<char>>& board, string word,int index, int i,int j){
        int dx[4]={0,0,1,-1};
        int dy[4]={-1,1,0,0};

        if(isValid(i,j,board) && board[i][j]==word[index]){
            if(index==word.size()-1) return true;
            char temp = board[i][j];
            board[i][j]='-1';
            for(int d=0;d<4;d++){
                if(isValid(i+dx[d],j+dy[d],board)){
                    if(solve(board,word,index+1,i+dx[d],j+dy[d])){
                        return true;
                    }
                }
            }
            board[i][j]=temp;

        }

       return false;
    }
    bool exist(vector<vector<char>>& board, string word) {
        //recursion
        //backtracking - no
        //direction
        bool ans=false;
        for(int i=0;i<board.size();i++){
            for(int j=0;j<board[0].size();j++){
                if(solve(board,word,0,i,j)){
                    return true;
                }
            }
        }
        return false;
    }
};