package com.ueba.scripts;

import com.ueba.db.DBHandler;
import com.ueba.login.LoginHandler;
import com.ueba.prerequisites.InputHandler;
import com.ueba.restapi.RequestHandler;
import com.ueba.restapi.RestClientWithoutCert;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ExportStats {
    private static final OkHttpClient HTTP_CLIENT = RestClientWithoutCert.getUnsafeOkHttpClient();
    public static JSONObject sessionDetails;
    public static JSONObject hostDetails= InputHandler.getHostDetails();
    public static Connection dbconnection;
    public static Statement statement;
    public static ResultSet resultSet;
    public void doExport(String format) throws IOException {
        String request = "{\"SORTTYPE\":1,\"OPERATION\":\"EXPORT\",\"START_TIME\":\"2022-04-05T18:30:00.000Z\",\"END_TIME\":\"2022-05-05T18:29:59.000Z\",\"TABLE_NAME\":\"UEBA_VIEW_ALERTS\",\"filterData\":[],\"widgetData\":[],\"CUSTOM_STRUCTURE\":{\"REQUEST_PARAMS\":{\"USER_ID\":1},\"UNIQUE_ID\":\"UEBA_VIEW_ALERTS_1\"},\"isAlert\":true,\"chosenServerValue\":\"Last+30+Days\",\"exportType\":\"[EXPORT_FORMAT]\"}";
        request = request.replace("[EXPORT_FORMAT]", format);
        String url = ""+hostDetails.getString("baseUrl")+"/RestAPI/WC/Export?mTCall=exportData";
        RequestBody formData = new FormBody.Builder()
                .add("req", request)
                .add("ueba_csrf",sessionDetails.getString("csrf"))
                .build();
        RequestHandler.post(formData,url,sessionDetails.getString("cookie"));
    }

    public String exportHistory() throws IOException {
        String url = ""+hostDetails.getString("baseUrl")+"/RestAPI/WC/Export?mTCall=exportHistory&LIMIT=5";
        String responseString=RequestHandler.get(url,sessionDetails.getString("cookie"));
        return responseString;
    }

    public void clearHistory() throws IOException {
        String request = "{\"OPERATION\":\"CLEAR_ALL\"}";
        String url = ""+hostDetails.getString("baseUrl")+"/emberAPI/exportAction";
        RequestBody formData = new FormBody.Builder()
                .add("req", request)
                .add("ueba_csrf",sessionDetails.getString("csrf"))
                .build();
        RequestHandler.post(formData,url,sessionDetails.getString("cookie"));
    }

    public String formatTime(long millis) {
        String timeFormatted = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return timeFormatted;
    }

    public void storeDb(String uniqueID,String format,String queueTime,String exportTime, String totalTime,int entriesCount,HttpServletRequest req) throws SQLException, ClassNotFoundException {
        String date=req.getParameter("date");
        String buildNumber=req.getParameter("buildNo");
        String validationName=req.getParameter("valName");
        String desc=req.getParameter("description");
        dbconnection= DBHandler.connectDB();
        DatabaseMetaData dbm =dbconnection.getMetaData();
        statement=dbconnection.createStatement();
        resultSet = dbm.getTables(null,null, "EXPORT_STATS", null);
        if(!resultSet.next()){
            String createTable="CREATE TABLE EXPORT_STATS (\n" +
                    "    UniqueId varchar(255),\n" +
                    "    Date varchar(255),\n" +
                    "    BuildNumber varchar(255),\n" +
                    "    ValidationName varchar(255),\n" +
                    "    Description varchar(255),\n" +
                    "    Format varchar(255),\n" +
                    "    QueueTime varchar(255),\n" +
                    "    ExportTime varchar(255),\n" +
                    "    TotalTime varchar(255),\n" +
                    "    EntriesCount varchar(255),\n" +
                    ");";
            statement.executeUpdate(createTable);
        }
        else {
            String insertData="INSERT INTO EXPORT_STATS (UniqueId, Date, BuildNumber, ValidationName, Description, Format, QueueTime, ExportTime, TotalTime, EntriesCount)\n" +
                    "VALUES ('"+uniqueID+"','"+date+"','"+buildNumber+"','"+validationName+"','"+desc+"','"+format+"', '"+queueTime+"', '"+exportTime+"', '"+totalTime+"','"+entriesCount+"')";
            statement.executeUpdate(insertData);
        }
    }

    public static List<Map<String, Object>> showData(String uniqueId) throws SQLException {
        String getData="Select * from EXPORT_STATS where UniqueId='"+uniqueId+"'";
        resultSet=statement.executeQuery(getData);
        System.out.println("\n*********************************************\n");
        System.out.println("Data from DB:");
        System.out.println("\n*********************************************\n");
        ResultSetMetaData md = resultSet.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, Object>> list = new ArrayList<>();
        while(resultSet.next()){
            Map<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), resultSet.getObject(i));
            }
            list.add(row);
            System.out.println("Queuing time for "+resultSet.getString("Format")+" format:"+resultSet.getString("QueueTime"));
            System.out.println("Queuing time for "+resultSet.getString("Format")+" format:"+resultSet.getString("ExportTime"));
            System.out.println("Queuing time for "+resultSet.getString("Format")+" format:"+resultSet.getString("TotalTime"));
            System.out.println("\n*********************************************\n");
        }
        System.out.println(list);
        return list;
    }

    public static List calcStats(HttpServletRequest request) throws IOException, InterruptedException, SQLException, ClassNotFoundException {
        sessionDetails=LoginHandler.getSessionDetails();
        String[] formats={"CSV","PDF","XLS","HTML"};
        String uniqueID = UUID.randomUUID().toString();
        ExportStats obj=new ExportStats();
        for(int i=0;i<formats.length;i++){
            obj.clearHistory();
            obj.doExport(formats[i]);
            long queueStartTime=System.currentTimeMillis();
            String responseData=obj.exportHistory();
            while(!responseData.contains("\"IS_STARTED\":true")){
                responseData=obj.exportHistory();
                Thread.sleep(1000);
            }
            long queueEndTime=System.currentTimeMillis();
            long exportStartTime=System.currentTimeMillis();
            while(!responseData.contains("\"completedHistoryStatus\":true")){
                responseData=obj.exportHistory();
                Thread.sleep(1000);
            }
            System.out.println(responseData);
            JSONObject jsonObject=new JSONObject(responseData);
            System.out.println(jsonObject);
            int entriesCount=(Integer) jsonObject.getJSONArray("exportedHistory").getJSONObject(0).get("EXPORTED_ENTRIES");
            long exportEndTime=System.currentTimeMillis();
            long queueTime=queueEndTime-queueStartTime;
            long exportTime=exportEndTime-exportStartTime;
            long overallTimeTaken = exportEndTime-queueStartTime;
            System.out.println("Queuing time for "+formats[i]+" format:"+obj.formatTime(queueTime));
            System.out.println("Export time for "+formats[i]+" format:"+obj.formatTime(exportTime));
            System.out.println("Overall time for "+formats[i]+" format:"+obj.formatTime(overallTimeTaken));
            System.out.println("\n*********************************************\n");
            obj.clearHistory();
            obj.storeDb(uniqueID,formats[i],obj.formatTime(queueTime),obj.formatTime(exportTime),obj.formatTime(overallTimeTaken),entriesCount,request);
        }
        List l=showData(uniqueID);
        return l;
    }
}

