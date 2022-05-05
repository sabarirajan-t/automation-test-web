package com.ueba.performance;

import com.google.gson.Gson;
import com.ueba.prerequisites.InputHandler;
import com.ueba.scripts.ExportStats;
import org.json.JSONObject;

import javax.management.remote.JMXConnector;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet("/")
public class StartPerformance extends HttpServlet {
    Map<Long, Double> uebaCpuMap=new HashMap<>();
    Map<Long, Long> uebaMemoryMap=new HashMap<>();
    Map<Long, Double> esCpuMap=new HashMap<>();
    Map<Long, Long> esMemoryMap=new HashMap<>();
    HashMap<String,String> data=new HashMap<>();
    int maxTime;
    int seconds;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String action= req.getParameter("param");
        String date=req.getParameter("date");
        String buildNumber=req.getParameter("buildNo");
        String validationName=req.getParameter("valName");
        String desc=req.getParameter("description");
        System.out.println(date+buildNumber+validationName+desc);

        try{
            if(action.equals("tostop")){
                seconds=maxTime;
            } else if (action.equals("export")) {
                InputHandler.loadData();
                List list=ExportStats.calcStats(req);
                System.out.println(list);
                String json = new Gson().toJson(list);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action=req.getServletPath();
        try {
            if(action.equals("/start")){
                InputHandler.loadData();
                JSONObject jmxData= InputHandler.getJMXDetails();
                JMXConnectionHandler.host=jmxData.getString("jmxhost");
                JMXConnectionHandler.port=jmxData.getString("jmxport_ueba");
                System.out.println(JMXConnectionHandler.host);
                System.out.println(JMXConnectionHandler.port);
                JMXConnector connector=JMXConnectionHandler.getConnector();
                JMXConnectionHandler.port=jmxData.getString("jmxport_es");
                System.out.println(JMXConnectionHandler.port);
                JMXConnector connector1=JMXConnectionHandler.getConnector();

                Callable<Void> callable1 = () -> {
                    uebaCpuMap=CPUCalc.calcCpuUeba(connector);
                    System.out.println("Avg. CPU:"+Double.valueOf(uebaCpuMap.values().stream().mapToDouble(d->d).average().orElse(0)));
                    System.out.println("Max CPU:"+ Collections.max(uebaCpuMap.values()));
                    System.out.println("Min CPU:"+ Collections.min(uebaCpuMap.values()));
                    data.put("avg_cpu_ueba", String.valueOf(Double.valueOf(uebaCpuMap.values().stream().mapToDouble(d->d).average().orElse(0))));
                    data.put("max_cpu_ueba", String.valueOf(Collections.max(uebaCpuMap.values())));
                    data.put("min_cpu_ueba", String.valueOf(Collections.min(uebaCpuMap.values())));
                    return null;
                };

                Callable<Void> callable2 = () -> {
                    uebaMemoryMap=MemoryCalc.calcMemoryUeba(connector);
                    System.out.println("Avg. Memory:"+Double.valueOf(uebaMemoryMap.values().stream().mapToDouble(d->d).average().orElse(0)));
                    System.out.println("Max Memory:"+ Collections.max(uebaMemoryMap.values()));
                    System.out.println("Min Memory:"+ Collections.min(uebaMemoryMap.values()));
                    data.put("avg_memory_ueba", String.valueOf(uebaMemoryMap.values().stream().mapToDouble(d -> d).average().orElse(0)));
                    data.put("max_memory_ueba", String.valueOf(Collections.max(uebaMemoryMap.values())));
                    data.put("min_memory_ueba", String.valueOf(Collections.min(uebaMemoryMap.values())));
                    return null;
                };

                Callable<Void> callable3 = () -> {
                    esCpuMap=CPUCalc.calcCpuEs(connector1);
                    System.out.println("Avg. CPU:"+Double.valueOf(esCpuMap.values().stream().mapToDouble(d->d).average().orElse(0)));
                    System.out.println("Max CPU:"+ Collections.max(esCpuMap.values()));
                    System.out.println("Min CPU:"+ Collections.min(esCpuMap.values()));
                    data.put("avg_cpu_es", String.valueOf(esCpuMap.values().stream().mapToDouble(d -> d).average().orElse(0)));
                    data.put("max_cpu_es", String.valueOf(Collections.max(esCpuMap.values())));
                    data.put("min_cpu_es", String.valueOf(Collections.min(esCpuMap.values())));
                    return null;
                };

                Callable<Void> callable4 = () -> {
                    esMemoryMap=MemoryCalc.calcMemoryEs(connector1);
                    System.out.println("Avg. Memory:"+Double.valueOf(esMemoryMap.values().stream().mapToDouble(d->d).average().orElse(0)));
                    System.out.println("Max Memory:"+ Collections.max(esMemoryMap.values()));
                    System.out.println("Min Memory:"+ Collections.min(esMemoryMap.values()));
                    data.put("avg_memory_es", String.valueOf(esMemoryMap.values().stream().mapToDouble(d -> d).average().orElse(0)));
                    data.put("max_memory_es", String.valueOf(Collections.max(esMemoryMap.values())));
                    data.put("min_memory_es", String.valueOf(Collections.min(esMemoryMap.values())));
                    return null;
                };

                Callable<Void> callable5 = () -> {
                    maxTime=Integer.MAX_VALUE;
                    seconds=0;
                    while (seconds<maxTime){
                        seconds++;
                        System.out.println(seconds);
                        Thread.sleep(1000);
                    }
                    CPUCalc.flag=false;
                    MemoryCalc.flag=false;
                    return null;
                };

                List<Callable<Void>> taskList = new ArrayList<>();
                taskList.add(callable1);
                taskList.add(callable2);
                taskList.add(callable3);
                taskList.add(callable4);
                taskList.add(callable5);

                ExecutorService executor = Executors.newFixedThreadPool(5);

                try
                {
                    executor.invokeAll(taskList);
                    System.out.println(uebaCpuMap);
                    System.out.println(uebaMemoryMap);
                    System.out.println(esCpuMap);
                    System.out.println(esMemoryMap);
                    System.out.println(data);
                    String newStr=new Gson().toJson(data);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(newStr);
                }
                catch (InterruptedException ie)
                {
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
