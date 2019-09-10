package com.incountry.obj.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

public class JsonMapper extends AbstractJsonMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static JsonMapper serializer = new JsonMapper();

    private JsonMapper() {
    }

    public static JsonMapper getInstance() {
        return serializer;
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void cleanCache() {
        SerializerProvider serializerProvider = getObjectMapper().getSerializerProvider();
        if (serializerProvider instanceof DefaultSerializerProvider) {
            ((DefaultSerializerProvider) serializerProvider).flushCachedSerializers();
        }
    }


}
