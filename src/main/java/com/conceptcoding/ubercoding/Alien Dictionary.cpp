// Time Complexity: O(C) where C is the total number of characters in all words
// Space Complexity: O(1) or O(26) - at most 26 unique characters in the alphabet
class Solution {
public:
    string alienOrder(vector<string>& words) {
        unordered_map<char, vector<char>> adjList;
        unordered_map<char, int> counts;
        for (string word : words) {
            for (char c : word) {
                counts[c] = 0;
                adjList[c] = vector<char>();
            }
        }
        for(int i=0;i<words.size()-1;i++){
            string word1 = words[i];
            string word2 = words[i+1];

            if(word1.size() > word2.size() && word1.substr(0,word2.size())==word2){
                return "";
            }

            for(int j=0;j<min(word1.size(),word2.size());j++){
                if(word1[j]!=word2[j]){
                    adjList[word1[j]].push_back(word2[j]);
                    counts[word2[j]]++;
                    break;
                }
            }
        }

        string sb = "";
        queue<char> queue;
        for(auto item : counts){
            if(item.second==0){
                queue.push(item.first);
            }
        }
        while(!queue.empty()){
            char c = queue.front();
            queue.pop();
            sb+=c;
            for(char next : adjList[c]){
                counts[next]--;
                if(counts[next]==0){
                    queue.push(next);
                }
            }
        }
        if(sb.size()<counts.size()){
            return "";
        }
        return sb;
    }
};