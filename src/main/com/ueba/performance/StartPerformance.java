package com.ueba.performance;

import com.google.gson.Gson;

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

import static javafx.application.Platform.exit;
@WebServlet("/")
public class StartPerformance extends HttpServlet {
    HashMap<String,Double> map=new HashMap<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //create a callable for each method
        //String data[];
        String action=req.getServletPath();
        try {
            if(action.equals("/test")){
                Callable<Void> callable1 = () -> {
                    map=CPUCalc.calcCpu();
                    System.out.println(map);
                    resp.setContentType("text/plain");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(map.toString());
                    return null;
                };

                Callable<Void> callable2 = () -> {
                    //HashMap test= (HashMap) MemoryCalc.calcMemory();
                    //String json = new Gson().toJson(test);
//                    String test= String.valueOf(MemoryCalc.calcMemory());
                    map=MemoryCalc.calcMemory();
                    System.out.println(map);
                    resp.setContentType("text/plain");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(map.toString());
                    return null;
                };

                Callable<Void> callable3 = () -> {
                    int maxTime=10;
                    int seconds=0;
                    while (seconds<maxTime){
                        seconds++;
                        System.out.println(seconds);
                        Thread.sleep(1000);
                    }
                    CPUCalc.flag=false;
                    MemoryCalc.flag=false;
                    return null;
                };


                //add to a list
                List<Callable<Void>> taskList = new ArrayList<Callable<Void>>();
                taskList.add(callable1);
                taskList.add(callable2);
                taskList.add(callable3);

                //create a pool executor with 3 threads
                ExecutorService executor = Executors.newFixedThreadPool(3);

                try
                {
                    //start the threads and wait for them to finish
                    executor.invokeAll(taskList);
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

    public void testThread()
    {

    }
}
