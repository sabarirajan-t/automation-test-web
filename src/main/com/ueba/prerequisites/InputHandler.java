package com.ueba.prerequisites;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class InputHandler{


    public static Properties props=new Properties();

    public InputHandler() throws FileNotFoundException {
    }

    public static void loadData() throws IOException {
        FileInputStream fileInputStream=new FileInputStream("C:\\Users\\sabari-11074\\IdeaProjects\\automation-test-web\\resources\\Inputs.properties");
        props.load(fileInputStream);
    }

    public static JSONObject getHostDetails(){
        JSONObject hostDetails=new JSONObject();
        hostDetails.put("baseUrl",props.getProperty("baseUrl"));
        return hostDetails;
    }

    public static JSONObject getUserCreds(){
        JSONObject userCreds=new JSONObject();
        userCreds.put("username",props.getProperty("username"));
        userCreds.put("password",props.getProperty("password"));
        userCreds.put("domainName",props.getProperty("domainName"));
        return userCreds;
    }

    public static JSONObject getDbDetails(){
        JSONObject dbDetails=new JSONObject();
        dbDetails.put("dbserver",props.getProperty("dbserver"));
        dbDetails.put("dbname",props.getProperty("dbname"));
        dbDetails.put("dbusername",props.getProperty("dbusername"));
        dbDetails.put("dbpassword",props.getProperty("dbpassword"));
        return dbDetails;
    }

    public static JSONObject getJMXDetails() throws IOException {
        loadData();
        JSONObject jmxDetails=new JSONObject();
        jmxDetails.put("jmxhost",props.getProperty("jmxhost"));
        jmxDetails.put("jmxport_ueba",props.getProperty("jmxport_ueba"));
        jmxDetails.put("jmxport_es",props.getProperty("jmxport_es"));
        return jmxDetails;
    }

}

