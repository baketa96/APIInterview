import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetData {

    private URL url;
    HttpURLConnection conn;
    private  String readLine = null;
    public GetData(){
            try {
                url = new URL("https://api.citybik.es/v2/networks/velib?fields=stations");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public JSONArray getData(){
        try {
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode!=HttpURLConnection.HTTP_OK){
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }else{
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );
                StringBuffer response = new StringBuffer();
                while((readLine=bufferedReader.readLine())!=null){
                    response.append(readLine);
                }
                bufferedReader.close();
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(response.toString());
                JSONObject networkObject = (JSONObject) jsonObject.get("network");
                JSONArray jsonArray = (JSONArray) networkObject.get("stations");


                return jsonArray;
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }

        return null;
    }




}
