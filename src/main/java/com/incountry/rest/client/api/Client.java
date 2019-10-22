package com.incountry.rest.client.api;

import java.io.Closeable;

public interface Client extends Closeable {
    MidPopClient getMidPopClient();
    PortalClient getPortalClient();
}
