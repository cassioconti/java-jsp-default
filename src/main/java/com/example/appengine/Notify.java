package com.example.appengine;

import com.google.api.services.storage.model.StorageObject;

import java.util.List;

public class Notify {

    public static String getInfo() {

        StorageUtils storageUtils = new StorageUtils();
        List<StorageObject> storageObjects = storageUtils.listStorageObjects();
        String objects = "";
        for (StorageObject storageObject : storageObjects) {
            objects += String.format("<br />%s, %s, %s bytes, %s", storageObject.getName(), storageObject.getContentType(), storageObject.getSize(), storageObject.getTimeCreated());
        }

        return "Version: " + System.getProperty("java.version")
                + " OS: " + System.getProperty("os.name")
                + " User: " + System.getProperty("user.name")
                + objects;
    }
}