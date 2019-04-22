import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PostData {


    public String createNoOfBikesAndStations(int noOfStations, int noOfBikes){
        return  "{\n" + "\"opereational_stations\": "+noOfStations+",\r\n" +
                "    \"total_bikes\": "+noOfBikes+"\r\n }" ;
    }

    public String createNearest(JSONObject nearest){
        double latitude = Double.parseDouble(nearest.get("latitude").toString());
        double longitude = Double.parseDouble(nearest.get("longitude").toString());
        String name = nearest.get("name").toString();
        int noOfBakes = Integer.parseInt(nearest.get("free_bikes").toString());
        return  "{\n" + "\"name\": "+name+",\r\n" +
                "    \"free_bikes\": "+noOfBakes+ ",\r\n" + "\"latitude\": " + latitude + ",\r\n" + "\"longitude\": " + longitude  +"\r\n }" ;
    }


    public void post(String POST_PARAMS){

       try {
           URL obj = new URL("http://localhost:8080/post");
           HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
           postConnection.setRequestMethod("POST");
           postConnection.setRequestProperty("Content-Type", "application/json");
           postConnection.setDoOutput(true);
           OutputStream os = postConnection.getOutputStream();
           os.write(POST_PARAMS.getBytes());
           os.flush();
           os.close();
           int responseCode = postConnection.getResponseCode();
           System.out.println("POST Response Code :  " + responseCode);
           System.out.println("POST Response Message : " + postConnection.getResponseMessage());
           if (responseCode == HttpURLConnection.HTTP_CREATED) {
               BufferedReader in = new BufferedReader(new InputStreamReader(
                       postConnection.getInputStream()));
               String inputLine;
               StringBuffer response = new StringBuffer();
               while ((inputLine = in .readLine()) != null) {
                   response.append(inputLine);
               } in .close();
               System.out.println(response.toString());
           } else {
               System.out.println("POST NOT WORKED");
           }
       }catch (MalformedURLException e){
           e.printStackTrace();
       }catch (IOException e){
           e.printStackTrace();
       }


    }


}

