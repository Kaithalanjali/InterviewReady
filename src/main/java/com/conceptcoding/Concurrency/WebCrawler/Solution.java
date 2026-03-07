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
/*
class Solution {
    private String getHostName(String url){
        return url.split("/")[2];
    }
    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        String hostName = getHostName(startUrl);

        List<String> res = new ArrayList<>();
        ConcurrentHashMap<String, Boolean> visited = new ConcurrentHashMap<>();

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Deque<Future> tasks = new ArrayDeque<>();

        queue.offer(startUrl);

        ExecutorService executor = Executors.newFixedThreadPool(4, r->{
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });

        while(true){
            String url = queue.poll();

            if(url!=null){
                if(getHostName(url).equals(hostName) && visited.get(url)==null){
                    res.add(url);
                    visited.put(url,true);

                    tasks.add(executor.submit(()->{
                        List<String> newUrls = htmlParser.getUrls(url);
                        for(String newUrl:newUrls){
                            queue.offer(newUrl);
                        }
                    }));
                }
            }else{
                if(!tasks.isEmpty()){
                    Future nextTask = tasks.poll();
                    try{
                        nextTask.get();
                    }catch(InterruptedException | ExecutionException e){

                    }

                }else{
                    break;
                }
            }
        }
        return res;

    }
}
*/
