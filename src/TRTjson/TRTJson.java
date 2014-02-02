package TRTjson;

import org.json.*;
import java.net.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;

/**
 *
 * @author michele
 */
public class TRTJson {

    private double currBid;
    private double currAsk;

    public void updateAskAndBid() {
        String line, result = "";

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("https://www.therocktrading.com/api/ticker/BTCEUR").openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                while ((line = rd.readLine()) != null) {
                    result += line;
                }
            }

            JSONObject obj = new JSONObject(result);
            JSONArray array = (JSONArray) obj.getJSONArray("result");

            obj = array.getJSONObject(0);
            currAsk = Double.parseDouble(obj.getString("ask"));
            currBid = Double.parseDouble(obj.getString("bid"));

        } catch (MalformedURLException e) {
        } catch (IOException | JSONException e) {
        }
    }

    /**
     * @return the currBid
     */
    public double getCurrBid() {
        return currBid;
    }

    /**
     * @return the currAsk
     */
    public double getCurrAsk() {
        return currAsk;
    }
    
    /**
     * Creates a sample dataset.
     *
     * @return a sample dataset.
     */
    public static JSONArray getJSONArrayDataset(int days) {

        int seconds=((days*24)*60)*60;
        int currTime=(int) (System.currentTimeMillis() / 1000L);
        int requestedTime=currTime-seconds;
        
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("https://www.therocktrading.com/api/trades/BTCEUR?since="+requestedTime).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            String result = "";
            String line;
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {

                while ((line = rd.readLine()) != null) {
                    result += line;
                }
            }
            return new JSONArray(result);


        } catch (MalformedURLException e) {
        } catch (IOException | JSONException e) {
        }

        return null;
    }
}