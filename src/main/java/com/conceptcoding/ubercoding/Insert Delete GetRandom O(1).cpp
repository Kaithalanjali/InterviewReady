// Time Complexity: insert() O(1), remove() O(1), getRandom() O(n) where n is number of elements
// Space Complexity: O(n) - unordered_set and unordered_map both store n elements
// Note: getRandom() is O(n) due to creating vector from set, not meeting O(1) requirement
class RandomizedSet {
public:
    unordered_set<int> s;
    unordered_map<int,int> mp;

    RandomizedSet() {}

    bool insert(int val) {
        s.insert(val);
        bool present = (mp[val] == 0) ? true : false;
        mp[val] = 1;
        return present;
    }

    bool remove(int val) {
        s.erase(val);
        bool present = (mp[val] == 1) ? true : false;
        mp[val] = 0;
        return present;
    }

    int getRandom() {
        vector<int> valueList(s.begin(), s.end());
        int randomIndex = rand() % valueList.size();
        return valueList[randomIndex];
    }
};

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet* obj = new RandomizedSet();
 * bool param_1 = obj->insert(val);
 * bool param_2 = obj->remove(val);
 * int param_3 = obj->getRandom();
 */