// Time Complexity: O(log n) - binary search approach
// Space Complexity: O(log n) - recursion stack depth
class Solution {
public:
    int search(int low, int high, vector<int>& nums){
        if(low==high){
            return low;
        }
        int mid = (low + high)/2;

        if(nums[mid] > nums[mid+1]){
            return search(low,mid,nums);
        }else{
            return search(mid+1,high,nums);
        }
    }
    int findPeakElement(vector<int>& nums) {
        int n = nums.size() - 1;
        return search(0,n,nums);
    }
};