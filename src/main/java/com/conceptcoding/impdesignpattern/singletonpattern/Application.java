package com.conceptcoding.impdesignpattern.singletonpattern;

public class Application {
    public static void main(String[] args) {
        // 4. Fetch the single instance of the Logger
        Logger logger = Logger.getInstance();
        logger.log("Application started.");
    }
}
