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

/**
 * Meeting Room Reservation System
 *
 * OVERALL COMPLEXITY SUMMARY:
 * - Constructor: O(R log R) time, O(R) space
 * - bookMeeting: O(R * log M) time, O(1) amortized space
 * - cancelMeeting: O(log M) time, O(1) space
 *
 * Where:
 * - R = number of rooms
 * - M = number of meetings per room (average)
 *
 * SPACE COMPLEXITY SUMMARY:
 * - O(R + T) where T is total number of active meetings across all rooms
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

    /**
     * Constructor: Initialize the meeting room reservation system
     *
     * Time Complexity: O(R log R) where R = number of rooms
     *   - Collections.sort(rooms): O(R log R)
     *   - Creating R empty TreeMaps: O(R)
     *   - Overall: O(R log R)
     *
     * Space Complexity: O(R)
     *   - rooms list: O(R)
     *   - roomsSchedule HashMap: O(R) for R empty TreeMaps
     *   - meetings HashMap: O(1) initially
     *   - Overall: O(R)
     */
    public MeetingRoomReservation(List<String> roomIds) {
        rooms = roomIds;
        Collections.sort(rooms); // O(R log R) - sort to ensure lexicographic ordering
        roomsSchedule = new HashMap<>();
        meetings = new HashMap<>();
        for(String it:rooms){
            roomsSchedule.put(it, new TreeMap<>());
        }
    }

    /**
     * Book a meeting in the lexicographically smallest available room
     *
     * Time Complexity: O(R * log M) where R = number of rooms, M = meetings per room
     *   - Check if meetingId exists: O(1) HashMap lookup
     *   - For each room (R iterations):
     *     - TreeMap.ceilingEntry(): O(log M)
     *     - TreeMap.lowerEntry(): O(log M)
     *     - TreeMap.put(): O(log M)
     *   - Overall: O(R * log M)
     *
     * Best case: O(log M) - first room is available
     * Worst case: O(R * log M) - check all rooms, last one is available or none available
     *
     * Space Complexity: O(1) amortized
     *   - Adds one entry to meetings HashMap: O(1) amortized
     *   - Adds one entry to one room's TreeMap: O(1) amortized
     *   - No temporary data structures created
     */
    public String bookMeeting(String meetingId, int startTime, int endTime) {
        // Check if meeting ID already exists - O(1)
        if(meetings.containsKey(meetingId)){
            return "";
        }

        // Iterate through rooms in lexicographic order - O(R)
        for(String room:rooms){
            TreeMap<Integer, Integer> schedule = roomsSchedule.get(room);

            // Check for overlaps using TreeMap operations - O(log M)
            Map.Entry<Integer, Integer> next = schedule.ceilingEntry(startTime); // O(log M)
            boolean overlap = false;

            // Check if next meeting conflicts
            if(next!=null && next.getKey()<=endTime){
                overlap=true;
            }

            // Check if previous meeting conflicts
            Map.Entry<Integer, Integer> prev = schedule.lowerEntry(startTime); // O(log M)
            if(prev!=null && prev.getValue()>=startTime){
                overlap=true;
            }

            // If no overlap, book the room
            if(!overlap){
                schedule.put(startTime,endTime); // O(log M)
                meetings.put(meetingId, new MeetingInfo(room, startTime, endTime)); // O(1)
                return room;
            }
        }
        return "";
    }

    /**
     * Cancel an existing meeting
     *
     * Time Complexity: O(log M) where M = meetings per room
     *   - Check if meetingId exists: O(1) HashMap lookup
     *   - Get MeetingInfo: O(1) HashMap get
     *   - TreeMap.remove(): O(log M)
     *   - HashMap.remove(): O(1)
     *   - Overall: O(log M)
     *
     * Space Complexity: O(1)
     *   - Only removes entries, no new allocations
     *   - Temporary variable for MeetingInfo: O(1)
     */
    public boolean cancelMeeting(String meetingId) {
        // Check if meeting exists - O(1)
        if(!meetings.containsKey(meetingId)){
            return false;
        }

        // Retrieve meeting info - O(1)
        MeetingInfo meetingInfo = meetings.get(meetingId);

        // Remove from room schedule - O(log M)
        TreeMap<Integer, Integer> schedule = roomsSchedule.get(meetingInfo.room);
        schedule.remove(meetingInfo.start); // O(log M)

        // Remove from meetings map - O(1)
        meetings.remove(meetingId);
        return true;
    }
}
/*
================================================================================
COMPREHENSIVE TIME AND SPACE COMPLEXITY ANALYSIS
================================================================================

NOTATION:
- R = Number of rooms (up to 50,000)
- M = Average number of meetings per room
- T = Total active meetings across all rooms

================================================================================
TIME COMPLEXITY SUMMARY:
================================================================================

1. Constructor: O(R log R)
   - Sorting room IDs: O(R log R)
   - Creating empty TreeMaps: O(R)
   - Dominates to: O(R log R)

2. bookMeeting: O(R * log M)
   - Best case: O(log M) - first room available
   - Average case: O(R/2 * log M) - room found halfway
   - Worst case: O(R * log M) - check all rooms or no room available

   Breakdown per room checked:
   - ceilingEntry(): O(log M)
   - lowerEntry(): O(log M)
   - put(): O(log M)
   - Total per room: O(log M)
   - Max rooms to check: R
   - Overall: O(R * log M)

3. cancelMeeting: O(log M)
   - HashMap lookups: O(1)
   - TreeMap remove: O(log M)
   - Total: O(log M)

================================================================================
SPACE COMPLEXITY SUMMARY:
================================================================================

1. Constructor/Overall Storage: O(R + T)
   - rooms list: O(R)
   - roomsSchedule map: O(R) keys + O(T) total entries in TreeMaps
   - meetings map: O(T) entries
   - Total: O(R + T)

2. bookMeeting: O(1) amortized
   - Adds 1 entry to meetings HashMap
   - Adds 1 entry to one TreeMap in roomsSchedule
   - No temporary collections

3. cancelMeeting: O(1)
   - Removes entries (frees space)
   - Only uses temporary variables

================================================================================
REAL-WORLD PERFORMANCE CHARACTERISTICS:
================================================================================

Given constraints:
- R ≤ 50,000 rooms
- Operations ≤ 100,000
- M ≤ 100,000 / R (in worst case all meetings in one room)

Worst-case scenario:
- bookMeeting: O(50,000 * log(100,000)) ≈ O(50,000 * 17) ≈ 850,000 operations
- cancelMeeting: O(log(100,000)) ≈ 17 operations

Average scenario (meetings distributed):
- M ≈ T / R (meetings evenly distributed)
- bookMeeting: O(R * log(T/R))
- With T = 10,000 and R = 100: O(100 * log(100)) ≈ 665 operations

================================================================================
DATA STRUCTURE CHOICES AND TRADE-OFFS:
================================================================================

1. TreeMap for room schedules:
   ✓ O(log M) for overlap checks (ceilingEntry, lowerEntry)
   ✓ O(log M) for insertions and deletions
   ✓ Maintains sorted order by start time
   ✗ Slightly higher constant factors than hash-based structures

2. HashMap for meetings:
   ✓ O(1) lookup by meetingId
   ✓ O(1) for checking duplicates
   ✓ Stores meeting metadata (room, start, end)

3. Sorted List for rooms:
   ✓ Ensures lexicographic ordering
   ✓ Linear iteration for availability check
   ✗ One-time O(R log R) sort cost at initialization

Alternative approach (not used):
- Priority Queue per room: Would complicate overlap checking
- Interval Tree: Overkill for this problem, similar complexity
- Segment Tree: More complex, no significant benefit

================================================================================
*/


