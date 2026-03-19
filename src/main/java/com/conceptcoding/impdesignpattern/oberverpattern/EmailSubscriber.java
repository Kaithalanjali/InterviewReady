package com.conceptcoding.impdesignpattern.oberverpattern;

public class EmailSubscriber implements Subscriber{
    @Override
    public void update(String video) {
        System.out.println("Email - New video uploaded: " + video);
    }
}
