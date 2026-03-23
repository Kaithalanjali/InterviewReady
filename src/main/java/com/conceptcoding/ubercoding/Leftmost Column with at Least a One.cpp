// Time Complexity: O(m log n) where m is rows and n is columns
// Space Complexity: O(1) - only uses constant extra space
class Solution {
public:
    int leftMostColumnWithOne(BinaryMatrix &binaryMatrix) {
        int rows = binaryMatrix.dimensions()[0];
        int cols = binaryMatrix.dimensions()[1];

        int smallestIndex = cols;
        for(int row = 0;row<rows;row++){
            int lo = 0;
            int hi = cols-1;
            while(lo<hi){
                int mid = (lo+hi)/2;
                if(binaryMatrix.get(row,mid)==0){
                    lo = mid+1;
                }else{
                    hi = mid;
                }

            }
            if(binaryMatrix.get(row,lo)==1){
                smallestIndex = min(smallestIndex,lo);
            }

        }

        return smallestIndex==cols?-1:smallestIndex;

    }
};