package com.example.appengine;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.PubsubScopes;
import com.google.api.services.pubsub.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PubsubUtils {
    private String subscriptionName = "projects/cassioconti1/subscriptions/listening-happened";

    public String retrieveMessage(){
        Pubsub client = getClient();

        try {
            PullResponse pullResponse;
            PullRequest pullRequest = new PullRequest()
                    .setReturnImmediately(true)
                    .setMaxMessages(1000);

            pullResponse = client.projects().subscriptions()
                    .pull(subscriptionName, pullRequest)
                    .execute();

            List<String> ackIds = new ArrayList<>();
            List<ReceivedMessage> receivedMessages = pullResponse.getReceivedMessages();
            String messagesReceived = "";

            if (receivedMessages != null) {

                for (ReceivedMessage receivedMessage : receivedMessages) {
                    PubsubMessage pubsubMessage = receivedMessage.getMessage();
                    if (pubsubMessage != null
                            && pubsubMessage.decodeData() != null) {
                        messagesReceived += new String(pubsubMessage.decodeData(), "UTF-8") + " ";
                    }

                    ackIds.add(receivedMessage.getAckId());
                }

                AcknowledgeRequest ackRequest = new AcknowledgeRequest();
                ackRequest.setAckIds(ackIds);
                client.projects().subscriptions()
                        .acknowledge(subscriptionName, ackRequest)
                        .execute();
            }

            return messagesReceived;
        }  catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private Pubsub getClient() {
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = new JacksonFactory();
            GoogleCredential credential = GoogleCredential.getApplicationDefault();
            if (credential.createScopedRequired()) {
                credential = credential.createScoped(PubsubScopes.all());
            }

            return new Pubsub.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName("Pubsub Util")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}