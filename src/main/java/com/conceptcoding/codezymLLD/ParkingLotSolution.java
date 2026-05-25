package com.conceptcoding.codezymLLD;

import java.util.HashMap;
import java.util.Map;
/*

Write code for low level design of a parking lot with multiple floors.
The parking lot has two kinds of parking spaces: type = 2, for 2 wheeler vehicles and type = 4, for 4 wheeler vehicles.

There are multiple floors in the parking lot. On each floor, vehicles are parked in parking spots arranged in rows and columns.
For simplicity, lets assume that each floor will have same number of rows and each row will have same number of columns.

You can solve this question in either Java or Python
Implement the below methods in Solution class:

init(Helper helper, int [][][] parking)
- helper has methods like, helper.print("") and helper.println("") which you can use for printing logs
- parking[i][j][k] : parking spot on i-th floor, j-th row and k-th column.
- each item in parking array is of the following type.
    4 : 4 wheeler parking spot,
    2 : 2 wheeler parking spot,
    0 : inactive spot, no vehicle can be parked here

park(int vehicleType, String vehicleNumber, String ticketId, int parkingStrategy)
returns spotId
- This function assigns an empty parking spot to vehicle and maps vehicleNumber and ticketId to the assigned spotId
- spotId is floor+"-"+row+"-"+column
e.g. parking[2][0][15] = parking spot at 2nd floor , 0th row and 15th column (0 based index),
its spotId will be: "2-0-15"
- parkingStrategy has two values, 0 and 1

parkingStrategy = 0
- Get the parking spot at lowest index i.e. lowest floor, row and column
e.g. park() is called with vehicleType 4 and we have free 4-wheeler spots at
parking[0][0][0], parking[0][0][1] and parking[1][0][2]
here we will return parking[0][0][0] because its index (floor, row, column) comes before the other two.

parkingStrategy = 1 :
- Get the floor with maximum number of free spots for the given vehicle type.
- If multiple floors have maximum free spots then choose the floor at lowest index from them.
e.g. park() is called with vehicleType 4 and floor[0] has 2 free 4 wheeler parking spots and
floor[1] and floor[3] both have 3 empty 4-wheeler parking spots.
here we will return the free 4-wheeler parking spot at lowest index from floor[1],
because apart from having highest number of free 4-wheeler spots it also comes before floor[3],
which also has 3 empty 4-wheeler parking spots.

removeVehicle(String spotId)
- Unparks or removes vehicle from parking spot.
- returns true if vehicle is removed
- returns false if vehicle not found or any other error


String searchVehicle(String query)
- searches the latest parking details of a vehicle parked in previous park() method calls.
- returns spotId e.g. 2-0-15 or empty string ""
- Query will be either vehicleNumber or ticketId.

int getFreeSpotsCount(int floor, int vehicleType)
- At any point of time get the number of free spots of vehicle type (2 or 4 wheeler).
- 0>= floor < parking.length (parking array from init() method).


 */
public class ParkingLotSolution implements Q07ParkingLotInterface {
    private Helper07 helper;
    public ParkingLotSolution(){}

    Integer[][][] parkingType;
    boolean[][][] occupied;

    int floors;
    int rows;
    int cols;

    int[] free2W;
    int[] free4W;

    Map<String,String> vehicleMap = new HashMap<>();
    Map<String,String> ticketMap = new HashMap<>();
    Map<String,String> spotVehicle = new HashMap<>();

    /**
     * Initialize the parking lot with given structure
     * Time Complexity: O(F × R × C) where F = floors, R = rows, C = columns
     *   - Triple nested loop to count free spots: O(F × R × C)
     * Space Complexity: O(F × R × C) for the occupied array + O(F) for free count arrays
     */
    public void init(Helper07 helper, Integer [][][] parking) {
        this.helper=helper;
        parkingType = parking;

        floors=parking.length;
        rows=parking[0].length;
        cols=parking[0][0].length;

        occupied=new boolean[floors][rows][cols];

        free2W=new int[floors];
        free4W=new int[floors];

        for (int f=0;f<floors;f++){
            for(int r=0;r<rows;r++){
                for(int c=0;c<cols;c++){
                    if(parking[f][r][c]==2){
                        free2W[f]++;
                    }
                    if(parking[f][r][c]==4){
                        free4W[f]++;
                    }
                }
            }
        }
         helper.println("parking lot initialized");
    }

    /**
     * Park a vehicle using specified strategy
     * Time Complexity:
     *   - Strategy 0 (First Available): O(F × R × C) worst case - iterate through all spots
     *   - Strategy 1 (Max Free Floor): O(F × R × C) worst case
     *     - Finding max floor: O(F)
     *     - Finding spot on that floor: O(R × C)
     * Space Complexity: O(1) - only adds constant entries to HashMaps
     */
    // returns spotId, e.g. 2-0-11 which is parking spot at parking[2][0][11]
    public String park(int vehicleType, String vehicleNumber,
                       String ticketId, int parkingStrategy){
        int floor = -1;
        if(parkingStrategy==1){
            int max=-1;
            for(int f=0;f<floors;f++){
                int freeCount=vehicleType==2?free2W[f]:free4W[f];
                if(freeCount>max){
                    max=freeCount;
                    floor=f;
                }
            }
            if(max<=0){
                return "";
            }
        }
        if(parkingStrategy==0){
            for(int f=0;f<floors;f++){
                for(int r=0;r<rows;r++){
                    for(int c=0;c<cols;c++){
                        if(parkingType[f][r][c]==vehicleType && !occupied[f][r][c]){
                            floor=f;
                            String spot = f+"-"+r+"-"+c;
                            occupied[f][r][c]=true;
                            spotVehicle.put(spot,vehicleNumber);
                            vehicleMap.put(vehicleNumber,spot);
                            ticketMap.put(ticketId,spot);
                            if(vehicleType==2){
                                free2W[f]--;
                            }else {
                                free4W[f]--;
                            }
                            return spot;
                        }

                    }
                }
            }
        }
            if(floor==-1){
                return "";
            }
            for(int r=0;r<rows;r++){
                for(int c=0;c<cols;c++){
                    if(parkingType[floor][r][c]==vehicleType && !occupied[floor][r][c]){
                        String spot = floor+"-"+r+"-"+c;
                        occupied[floor][r][c]=true;
                        spotVehicle.put(spot,vehicleNumber);
                        vehicleMap.put(vehicleNumber,spot);
                        ticketMap.put(ticketId,spot);
                        if(vehicleType==2){
                            free2W[floor]--;
                        }else {
                            free4W[floor]--;
                        }
                        return spot;
                    }
                }
            }
            return "";
    }

    /**
     * Remove a vehicle from a parking spot
     * Time Complexity: O(1)
     *   - HashMap containsKey: O(1) average
     *   - String split: O(1) (fixed format "F-R-C")
     *   - HashMap remove operations: O(1) average each
     *   - Array access and updates: O(1)
     * Space Complexity: O(1) - no additional data structures created
     */
    // spotId : 2-0-11 which is parking spot at parking[2][0][11]
    public boolean removeVehicle(String spotId){
        if(spotVehicle.containsKey(spotId)){
            String[] arr = spotId.split("-");
            int f = Integer.parseInt(arr[0]);
            int r = Integer.parseInt(arr[1]);
            int c = Integer.parseInt(arr[2]);

            occupied[f][r][c] = false;
            String vehicleNumber = spotVehicle.get(spotId);
            spotVehicle.remove(spotId);
            vehicleMap.remove(vehicleNumber);
            ticketMap.values().remove(spotId);
            if(parkingType[f][r][c]==2){
                free2W[f]++;
            }else {
                free4W[f]++;
            }
            return true;
        }
        return false;
    }

    /**
     * Search for a vehicle by vehicle number or ticket ID
     * Time Complexity: O(1)
     *   - HashMap containsKey: O(1) average
     *   - HashMap get: O(1) average
     * Space Complexity: O(1) - no additional data structures created
     */
    // query is either vehicleNumber or ticketId
    public String searchVehicle(String query){
        if(vehicleMap.containsKey(query)){
            return vehicleMap.get(query);
        }
        if(ticketMap.containsKey(query)){
            return ticketMap.get(query);
        }
        return "";
    }

    /**
     * Get the count of free spots on a specific floor for a vehicle type
     * Time Complexity: O(1)
     *   - Array access: O(1)
     * Space Complexity: O(1)
     */
    public int getFreeSpotsCount(int floor, int vehicleType){
        if(vehicleType==2){
            return free2W[floor];
        }
        if(vehicleType==4){
            return free4W[floor];
        }
        return 0;
    }
}

// uncomment below code in case you are using your local ide and
// comment it back again back when you are pasting completed solution in the online CodeZym editor
// this will help avoid unwanted compilation errors and get method autocomplete in your local code editor.

 interface Q07ParkingLotInterface {
 void init(Helper07 helper, Integer [][][] parking);
 String park(int vehicleType, String vehicleNumber, String ticketId, int parkingStrategy);
 boolean removeVehicle(String spotId);
 String searchVehicle(String query);
 int getFreeSpotsCount(int floor, int vehicleType);
 }

 class Helper07{
 void print(String s){System.out.print(s);} void println(String s){print(s+"\n");}
 }

