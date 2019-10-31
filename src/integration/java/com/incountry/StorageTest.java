package com.incountry;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class StorageTest {
    private Storage store;
    private String country = "US";
    private String recordKey = "some_key";
    private String profileKey = "profileKey";
    private String key2 = "key2";
    private String key3 = "key3";
    private String recordBody = "{\"name\":\"last\"}";


    @Before
    public void beforeTestMethod() throws Exception {
        this.store = new Storage();
    }

    @Test
    public void testWrite() throws GeneralSecurityException, IOException, Storage.StorageException {
        store.write(country, recordKey, recordBody, profileKey, null, key2, key3);
    }

    @Test
    public void testRead() throws GeneralSecurityException, IOException, Storage.StorageException {
        Data d = store.read(country, recordKey);
        assertEquals(recordKey, d.getKey());
        assertEquals(recordBody, d.getBody());
        assertEquals(profileKey, d.getProfile_key());
        assertEquals(key2, d.getKey2());
        assertEquals(key3, d.getKey3());
    }

    @Test
    public void testDelete() throws GeneralSecurityException, IOException, Storage.StorageException {
        String response = store.delete(country, recordKey);
        assertNotEquals(null, response);
        // Cannot read deleted record
        Data d = store.read(country, recordKey);
        assertEquals(null, d);
    }
}