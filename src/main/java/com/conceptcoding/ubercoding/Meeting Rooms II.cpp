// Time Complexity: O(n log n) - sorting takes O(n log n), heap operations take O(n log n)
// Space Complexity: O(n) - priority queue can store at most n meetings
class Solution {
public:
    int minMeetingRooms(vector<vector<int>>& intervals) {
        sort(intervals.begin(),intervals.end());

        priority_queue<pair<int,int>,vector<pair<int,int>>, greater<pair<int,int>>> pq;
        for(int i=0;i<intervals.size();i++){

            if(!pq.empty() && pq.top().first<=intervals[i][0]){
                pq.pop();
            }
            pq.push({intervals[i][1],intervals[i][0]});
        }
        return pq.size();
    }
};