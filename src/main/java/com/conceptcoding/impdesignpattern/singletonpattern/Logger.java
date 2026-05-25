package com.conceptcoding.impdesignpattern.singletonpattern;

public class Logger {
    private static volatile Logger instance;

    //private constructor to prevent instantiation
    private void Loggger(){};

    public static Logger getInstance(){
        if(instance == null){
            synchronized (Logger.class){
                if(instance == null){
                    instance = new Logger();
                }
            }

        }
        return instance;
    }

    public void log(String message){
        System.out.println("[Logger] " + message);
    }
}
