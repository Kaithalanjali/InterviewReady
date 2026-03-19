package com.conceptcoding.impdesignpattern.oberverpattern;

import java.util.ArrayList;
import java.util.List;

public class YouTubeChannelImpl implements YouTubeChannel {
    private String channelName;
    private List<Subscriber> subscribers;
    private String video;

    public YouTubeChannelImpl(String channelName) {
        this.channelName = channelName;
        this.subscribers = new ArrayList<>();
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(String video) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(video);
        }
    }

    @Override
    public void uploadNewVideo(String video) {
        this.video = video; // Set the video that is being uploaded
        notifySubscribers(video); // Notify all subscribers about the new video
    }
}
