package com.conceptcoding.Concurrency.WebCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution {
    private String hostName;

    private ConcurrentHashMap<String, Boolean> urlHashMap = new ConcurrentHashMap<>();

    private ExecutorService executor = Executors.newFixedThreadPool(5);

    private AtomicInteger numOfUrlsToParse = new AtomicInteger(0);

    private HtmlParser htmlParser;

    class Task implements Runnable{
        private String url;

        Task(String url){
            this.url = url;
        }

        @Override
        public void run() {
            for(String extractedUrl : htmlParser.getUrls(url)){
                String curHostName = extractedUrl.split("/")[2];
                if(curHostName.equals(hostName)){
                    if(!urlHashMap.putIfAbsent(extractedUrl,true)){
                        numOfUrlsToParse.addAndGet(1);

                        executor.submit(new Task(extractedUrl));
                    }
                }
            }
            numOfUrlsToParse.addAndGet(-1);
        }
    }

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        this.htmlParser = htmlParser;
        this.hostName = startUrl.split("/")[2];

        urlHashMap.putIfAbsent(startUrl,true);
        numOfUrlsToParse.addAndGet(1);

        executor.submit(new Task(startUrl));

        while(numOfUrlsToParse.get() > 0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        return new ArrayList<>(urlHashMap.keySet());
    }
}

