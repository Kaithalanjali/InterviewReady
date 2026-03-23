// Time Complexity: O(n log n) - each element added/removed from heaps at most once
// Space Complexity: O(n) - two heaps can store all elements in worst case
class Solution {
public:
    int longestSubarray(vector<int>& nums, int limit) {
        priority_queue<pair<int,int>> maxHeap;
        priority_queue<pair<int,int>, vector<pair<int,int>>, greater<pair<int,int>>> minHeap;
        int left = 0;
        int maxLength = 0;

        for(int right=0;right<nums.size();right++){
            maxHeap.push({nums[right],right});
            minHeap.push({nums[right],right});



            while(maxHeap.top().first - minHeap.top().first > limit){
                left = min(maxHeap.top().second,minHeap.top().second)+1;

                while(maxHeap.top().second<left){
                    maxHeap.pop();
                }

                while(minHeap.top().second<left){
                    minHeap.pop();
                }
            }
            maxLength = max(maxLength,right-left+1);
        }
        return maxLength;
    }
};