package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class HTMLParser {
  // dummy implementation of an HTML parser
  public HTMLParser() {}

  public List<String> extractUrlsFromPage(String page) throws InterruptedException {
    Thread.sleep(1500);
    return new ArrayList<>();
  }
}

public class WebCrawler {
  // the startURL for the crawler
  private String hostName;

  // Collection to store all the URLs
  private final ConcurrentHashMap<String, Boolean> urlHashMap = new ConcurrentHashMap<>();

  // Max threads to spawn 
  private final int MAX_THREADS = 5;

  // HTML Parser instance
  private HTMLParser htmlParser;

  // Count
  final AtomicInteger noOfUrlsToParse = new AtomicInteger(0); // count. loop till this is 0
  final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

  class Task implements Runnable {
    private String url;

    public Task(String url) { this.url = url; }

    @Override
    public void run() {
      try {
        for (String urlToParse : htmlParser.extractUrlsFromPage(url)) {
          String currHostName = urlToParse.split("/")[2];
          if (currHostName.equals(hostName) && urlHashMap.putIfAbsent(urlToParse, true) == null) {
            noOfUrlsToParse.addAndGet(1);
            executor.submit(new Task(urlToParse));
          }
        }
        noOfUrlsToParse.addAndGet(-1);
      } catch (InterruptedException e) {}
    }
  }

  public List<String> crawlUrl(String url, HTMLParser parser) {
    // get the hostname from the url
    hostName = url.split("/")[2];

    // set the parser
    htmlParser = parser;

    // mark this url as visited in the map
    urlHashMap.put(url, true);
    noOfUrlsToParse.addAndGet(1);
    executor.submit(new Task(url));

    while (noOfUrlsToParse.get() > 0) {
      try {
        Thread.sleep(80);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }

    executor.shutdown();
    return new ArrayList<>(urlHashMap.keySet());
  }
}
