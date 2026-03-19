package com.conceptcoding.impdesignpattern.oberverpattern;

public interface YouTubeChannel {
    void addSubscriber(Subscriber subscriber);
    void removeSubscriber(Subscriber subscriber);
    void notifySubscribers(String video);
    void uploadNewVideo(String video);
}
