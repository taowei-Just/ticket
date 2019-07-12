package com.example.demo.crawl.engine;

import com.example.demo.crawl.iml.Icrawl;

public class Crawl {

    public static void main(String[] args) {
         
        
        crawl( "", new Icrawl(){
            @Override
            public void crawl(Object httprequest) {
                
            }
        });
        
        
    }

    public static void crawl(String host ,Icrawl icrawl) {
        icrawl.crawl( httprequest(host));
    }

    private static Object httprequest(String host) {
        return null;
    }


}
