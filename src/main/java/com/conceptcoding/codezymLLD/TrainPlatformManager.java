package com.conceptcoding.codezymLLD;

import java.util.*;
/*
32. Design a Train Platform Management System

Design a system that manages assignment of trains to platforms in a railway station and supports time-based queries, with a clean, extensible object-oriented design.

At any time only one train can be assigned to a single platform.
Methods
TrainPlatformManager(int platformCount)
- 1 ≤ platformCount ≤ 20
- platforms are numbered 0 to platformCount-1

String assignPlatform(String trainId, int arrivalTime, int waitTime)
- trainId will always be non blank and globally unique.
- 1 ≤ arrivalTime
- 1 ≤ waitTime ≤ 30
- departureTime = arrivalTime + waitTime - 1 + delay
- delay will occur if no platform is available immediately at arrivalTime.
- both arrivalTime and departureTime are inclusive.
- i.e. if train A departs from platform p at time t,
then another train B can't arrive at platform p before time t+1

- you have to return two comma separated integers as string
- platformNumber+","+delayTime (delay time is there in case no platform is free on train's arrival)
- e.g. "4,6" i.e. train is assigned to platform 4 but it will have to wait 6 minutes before arrival.
- "12,0" train is assigned platform 12 and it can park immediately after arrival.
- you must assign a platform with lowest wait time.
- if multiple platforms have minimum wait time, choose the one with lowest index.

String getTrainAtPlatform(int platformNumber, int timestamp)
- returns occupying trainId or "" if none.

int getPlatformOfTrain(String trainId, int timestamp)
- returns platform number (1..platformCount) or -1 if not on any platform at the timestamp.
 */
public class TrainPlatformManager {
    static class Train{
        String trainId;
        int start;
        int end;
        public Train(String trainId, int start, int end){
            this.trainId = trainId;
            this.start = start;
            this.end = end;
        }
    }

    static class TrainRecord{
        int platform;
        int start;
        int end;

        public TrainRecord(int platform, int start, int end){
            this.platform = platform;
            this.start = start;
            this.end = end;
        }
    }

    List<TreeMap<Integer, Train>> schedules;
    Map<String, TrainRecord> trainMap;

    /**
     * Initialize the train platform manager with specified number of platforms
     * Time Complexity: O(P) where P = platformCount (creating P empty TreeMaps)
     * Space Complexity: O(P) for storing P platform schedules
     */
    public TrainPlatformManager(int platformCount) {
        schedules = new ArrayList<>();
        trainMap = new HashMap<>();
        for(int i=0;i<platformCount;i++){
            schedules.add(new TreeMap<>());
        }
    }

    /**
     * Assign a platform to a train, minimizing delay and preferring lower platform numbers
     * Time Complexity: O(P * T * log(N)) where:
     *   - P = number of platforms
     *   - T = number of trains already on a platform (worst case iterations in while loop)
     *   - N = average number of trains per platform
     *   - For each platform: iterate through conflicts (O(T)), each TreeMap operation is O(log(N))
     * Space Complexity: O(1) - creates one Train and one TrainRecord object
     */
    public String assignPlatform(String trainId, int arrivalTime, int waitTime) {
        int bestPlatform = -1;
        int bestDelay = Integer.MAX_VALUE;
        int bestStart = -1;

        for(int p=0;p<schedules.size();p++){
            TreeMap<Integer, Train> schedule = schedules.get(p);
            int start = arrivalTime;

            while(true){
                Map.Entry<Integer,Train> entryFloor = schedule.floorEntry(start);

                if(entryFloor!=null && entryFloor.getValue().end>=start){
                    start = entryFloor.getValue().end+1;
                    continue;
                }

                //checking for end time conflict

                Map.Entry<Integer, Train> entryCeil = schedule.ceilingEntry(start);

                int end = start+waitTime-1;
                if(entryCeil==null || entryCeil.getKey()>end){
                    //no conflict
                    break;
                }
                start = entryCeil.getValue().end+1;
            }
            int delay = start - arrivalTime;

            if(delay<bestDelay || (delay==bestDelay && p<bestPlatform)){
                bestDelay = delay;
                bestPlatform = p;
                bestStart = start;
            }
        }
        int end = bestStart + waitTime-1;
        Train train = new Train(trainId, bestStart, end);
        schedules.get(bestPlatform).put(bestStart, train); // Use start time as key, not platform number
        trainMap.put(trainId, new TrainRecord(bestPlatform, bestStart,end));
        return bestPlatform+"-"+bestDelay;
    }

    /**
     * Get the train occupying a specific platform at a given timestamp
     * Time Complexity: O(log(N)) where N = number of trains on that platform
     *   - floorEntry operation on TreeMap: O(log(N))
     *   - Comparison operations: O(1)
     * Space Complexity: O(1) - no additional data structures created
     */
    public String getTrainAtPlatform(int platformNumber, int timestamp) {
        if(platformNumber<0 || platformNumber>=schedules.size()){
            return "";
        }
        TreeMap<Integer, Train> schedule = schedules.get(platformNumber);
        Map.Entry<Integer, Train> entry = schedule.floorEntry(timestamp);

        if(entry!=null && timestamp<=entry.getValue().end){
            return entry.getValue().trainId;
        }
        return  "";
    }

    /**
     * Get the platform number where a specific train is located at a given timestamp
     * Time Complexity: O(1)
     *   - HashMap containsKey: O(1) average
     *   - HashMap get: O(1) average
     *   - Comparison operations: O(1)
     * Space Complexity: O(1) - no additional data structures created
     */
    public int getPlatformOfTrain(String trainId, int timestamp) {
        if(trainMap.containsKey(trainId)){
            TrainRecord trainRecord = trainMap.get(trainId);
            if(timestamp>=trainRecord.start && timestamp<=trainRecord.end){
                return trainRecord.platform;
            }
        }
        return -1;
    }

    public static void main(String[] args){
        TrainPlatformManager manager = new TrainPlatformManager(2);
        System.out.println(manager.assignPlatform("T1", 10, 5)); // Should assign to platform 0 with no delay
        System.out.println(manager.assignPlatform("T2", 12, 5)); // Should assign to platform 1 with no delay
        System.out.println(manager.assignPlatform("T3", 11, 5)); // Should assign to platform 0 with a delay of 5 (starts at 15)
        System.out.println(manager.getTrainAtPlatform(0, 10)); // Should return "T1"
        System.out.println(manager.getTrainAtPlatform(0, 14)); // Should return "T1"
        System.out.println(manager.getTrainAtPlatform(0, 15)); // Should return "T3"
        System.out.println(manager.getTrainAtPlatform(1, 12)); // Should return "T2"
        System.out.println(manager.getTrainAtPlatform(1, 17)); // Should return "T2"
        System.out.println(manager.getPlatformOfTrain("T1", 12)); // Should return 0
        System.out.println(manager.getPlatformOfTrain("T2", 13)); // Should return 1
        System.out.println(manager.getPlatformOfTrain("T3", 16)); // Should return 0
    }
}
