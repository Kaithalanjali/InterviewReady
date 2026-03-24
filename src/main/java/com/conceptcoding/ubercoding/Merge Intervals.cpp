// Time Complexity: O(n log n) where n is the number of intervals - dominated by sorting
// Space Complexity: O(n) - merged vector stores at most n intervals (output space)
class Solution {
public:
    vector<vector<int>> merge(vector<vector<int>>& intervals) {
        sort(intervals.begin(), intervals.end());

        vector<vector<int>> merged;

        for(auto interval : intervals){
            if(merged.empty()|| merged.back()[1]<interval[0]){
                merged.push_back(interval);
            }
            else{
                merged.back()[1]=max(merged.back()[1],interval[1]);
            }
        }
        return merged;
    }
};