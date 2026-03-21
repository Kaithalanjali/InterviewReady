package com.conceptcoding.uberlld;

import java.util.HashMap;
import java.util.Map;

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

