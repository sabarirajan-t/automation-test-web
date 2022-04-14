package com.ueba.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.application.Platform.exit;

public class StartPerformance {


    public void testThread()
    {

        //create a callable for each method
        Callable<Void> callable1 = () -> {
            CPUCalc.calcCpu();
            return null;
        };

        Callable<Void> callable2 = () -> {
            MemoryCalc.calcMemory();
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
            System.exit(0);
        }
        catch (InterruptedException ie)
        {
            //do something if you care about interruption;
        }

    }
}
