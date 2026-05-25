package com.conceptcoding.codezymLLD;

import java.util.Arrays;

public class run {
    public static void main(String[] args){
//        HitCounter hitCounter = new HitCounter();
//        hitCounter.recordClick(1);
//        hitCounter.recordClick(2);
//        hitCounter.recordClick(3);
//        System.out.println(hitCounter.getRecentClicks(4)); // should print 3
//        System.out.println(hitCounter.getRecentClicks(300)); // should print 3
//        System.out.println(hitCounter.getRecentClicks(301)); // should print 2

        MeetingRoomReservation rb = new MeetingRoomReservation(Arrays.asList("roomA", "roomB"));
        System.out.println(rb.bookMeeting("m1", 10, 20));   // returns "roomA"
        System.out.println(rb.bookMeeting("m2", 15, 25));   // returns "roomB"
        System.out.println(rb.bookMeeting("m3", 20, 30));   // returns "" (20 conflicts with both rooms)
        System.out.println(rb.cancelMeeting("m1"));          // returns true
        System.out.println(rb.bookMeeting("m4", 20, 30));   // returns "roomA" (now free after cancel)

        MeetingRoomReservation rb2 = new MeetingRoomReservation(Arrays.asList("Z1", "A1", "M3"));

        System.out.println(rb2.bookMeeting("x", 5, 5));     // returns "A1" (lexicographically smallest)
        System.out.println(rb2.bookMeeting("y", 5, 6));     // returns "M3"
        System.out.println(rb2.cancelMeeting("nope"));       // returns false (no such active meeting)
        System.out.println(rb2.bookMeeting("z", 6, 10));    // returns "A1" (5..5 and 6..10 do not overlap)

        FileSystemShell fs = new FileSystemShell();
        System.out.println(fs.pwd()); // should print "/"
        fs.mkdir("/a/b/c");
        System.out.println(fs.pwd()); // should still print "/"
        fs.cd("/a/b");
        System.out.println(fs.pwd()); // should print "/a/b"
        fs.cd("*");
        System.out.println(fs.pwd()); // should print "/a/b/c"
        fs.cd("../*");
        System.out.println(fs.pwd()); // should print "/a/b/c"
        fs.cd("/ *");
        System.out.println(fs.pwd()); // should print "/a/b/c"
        fs.cd("/nope/*/x");
        System.out.println(fs.pwd()); // should print "/a/b/c"


    }
}
