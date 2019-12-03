package com.incountry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.incountry.crypto.impl.Crypto;
import lombok.Getter;
import lombok.Setter;
import org.javatuples.Pair;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;


public class Record {
    private static final String P_COUNTRY = "country";
    private static final String P_BODY = "body";
    private static final String P_KEY = "key";
    private static final String P_KEY_2 = "key2";
    private static final String P_KEY_3 = "key3";
    private static final String P_PROFILE_KEY = "profile_key";
    private static final String P_RANGE_KEY = "range_key";
    private static final String P_PAYLOAD = "payload";
    private static final String P_META = "meta";
    private static final String VERSION = "version";

    @Getter
    @Setter
    String country;

    @Getter
    @Setter
    String key;

    @Getter
    @Setter
    String body;

    @Getter
    @Setter
    @SerializedName("profile_key")
    String profileKey;

    @Getter
    @Setter
    @SerializedName("range_key")
    Integer rangeKey;

    @Getter
    @Setter
    String key2;

    @Getter
    @Setter
    String key3;

    public Record(){}

    public Record(String country, String key, String body){
        this.country = country;
        this.key = key;
        this.body = body;
    }

    public Record(String country, String key, String body, String profileKey, Integer rangeKey, String key2, String key3) {
        this.country = country;
        this.key = key;
        this.body = body;
        this.profileKey = profileKey;
        this.rangeKey = rangeKey;
        this.key2 = key2;
        this.key3 = key3;
    }

    private static <T> T mergeKeys(T a, T b){
        return b != null ? b : a;
    }

    public static Record merge(Record base, Record merged){
        String country = mergeKeys(base.getCountry(), merged.getCountry());
        String mergedKey = mergeKeys(base.getKey(), merged.getKey());
        String mergedBody = mergeKeys(base.getBody(), merged.getBody());
        String mergedProfileKey = mergeKeys(base.getProfileKey(), merged.getProfileKey());
        Integer mergedRangeKey = mergeKeys(base.getRangeKey(), merged.getRangeKey());
        String mergedKey2 = mergeKeys(base.getKey2(), merged.getKey2());
        String mergedKey3 = mergeKeys(base.getKey3(), merged.getKey3());

        return new Record(country, mergedKey, mergedBody, mergedProfileKey, mergedRangeKey, mergedKey2, mergedKey3);
    }

    /**
     * Get property value from json
     * @param jsonObject json object
     * @param property property name
     * @return property value
     */
    private static String getPropertyFromJson(JsonObject jsonObject, String property) {
        if (!jsonObject.has(property)) {
            return null;
        }
        return jsonObject.get(property).isJsonNull() ? null : jsonObject.get(property).getAsString();
    }

    /**
     * Create record object from json string
     * @param jsonString json string
     * @param mCrypto cryprto object
     * @return record objects with data from json
     * @throws GeneralSecurityException if decryption failed
     */
    public static Record fromString(String jsonString, Crypto mCrypto) throws GeneralSecurityException {
        JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);

        String key = getPropertyFromJson(jsonObject, P_KEY);
        String body = getPropertyFromJson(jsonObject, P_BODY);
        String profileKey = getPropertyFromJson(jsonObject, P_PROFILE_KEY);
        Integer rangeKey = getPropertyFromJson(jsonObject, P_RANGE_KEY) != null ? Integer.parseInt(getPropertyFromJson(jsonObject, P_RANGE_KEY)) : null;
        String key2 = getPropertyFromJson(jsonObject, P_KEY_2);
        String key3 = getPropertyFromJson(jsonObject, P_KEY_3);
        String version = getPropertyFromJson(jsonObject, VERSION);

        if (body != null && mCrypto != null){
            String[] parts = body.split(":");

            body = mCrypto.decrypt(body, version);

            if (parts.length != 2){
                key = mCrypto.decrypt(key, version);
                profileKey = mCrypto.decrypt(profileKey, version);
                key2 = mCrypto.decrypt(key2, version);
                key3 = mCrypto.decrypt(key3, version);
            } else {
                JsonObject bodyObj = new Gson().fromJson(body, JsonObject.class);
                body = getPropertyFromJson(bodyObj, P_PAYLOAD);
                String meta = getPropertyFromJson(bodyObj, P_META);
                JsonObject metaObj = new Gson().fromJson(meta, JsonObject.class);
                key = getPropertyFromJson(metaObj, P_KEY);
                profileKey = getPropertyFromJson(metaObj, P_PROFILE_KEY);
                key2 = getPropertyFromJson(metaObj, P_KEY_2);
                key3 = getPropertyFromJson(metaObj, P_KEY_3);
            }
        }
        return new Record(null, key, body, profileKey, rangeKey, key2, key3);
    }


    public String toString(Crypto mCrypto) throws GeneralSecurityException, IOException {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        if (mCrypto == null) {
            JsonElement recordJson = gson.toJsonTree(this);
            ((JsonObject) recordJson).remove(P_COUNTRY);
            return recordJson.toString();
        }

        JsonElement nodesElement = gson.toJsonTree(this);
        ((JsonObject) nodesElement).remove(P_COUNTRY);
        ((JsonObject) nodesElement).remove(P_BODY);

        Map<String, String> bodyJson = new HashMap<>();
        bodyJson.put(P_PAYLOAD, body);
        bodyJson.put(P_META, nodesElement.toString());
        String bodyJsonString = gson.toJson(bodyJson);

        Pair<String, Integer> encryptedBodyAndVersion = mCrypto.encrypt(bodyJsonString);

        JsonObject recordJson = new JsonObject();
        recordJson.addProperty(P_KEY, mCrypto.createKeyHash(key));
        recordJson.addProperty(P_KEY_2, mCrypto.createKeyHash(key2));
        recordJson.addProperty(P_KEY_3, mCrypto.createKeyHash(key3));
        recordJson.addProperty(P_PROFILE_KEY, mCrypto.createKeyHash(profileKey));
        recordJson.addProperty(P_RANGE_KEY, rangeKey);
        recordJson.addProperty(P_BODY, encryptedBodyAndVersion.getValue0());
        recordJson.addProperty(VERSION, encryptedBodyAndVersion.getValue1());
        return recordJson.toString();
    }
}