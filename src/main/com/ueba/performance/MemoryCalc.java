package com.ueba.performance;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MemoryCalc {
    public static boolean flag=true;
    static Map<Long,Long> memoryMapUeba=new HashMap<Long,Long>();
    static Map<Long,Long> memoryMapEs=new HashMap<Long,Long>();
    public static Map<Long, Long> calcMemoryUeba(JMXConnector connector) throws IOException, MalformedObjectNameException, ReflectionException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, InterruptedException {
        while(flag){
            Object obj = connector.getMBeanServerConnection().getAttribute(new ObjectName("java.lang:type=Memory"), "HeapMemoryUsage");
            CompositeData cd = (CompositeData) obj;
            memoryMapUeba.putAll(FormatData.formatMemory((long)cd.get("used")/(1024*1024)));
            Thread.sleep(3000);
        }
        return memoryMapUeba;
    }

    public static Map<Long, Long> calcMemoryEs(JMXConnector connector) throws IOException, MalformedObjectNameException, ReflectionException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, InterruptedException {
        while(flag){
            Object obj = connector.getMBeanServerConnection().getAttribute(new ObjectName("java.lang:type=Memory"), "HeapMemoryUsage");
            CompositeData cd = (CompositeData) obj;
            memoryMapEs.putAll(FormatData.formatMemory((long)cd.get("used")/(1024*1024)));
            Thread.sleep(3000);
        }
        return memoryMapEs;
    }
}
