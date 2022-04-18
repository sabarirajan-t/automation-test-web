package com.ueba.performance;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ueba.prerequisites.InputHandler;
import org.json.JSONObject;

import javax.servlet.ServletException;
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

//    JSONObject jmxDetails=InputHandler.getJMXDetails();
//    String jmxhost=jmxDetails.getString("jmxHost");
//    String jmxport_ueba=jmxDetails.getString("jmxPortUeba");
//    String jmxport_es=jmxDetails.getString("jmxPortEs");


    HashMap<String,Double> map=new HashMap<>();
    HashMap<String,Double> map1=new HashMap<>();
    int maxTime;
    int seconds;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("param").toString();
        try{
            if(action.equals("tostop")){
                seconds=maxTime;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //create a callable for each method
        //String data[];
        String action=req.getServletPath();
        try {
            if(action.equals("/start")){
                Callable<Void> callable1 = () -> {
                    map=CPUCalc.calcCpu();
//                    System.out.println(map);
//                    String newStr=new Gson().toJson(map);
//                    System.out.println(newStr);
//                    resp.setContentType("application/json");
//                    resp.setCharacterEncoding("UTF-8");
//                    resp.getWriter().write(newStr);
                    return null;
                };

                Callable<Void> callable2 = () -> {
                    //HashMap test= (HashMap) MemoryCalc.calcMemory();
                    //String json = new Gson().toJson(test);
//                    String test= String.valueOf(MemoryCalc.calcMemory());
                    map1=MemoryCalc.calcMemory();
//                    System.out.println(map1);
//                    String newStr=new Gson().toJson(map1);
//                    System.out.println(newStr);
//                    resp.setContentType("application/json");
//                    resp.setCharacterEncoding("UTF-8");
//                    resp.getWriter().write(newStr);
                    return null;
                };

                Callable<Void> callable3 = () -> {
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

//                Callable<Void> callable4 = () -> {
//
//                    map=CPUCalc.calcCpu();
//                    return null;
//                };

                //add to a list
                List<Callable<Void>> taskList = new ArrayList<Callable<Void>>();
                taskList.add(callable1);
                taskList.add(callable2);
                taskList.add(callable3);
//                taskList.add(callable4);

                //create a pool executor with 3 threads
                ExecutorService executor = Executors.newFixedThreadPool(3);

                try
                {
                    //start the threads and wait for them to finish
                    executor.invokeAll(taskList);
                    map.putAll(map1);
                    System.out.println(map);
                    String newStr=new Gson().toJson(map);
                    System.out.println(newStr);
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(newStr);
                    //System.exit(0);
                }
                catch (InterruptedException ie)
                {
                    //do something if you care about interruption;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
