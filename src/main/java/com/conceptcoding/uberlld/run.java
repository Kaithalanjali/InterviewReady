package com.conceptcoding.uberlld;

public class run {
    public static void main(String[] args){
        HitCounter hitCounter = new HitCounter();
        hitCounter.recordClick(1);
        hitCounter.recordClick(2);
        hitCounter.recordClick(3);
        System.out.println(hitCounter.getRecentClicks(4)); // should print 3
        System.out.println(hitCounter.getRecentClicks(300)); // should print 3
        System.out.println(hitCounter.getRecentClicks(301)); // should print 2
    }
}
