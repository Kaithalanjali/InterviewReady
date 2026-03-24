// Time Complexity: O(n) where n is the length of height array - single pass with two pointers
// Space Complexity: O(1) - only constant extra space used for variables
class Solution {
    public int maxArea(int[] height) {
        int maxarea = 0;
        int left = 0;
        int right = height.length-1;
        while(left<right){
            int width = right-left;
            maxarea= Math.max(maxarea,Math.min(height[left],height[right])*width);

            if(height[left]<=height[right]){
                left++;
            }else{
                right--;
            }
        }
        return maxarea;
    }
}