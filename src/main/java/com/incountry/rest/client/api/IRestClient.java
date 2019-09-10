package com.incountry.rest.client.api;

import java.io.Closeable;

public interface IRestClient extends Closeable {
    IMidPopClient getMidPopClient();
}
