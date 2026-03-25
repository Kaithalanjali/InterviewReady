package com.conceptcoding.uberlld;

import java.util.*;
/*
Design a Meeting room reservation System

Design a simple Meeting room reservation System for a fixed list of conference rooms. You will be given the room identifiers up front, and you must support booking and canceling meetings while ensuring no two meetings overlap in the same room. If multiple rooms are free for a requested time, always choose the lexicographically smallest room id. Time ranges are inclusive: a meeting ending at t conflicts with another starting at t in the same room.
Class name: RoomBooking
Constructor: RoomBooking(List<String> roomIds)
Methods:
String bookMeeting(String meetingId, int startTime, int endTime)
boolean cancelMeeting(String meetingId)
Details:

roomIds are fixed at construction, non-empty strings, and all unique.
bookMeeting returns the lexicographically smallest available room id for the given inclusive time range [startTime, endTime], or an empty string "" if no room can host it.
At most one meeting can occur in a room at any moment.
cancelMeeting returns true only if the meeting with the given meetingId exists and is currently active, and then cancels it, otherwise returns false.
meetingId values are strings, an id cannot refer to more than one active meeting at a time.
Examples
Example 1
RoomBooking rb = new RoomBooking(Arrays.asList("roomA", "roomB"));

rb.bookMeeting("m1", 10, 20);   // returns "roomA"
rb.bookMeeting("m2", 15, 25);   // returns "roomB"
rb.bookMeeting("m3", 20, 30);   // returns "" (20 conflicts with both rooms)
rb.cancelMeeting("m1");          // returns true
rb.bookMeeting("m4", 20, 30);   // returns "roomA" (now free after cancel)

Example 2
RoomBooking rb2 = new RoomBooking(Arrays.asList("Z1", "A1", "M3"));

rb2.bookMeeting("x", 5, 5);     // returns "A1" (lexicographically smallest)
rb2.bookMeeting("y", 5, 6);     // returns "M3"
rb2.cancelMeeting("nope");       // returns false (no such active meeting)
rb2.bookMeeting("z", 6, 10);    // returns "A1" (5..5 and 6..10 do not overlap)
Constraints
1 ≤ number of rooms ≤ 50,000
0 ≤ startTime ≤ endTime ≤ 10^9
Total number of operations (bookMeeting + cancelMeeting) ≤ 100,000
All room ids are unique, non-empty strings of length ≤ 50
meetingId is a non-empty string of length ≤ 50
Time ranges are inclusive on both ends
 */

public class MeetingRoomReservation {
    private List<String> rooms;
    private Map<String, TreeMap<Integer, Integer>> roomsSchedule;
    private static class MeetingInfo{
        String room;
        int start;
        int end;

        public MeetingInfo(String room, int start, int end){
            this.room = room;
            this.start = start;
            this.end = end;
        }
    }
    private Map<String, MeetingInfo> meetings;
    public MeetingRoomReservation(List<String> roomIds) {
        rooms = roomIds;
        Collections.sort(rooms);
        roomsSchedule = new HashMap<>();
        meetings = new HashMap<>();
        for(String it:rooms){
            roomsSchedule.put(it, new TreeMap<>());
        }
    }

    public String bookMeeting(String meetingId, int startTime, int endTime) {
        if(meetings.containsKey(meetingId)){
            return "";
        }

        for(String room:rooms){
            TreeMap<Integer, Integer> schedule = roomsSchedule.get(room);
            Map.Entry<Integer, Integer> next = schedule.ceilingEntry(startTime);
            boolean overlap = false;
            if(next!=null && next.getKey()<=endTime){
                overlap=true;
            }
            Map.Entry<Integer, Integer> prev = schedule.lowerEntry(startTime);
            if(prev!=null && prev.getValue()>=startTime){
                overlap=true;
            }
            if(!overlap){
                schedule.put(startTime,endTime);
                meetings.put(meetingId, new MeetingInfo(room, startTime, endTime));
                return room;
            }
        }
        return "";
    }

    public boolean cancelMeeting(String meetingId) {
        if(!meetings.containsKey(meetingId)){
            return false;
        }
        MeetingInfo meetingInfo = meetings.get(meetingId);

        TreeMap<Integer, Integer> schedule = roomsSchedule.get(meetingInfo.room);
        schedule.remove(meetingInfo.start);
        meetings.remove(meetingId);
        return true;
    }
}
/*
Time Complexity
bookMeeting
O(R log M)

Where:

R = rooms
M = meetings in room
cancelMeeting
O(log M)

TreeMap deletion.
 */
