// Time Complexity: set() O(log n), get() O(n) where n is timestamps for a key
// Space Complexity: O(n) - stores all key-value-timestamp tuples
class TimeMap {
public:
    unordered_map<string,priority_queue<pair<int,string>>> mp;
    TimeMap() {
        mp.clear();
    }

    void set(string key, string value, int timestamp) {
        mp[key].push({timestamp,value});
    }

    string get(string key, int timestamp) {
        vector<pair<int,string>> rem;
        while(!mp[key].empty() && mp[key].top().first>timestamp){
            rem.push_back(mp[key].top());
            mp[key].pop();
        }
        string ans = mp[key].empty()?"":mp[key].top().second;

        for(auto b:rem){
            mp[key].push(b);
        }
        return ans;
    }
};

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap* obj = new TimeMap();
 * obj->set(key,value,timestamp);
 * string param_2 = obj->get(key,timestamp);
 */