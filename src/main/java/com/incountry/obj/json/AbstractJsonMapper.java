package com.incountry.obj.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractJsonMapper implements IJsonMapper<String, Object> {
    protected abstract ObjectMapper getObjectMapper();

    @Override
    public String map(boolean pretty, boolean printClassName, Object obj) {
        if (obj == null) {
            return "null";
        }
        try {
            return (printClassName ? (obj.getClass().getSimpleName() + ":") : "") + (pretty ?
                    getObjectMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(obj) :
                    getObjectMapper().writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            return obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode());
        }
    }

    @Override
    public String map(boolean pretty, Object obj) {
        return map(pretty, false, obj);
    }

    @Override
    public String map(Object obj) {
        return map(false, false, obj);
    }


}
