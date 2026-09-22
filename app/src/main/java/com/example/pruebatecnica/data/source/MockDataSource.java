package com.example.pruebatecnica.data.source;

import android.content.Context;

import com.example.pruebatecnica.data.model.Contact;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MockDataSource implements DataSource {

    private static final String FILE_NAME = "contacts.json";
    private final Context context;

    public MockDataSource(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public List<Contact> getContacts() {

        List<Contact> contacts = new ArrayList<>();

        try {
            String json = readAsset();
            JSONArray array = new JSONArray(json);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                contacts.add(new Contact(
                        obj.getInt("code"),
                        obj.getString("name"),
                        obj.getString("phone"),
                        obj.getString("email"),
                        obj.getBoolean("visited")
                ));
            }

        } catch (Exception e) {
            return new ArrayList<>();
        }
        return contacts;
    }

    private String readAsset() throws IOException {
        try (InputStream is = context.getAssets().open(FILE_NAME)) {
            byte[] buffer = new byte[is.available()];
            int read = is.read(buffer);
            if (read == -1) {
                return "[]";
            }
            return new String(buffer, StandardCharsets.UTF_8);
        }
    }
}
