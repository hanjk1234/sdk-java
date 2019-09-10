package com.incountry.obj;

public interface IObjectMapper<R, S> {
    R map(S obj);
}
