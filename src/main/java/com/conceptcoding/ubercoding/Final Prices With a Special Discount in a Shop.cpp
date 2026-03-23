// Time Complexity: O(n) - each element pushed and popped once from stack
// Space Complexity: O(n) - stack in worst case stores all elements
class Solution {
public:
    vector<int> finalPrices(vector<int>& prices) {
        int n = prices.size();
        vector<int> ans(n);
        stack<int> st;
        st.push(0);

        for(int i=1;i<n;i++){
            int newElement = prices[i];

            while(!st.empty() && prices[st.top()]>=newElement){
                int index = st.top();
                st.pop();
                prices[index] = prices[index]-newElement;
            }
            st.push(i);
        }
        return prices;
    }

};