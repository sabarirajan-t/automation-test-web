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
    static String host="";
    static String port="";
    public static JMXConnector getConnector() throws IOException {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"+host+":"+port+"/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(url);
        return connector;
    }
}
