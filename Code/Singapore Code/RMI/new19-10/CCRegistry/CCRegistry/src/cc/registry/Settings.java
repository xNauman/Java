package cc.registry;

import java.net.*;

public class Settings {

    protected int port = 4444;
    protected InetAddress group;
    protected MulticastSocket socket;

    public Settings() throws Exception {
        group = InetAddress.getByName("230.0.0.1");
        socket = new MulticastSocket(port);
    }
}
