package com.example.appengine;

import java.util.List;

public class Notify {
    
    public static String getInfo() {
        PubsubUtils pubsub = new PubsubUtils();
        List<String> retrievedMessages = pubsub.retrieveMessage();

        String output = "";
        for (String message:retrievedMessages) {
            output += String.format("<br />%s", message);
        }

        return output;
    }
}