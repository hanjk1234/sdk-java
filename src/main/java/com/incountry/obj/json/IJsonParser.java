package com.incountry.obj.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;

public interface IJsonParser {
    @SuppressWarnings("squid:RedundantThrowsDeclarationCheck")
    <T> T readValue(String content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException;
}
