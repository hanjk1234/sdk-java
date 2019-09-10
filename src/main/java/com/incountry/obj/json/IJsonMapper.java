package com.incountry.obj.json;

import com.incountry.obj.IObjectMapper;

public interface IJsonMapper<R, S> extends IObjectMapper<R, S> {
    R map(boolean pretty, S obj);

    R map(boolean pretty, boolean printClassName, S obj);


}
