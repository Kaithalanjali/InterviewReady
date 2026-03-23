// Time Complexity: hit() - O(1), getHits() - O(n) where n is total hits
// Space Complexity: O(n) - queue stores all hits
class HitCounter {
public:
    queue<int> q;
    HitCounter() {

    }

    void hit(int timestamp) {
        q.push(timestamp);
    }

    int getHits(int timestamp) {
        queue<int> temp = q;
        int upperBound = timestamp;
        int lowerBound = max(0,timestamp-300);

        while(!temp.empty() && temp.front()<=lowerBound){
            temp.pop();
        }
        return temp.size();
    }
};