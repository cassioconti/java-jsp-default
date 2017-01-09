package com.example.appengine;

public class Notify {
    
    public static String getInfo() {
        PubsubUtils pubsub = new PubsubUtils();

        return "Version: " + System.getProperty("java.version")
                + " OS: " + System.getProperty("os.name")
                + " User: " + System.getProperty("user.name")
                + " Message published: " + pubsub.publishMessage();
    }
}