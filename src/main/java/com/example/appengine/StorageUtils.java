package com.example.appengine;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;
import com.google.api.services.storage.model.Objects;
import com.google.api.services.storage.model.StorageObject;

import java.util.ArrayList;
import java.util.List;

public class StorageUtils {

    private static final String bucket = "cassioconti1.appspot.com";

    public List<StorageObject> listStorageObjects() {
        List<StorageObject> results = new ArrayList<>();

        try {
            Storage storage = this.getClient();
            Storage.Objects.List list = storage.objects().list(bucket);
            Objects objects;
            do {
                objects = list.execute();
                results.addAll(objects.getItems());
                list.setPageToken(objects.getNextPageToken());
            } while (null != objects.getNextPageToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    private Storage getClient() {
        try {
            HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = new JacksonFactory();
            GoogleCredential credential = GoogleCredential.getApplicationDefault();

            if (credential.createScopedRequired()) {
                credential = credential.createScoped(StorageScopes.all());
            }

            return new Storage.Builder(transport, jsonFactory, credential)
                    .setApplicationName("Storage Util")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}