// Time Complexity: O(n^2) where n is the length of string - expand around center for each position
// Space Complexity: O(1) - only constant extra space used for variables
class Solution {
public:
    void palindromeSubstr(int l, int r,string s,int n,int& start,int& end,int& maxLength){
        while(l>=0 && r<n){
            if(s[l]==s[r]){
                l--;
                r++;
            }else{

                break;
            }
        }
        int len = r-l+1-2;
                if(len>maxLength){
                    start=l+1;
                    end=r-1;
                    maxLength = r-l+1-2;
                }
    }
    string longestPalindrome(string s) {
        int maxLength = 1;
        int start = 0;
        int end = 0;
        int n = s.size();
        for(int i=0;i<n-1;i++){
            int l = i;
            int r = i;
            palindromeSubstr(l,r,s,n,start,end,maxLength);
        }
        for(int i=0;i<n-1;i++){
            int l = i;
            int r = i+1;
            palindromeSubstr(l,r,s,n,start,end,maxLength);
        }
        return s.substr(start,maxLength);
    }
};