package com.conceptcoding.impdesignpattern.oberverpattern;

public class Main {
    public  static void main(String[] args) {
        YouTubeChannel channel = new YouTubeChannelImpl("Code with Harry");
        Subscriber subscriber1 = new YouTubeSubscriber();
        Subscriber subscriber2 = new EmailSubscriber();

        channel.addSubscriber(subscriber1);
        channel.addSubscriber(subscriber2);

        channel.uploadNewVideo("Observer Pattern in Java");
    }
}
