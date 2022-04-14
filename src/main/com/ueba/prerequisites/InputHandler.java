package com.ueba.prerequisites;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class InputHandler{

    FileInputStream fileInputStream=new FileInputStream("resources/Inputs.properties");
    public static Properties props=new Properties();

    public InputHandler() throws IOException {
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

    public static JSONObject getJMXDetails(){
        JSONObject jmxDetails=new JSONObject();
        jmxDetails.put("jmxHost",props.getProperty("jmxhost"));
        jmxDetails.put("jmxPort",props.getProperty("jmxport"));
        return jmxDetails;
    }

}

