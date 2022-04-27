package com.ueba.performance;

import javax.management.*;
import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CPUCalc {
    public static boolean flag=true;
    static Map<Long,Double> cpuMapUeba=new HashMap<Long,Double>();
    static Map<Long,Double> cpuMapEs=new HashMap<Long,Double>();
    public static Map<Long, Double> calcCpuUeba(JMXConnector connector) throws IOException, InterruptedException, MalformedObjectNameException, ReflectionException, AttributeNotFoundException, InstanceNotFoundException, MBeanException {
        while(flag){
            Object obj = connector.getMBeanServerConnection().getAttribute(new ObjectName("java.lang:type=OperatingSystem"), "ProcessCpuLoad");
            cpuMapUeba.putAll(FormatData.formatCpu((double)obj*100));
            Thread.sleep(1000);
        }
        return cpuMapUeba;
    }

    public static Map<Long, Double> calcCpuEs(JMXConnector connector) throws IOException, InterruptedException, MalformedObjectNameException, ReflectionException, AttributeNotFoundException, InstanceNotFoundException, MBeanException {
        while(flag){
            Object obj = connector.getMBeanServerConnection().getAttribute(new ObjectName("java.lang:type=OperatingSystem"), "ProcessCpuLoad");
            cpuMapEs.putAll(FormatData.formatCpu((double)obj*100));
            Thread.sleep(1000);
        }
        return cpuMapEs;
    }
}
