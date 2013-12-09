package cc.registry;

import java.net.*;

public class RegistrySettings {

    protected int port;
    protected InetAddress group;

    public RegistrySettings() throws Exception {

        port = 4444;
        group = InetAddress.getByName("230.0.0.1");
    }
}
