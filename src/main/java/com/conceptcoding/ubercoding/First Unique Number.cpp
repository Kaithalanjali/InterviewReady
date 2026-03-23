// Time Complexity: Constructor O(n), showFirstUnique() O(n), add() O(1)
// Space Complexity: O(n) - hashmap and queue store all numbers
class FirstUnique {
public:
    unordered_map<int, int> mp;
    queue<int> q;
    FirstUnique(vector<int>& nums) {

        for (int i = 0; i < nums.size(); i++) {
            mp[nums[i]]++;
            q.push(nums[i]);
        }
    }

    int showFirstUnique() {

        while(!q.empty()){
            int temp = q.front();
            if(mp[temp]!=1){
                q.pop();
            }else{
                return temp;
            }
        }
        return -1;

    }

    void add(int value) {
        mp[value]++;
        q.push(value);
    }
};

/**
 * Your FirstUnique object will be instantiated and called as such:
 * FirstUnique* obj = new FirstUnique(nums);
 * int param_1 = obj->showFirstUnique();
 * obj->add(value);
 */