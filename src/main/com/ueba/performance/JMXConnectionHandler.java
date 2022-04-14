package com.ueba.performance;

import com.ueba.prerequisites.InputHandler;
import netscape.javascript.JSObject;
import org.json.JSONObject;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.io.InputStream;

public class JMXConnectionHandler {
    public static JMXConnector getConnector() throws IOException {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://172.24.138.15:9010/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(url);
        return connector;
    }
}
