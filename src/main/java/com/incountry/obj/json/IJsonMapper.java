package com.incountry.obj.json;

import com.incountry.obj.ObjectMapper;

public interface IJsonMapper<R, S> extends ObjectMapper<R, S> {
    R map(boolean pretty, S obj);

    R map(boolean pretty, boolean printClassName, S obj);


}
