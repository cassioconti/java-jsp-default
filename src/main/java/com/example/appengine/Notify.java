package com.example.appengine;

public class Notify {
    
    public static String getInfo() {
        PubsubUtils pubsub = new PubsubUtils();

        return " Message published: " + pubsub.publishMessage();
    }
}