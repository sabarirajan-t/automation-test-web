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
import java.io.IOException;
import java.sql.*;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ExportStats {
    private static final OkHttpClient HTTP_CLIENT = RestClientWithoutCert.getUnsafeOkHttpClient();
    public static JSONObject sessionDetails;
    public static JSONObject hostDetails= InputHandler.getHostDetails();
    public static Connection dbconnection;
    public static Statement statement;
    public static ResultSet resultSet;
    public void doExport(String format) throws IOException {
        String request = "{\"inputParams\":{\"reportId\":59,\"businessHour\":0,\"endDate\":\"1651084199\",\"cvIdList\":[{\"cvID\":162,\"name\":\"Time+Based\",\"criteriaType\":\"2\"},{\"cvID\":163,\"name\":\"Count+Based\",\"criteriaType\":\"2\"},{\"cvID\":164,\"name\":\"Pattern+Based\",\"criteriaType\":\"-1\"}],\"reportCategoryType\":1,\"businessInputList\":[{\"id\":\"0\",\"value\":\"All\"},{\"id\":\"1\",\"value\":\"Business\"},{\"id\":\"2\",\"value\":\"Non-Business\"}],\"showMakeAsDefaultReport\":true,\"chartInputList\":[{\"name\":\"ueba.ReportGraphs.oneday_twoday\",\"value\":\"1;;;2\"},{\"name\":\"ueba.ReportGraphs.oneday_oneweek\",\"value\":\"1;;;7\"},{\"name\":\"ueba.ReportGraphs.oneweek_onemonth\",\"value\":\"7;;;30\"}],\"reportType\":\"report\",\"chosenServerValue\":\"Last+30+Days\",\"selectedUserServer\":\"0\",\"activeTabId\":\"tabId_162\",\"selectedHour\":0,\"isBusinessHourConfigured\":true,\"reportDetailsMap\":{\"REPORT_ENABLED\":true,\"REPORT_TYPE\":2,\"LOGIN_ID\":1,\"IS_ENABLED\":true,\"UEBA_REPORT_NAME_zh_CN\":\"登录\",\"PRIORITY\":1,\"UEBA_REPORT_ID\":59,\"REPORT_CATEGORY_TYPE\":1,\"UEBA_REPORT_NAME_ja_JP\":\"ログオン\",\"REPORT_CATEGORY_ID\":18,\"UEBA_REPORT_NAME\":\"Logons\",\"IS_PREDEFINED_REPORT\":true,\"TITLE\":\"Logons\",\"TILE_NAME\":\"report\",\"DISPLAY_TYPE\":\"BOTH\",\"UEBA_REPORT_NAME_en_US\":\"Logons\"},\"startDate\":\"1648492200\",\"isClusterModelAvailable\":true},\"tabInputParams\":{\"cvId\":164,\"tabId\":\"tabId_164\",\"tabName\":\"tabName_164\",\"defaultFlag\":false,\"displayName\":\"Pattern+Based\",\"criteriaType\":-1,\"totalCount\":50,\"tabIdh\":\"#tabId_164\",\"rangeList\":[{\"value\":\"25\"},{\"value\":\"50\"},{\"value\":\"75\"},{\"value\":\"100\"}],\"showAddOrRemove\":true,\"showFilter\":true,\"showAdvSearch\":true,\"showAdvSearchMethod\":\"advSearchAction\",\"startValue\":1,\"rangeValue\":25,\"showAllColFlag\":true,\"hideTopDivider\":true,\"showLoading\":false},\"exportType\":\"[EXPORT_FORMAT]\",\"chartType\":\"verticalbar3d\"}";
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

    public void storeDb(String uniqueID,String format,String queueTime,String exportTime, String totalTime) throws SQLException, ClassNotFoundException {
        dbconnection= DBHandler.connectDB();
        DatabaseMetaData dbm =dbconnection.getMetaData();
        statement=dbconnection.createStatement();
        resultSet = dbm.getTables(null,null, "EXPORT_STATS", null);
        if(!resultSet.next()){
            String createTable="CREATE TABLE EXPORT_STATS (\n" +
                    "    UniqueId varchar(255),\n" +
                    "    Format varchar(255),\n" +
                    "    QueueTime varchar(255),\n" +
                    "    ExportTime varchar(255),\n" +
                    "    TotalTime varchar(255),\n" +
                    ");";
            statement.executeUpdate(createTable);
        }
        else {
            String insertData="INSERT INTO EXPORT_STATS (UniqueId, Format, QueueTime, ExportTime, TotalTime)\n" +
                    "VALUES ('"+uniqueID+"','"+format+"', '"+queueTime+"', '"+exportTime+"', '"+totalTime+"')";
            statement.executeUpdate(insertData);
        }
    }

    public static void showData(String uniqueId) throws SQLException {
        String getData="Select * from EXPORT_STATS where UniqueId='"+uniqueId+"'";
        resultSet=statement.executeQuery(getData);
        System.out.println("\n*********************************************\n");
        System.out.println("Data from DB:");
        System.out.println("\n*********************************************\n");
        while(resultSet.next()){
            System.out.println("Queuing time for "+resultSet.getString("Format")+" format:"+resultSet.getString("QueueTime"));
            System.out.println("Queuing time for "+resultSet.getString("Format")+" format:"+resultSet.getString("ExportTime"));
            System.out.println("Queuing time for "+resultSet.getString("Format")+" format:"+resultSet.getString("TotalTime"));
            System.out.println("\n*********************************************\n");
        }
    }

    public static void calcStats() throws IOException, InterruptedException, SQLException, ClassNotFoundException {
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
            long exportEndTime=System.currentTimeMillis();
            long queueTime=queueEndTime-queueStartTime;
            long exportTime=exportEndTime-exportStartTime;
            long overallTimeTaken = exportEndTime-queueStartTime;
            System.out.println("Queuing time for "+formats[i]+" format:"+obj.formatTime(queueTime));
            System.out.println("Export time for "+formats[i]+" format:"+obj.formatTime(exportTime));
            System.out.println("Overall time for "+formats[i]+" format:"+obj.formatTime(overallTimeTaken));
            System.out.println("\n*********************************************\n");
            obj.clearHistory();
            obj.storeDb(uniqueID,formats[i],obj.formatTime(queueTime),obj.formatTime(exportTime),obj.formatTime(overallTimeTaken));
        }
        showData(uniqueID);
    }
}

