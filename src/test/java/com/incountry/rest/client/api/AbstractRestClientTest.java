package com.incountry.rest.client.api;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.common.reflect.TypeToken;
import com.incountry.storage.dto.MidPopData;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class AbstractRestClientTest {

    @Test
    public void testGet() throws IOException {
        HttpTransport transportMock = new MockHttpTransport() {
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() throws IOException {
                        MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                        response.setStatusCode(200);
                        response.setContentType(Json.MEDIA_TYPE);
                        response.setContent("{\"country\":\"us\",\"profile_key\":\"pf-key\",\"body\":\"body\", " +
                                "\"range_key\":\"rk\", \"key\":\"key\", \"key2\":\"key2\",\"key3\":\"key3\"}");
                        return response;
                    }
                };
            }
        };

        AbstractRestClient testObj = new AbstractRestClient(transportMock) {
        };

        MidPopData data = testObj.getAndParse("https://us.api.incountry.io/v2/storage/records/us/201e5a2194145a780aeef762deea82aa",
                new TypeToken<MidPopData>() {
                }.getType(), null, null);

        Assert.assertTrue(MidPopData.builder().setCountry("us").setProfileKey("pf-key").setBody("body")
                .setKey("key").setKey2("key2").setKey3("key3").setRangeKey("rk").build().equals(data));
    }
}