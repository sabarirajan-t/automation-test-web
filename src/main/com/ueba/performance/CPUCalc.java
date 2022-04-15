package com.ueba.performance;

import javax.management.*;
import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static javafx.application.Platform.exit;

public class CPUCalc {
    public static boolean flag=true;
    static Map<Long,Double> cpuMap=new HashMap<Long,Double>();
    public static HashMap<String, Double> calcCpu() throws IOException, InterruptedException, MalformedObjectNameException, ReflectionException, AttributeNotFoundException, InstanceNotFoundException, MBeanException {
        JMXConnector connector=JMXConnectionHandler.getConnector();
        while(flag){
            Object obj = connector.getMBeanServerConnection().getAttribute(new ObjectName("java.lang:type=OperatingSystem"), "ProcessCpuLoad");
            //System.out.printf("%.2f\n",(double)obj*100);
            cpuMap.putAll(FormatData.formatCpu((double)obj*100));
            Thread.sleep(1000);
        }
        System.out.println("Avg. CPU:"+cpuMap.values().stream().mapToDouble(d->d).average().orElse(0));
        System.out.println("Max CPU:"+Collections.max(cpuMap.values()));
        HashMap<String,Double> data=new HashMap<>();
        data.put("avg_cpu",cpuMap.values().stream().mapToDouble(d->d).average().orElse(0));
        data.put("max_cpu",Collections.max(cpuMap.values()));
        return data;
    }
}
