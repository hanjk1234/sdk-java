package com.incountry.obj.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonParser implements IJsonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static JsonParser parser = new JsonParser();

    public static JsonParser getInstance() {
        return parser;
    }

    protected static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    public <T> T readValue(String content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
        return getObjectMapper().readValue(content, valueType);
    }
}
