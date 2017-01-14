package com.example.appengine;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.*;

public class StorageUtils {

    private static final String bucket = "cassioconti1.appspot.com";

    public List<StorageObject> listStorageObjects() {
        List<StorageObject> storageObjects = new ArrayList<>();

        try {
            String uri = String.format("https://www.googleapis.com/storage/v1/b/%s/o", URLEncoder.encode(bucket, "UTF-8"));
            Set<String> scopes = new HashSet<>();
            scopes.add("https://www.googleapis.com/auth/devstorage.read_only");
            JsonObject jsonObject = this.getJson(uri, scopes);

            JsonArray items = jsonObject.getAsJsonArray("items");
            Iterator<JsonElement> iterator = items.iterator();
            while (iterator.hasNext()) {
                JsonObject storageItemJson = iterator.next().getAsJsonObject();

                String name = storageItemJson.get("name").getAsString();
                String id = storageItemJson.get("id").getAsString();
                String contentType = storageItemJson.get("contentType").getAsString();
                String timeCreated = storageItemJson.get("timeCreated").getAsString();
                String size = storageItemJson.get("size").getAsString();

                StorageObject storageObject = new StorageObject(name, id, contentType, size, timeCreated);
                storageObjects.add(storageObject);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return storageObjects;
    }

    private JsonObject getJson(String uri, Set<String> scope) {
        try {
            GenericUrl url = new GenericUrl(uri);
            HttpRequest request = this.getCredentials(scope).buildGetRequest(url);
            HttpResponse response = request.execute();
            String contentJson = response.parseAsString();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(contentJson);
            return root.getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private HttpRequestFactory getCredentials(Set<String> scope) {
        try {
            GoogleCredential credential = GoogleCredential.getApplicationDefault().createScoped(scope);
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);

            return requestFactory;

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}