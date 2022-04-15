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
    static Map<Long,Long> memoryMap=new HashMap<Long,Long>();
    public static HashMap calcMemory() throws IOException, MalformedObjectNameException, ReflectionException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, InterruptedException {
        JMXConnector connector=JMXConnectionHandler.getConnector();
        while(flag){
            Object obj = connector.getMBeanServerConnection().getAttribute(new ObjectName("java.lang:type=Memory"), "HeapMemoryUsage");
            CompositeData cd = (CompositeData) obj;
            //System.out.println((long)cd.get("used")/(1024*1024));
            memoryMap.putAll(FormatData.formatMemory((long)cd.get("used")/(1024*1024)));
            Thread.sleep(1000);
        }
        System.out.println("Avg. Memory:"+Double.valueOf(memoryMap.values().stream().mapToDouble(d->d).average().orElse(0)));
        System.out.println("Max Memory:"+ Collections.max(memoryMap.values()));
        HashMap<String,Double> data=new HashMap<>();
        data.put("avg_memory",Double.valueOf(memoryMap.values().stream().mapToDouble(d->d).average().orElse(0)));
        data.put("max_meory",(double)Collections.max(memoryMap.values()));
        return data;
    }
}
