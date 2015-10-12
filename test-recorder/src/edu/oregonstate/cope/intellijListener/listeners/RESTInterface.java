package edu.oregonstate.cope.intellijListener.listeners;


import edu.oregonstate.cope.settings.CopeGlobalSettings;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

import java.io.*;

/**
 * Created by michaelhilton on 9/29/15.
 */
public class RESTInterface {

    public static String testConnection(String url) throws IOException {
        HttpPost httppost = new HttpPost(url);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            JSONObject jsonObject = TestListener.buildCommonJSONObj(Events.testConnection);
            StringEntity input = new StringEntity(jsonObject.toString());
            input.setContentType("application/json");
            httppost.setEntity(input);
            try (CloseableHttpResponse response = httpClient.execute(httppost)) {
                return response.getStatusLine().toString();
            }
        }
    }

    public static void RESTcall(JSONObject testJSON){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String userName = CopeGlobalSettings.getInstance().getUserId();
            String url = CopeGlobalSettings.getInstance().getUrl();
            HttpPost httppost = new HttpPost(url);

            testJSON.put("username",userName);

            StringEntity input = new StringEntity(testJSON.toString());
            input.setContentType("application/json");
            httppost.setEntity(input);

            System.out.println("Executing request: " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
