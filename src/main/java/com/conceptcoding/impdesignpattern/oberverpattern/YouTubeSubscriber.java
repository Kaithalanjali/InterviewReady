package com.conceptcoding.impdesignpattern.oberverpattern;

public class YouTubeSubscriber implements Subscriber{
    @Override
    public void update(String video) {
        System.out.println("New youTube video uploaded: " + video);
    }
}
