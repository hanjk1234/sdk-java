package com.incountry.rest.client.api;

import com.google.api.client.http.*;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class AbstractRestClient implements Closeable {
    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private HttpTransport httpTransport;
    private HttpRequestFactory requestFactory;


    public AbstractRestClient() {
        this(new ApacheHttpTransport());
    }

    public AbstractRestClient(HttpTransport httpTransport) {
        this.httpTransport = httpTransport;
        requestFactory = httpTransport.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) {
                request.setParser(new JsonObjectParser(JSON_FACTORY));
            }
        });
    }

    @Override
    public void close() throws IOException {
        httpTransport.shutdown();
    }

    protected <T> T getAndParse(String url, Type type, Map<String, String> headers, Map<String, String> params) throws IOException {
        HttpRequest request = requestFactory.buildGetRequest(assembleUrl(url, params));
        updateRequestWithHeaders(request, headers);
        request.execute();
        return (T) request.execute().parseAs(type);
    }

    protected <T> T postAndParse(String url, Type type, Map<String, String> headers, Map<String, String> params, Object dto) throws IOException {
        HttpRequest request = requestFactory.buildPostRequest(assembleUrl(url, params), new JsonHttpContent(JSON_FACTORY, dto));
        return (T) request.execute().parseAs(type);
    }

    protected void updateRequestWithHeaders(HttpRequest request, Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) return;
        headers.forEach((k, v) -> request.getHeaders().set(k, v));
    }

    protected GenericUrl assembleUrl(String url, Map<String, String> params) {
        GenericUrl gUrl = new GenericUrl(url);
        if (params != null && !params.isEmpty()) {
            params.forEach((k, v) -> gUrl.put(k, v));
        }
        return gUrl;
    }

}
