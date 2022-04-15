//package com.ueba.scripts;
//
//import com.ueba.db.DBHandler;
//import com.ueba.login.LoginHandler;
//import com.ueba.performance.CPUCalc;
//import com.ueba.performance.MemoryCalc;
//import com.ueba.performance.StartPerformance;
//import com.ueba.prerequisites.InputHandler;
//import org.json.JSONObject;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.TimerTask;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//@WebServlet("/")
//public class MainClass extends HttpServlet {
////    public static void main(String args[]) throws Exception {
//////        InputHandler inputHandler = new InputHandler();
//////        LoginHandler loginHandler = new LoginHandler();
//////        DBHandler dbHandler = new DBHandler();
//////        ExportStats exportStats = new ExportStats();
//////        exportStats.calcStats();
////        CPUCalc cpuCalc=new CPUCalc();
////        MemoryCalc memoryCalc=new MemoryCalc();
////        //cpuCalc.calcCpu();
////        //memoryCalc.calcMemory();
////        StartPerformance startPerformance=new StartPerformance();
////        startPerformance.testThread();
////    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String action=req.getServletPath();
//        try {
//            switch (action){
//                case "/exportstat":
//                    StartPerformance startPerformance=new StartPerformance();
//                    startPerformance.testThread();
//                    PrintWriter out=resp.getWriter();
//                    out.println();
//                    break;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
