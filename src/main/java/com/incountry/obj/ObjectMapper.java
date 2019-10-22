package com.incountry.obj;

public interface ObjectMapper<R, S> {
    R map(S obj);
}
