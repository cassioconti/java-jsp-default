package com.example.appengine;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.PubsubScopes;
import com.google.api.services.pubsub.model.PublishRequest;
import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.common.collect.ImmutableList;

public class PubsubUtils {

    private static final String projectId = "cassioconti1";
    private static final String topicName = "something-happened";

    public boolean publishMessage() {
        Pubsub client = getClient();

        String message = "This is the message saying that something had happened";
        String fullTopicName = String.format("projects/%s/topics/%s", projectId, topicName);

        try {
            PubsubMessage pubsubMessage = new PubsubMessage();
            pubsubMessage.encodeData(message.getBytes("UTF-8"));
            PublishRequest publishRequest = new PublishRequest();
            publishRequest.setMessages(ImmutableList.of(pubsubMessage));

            client.projects().topics()
                    .publish(fullTopicName, publishRequest)
                    .execute();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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