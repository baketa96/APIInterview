package com.AmazonTestAPI.SimpleAPI;

import com.AmazonTestAPI.SimpleAPI.Models.GeneralData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class APIService {

    private String URLString = "https://api.citybik.es/v2/networks/velib?fields=stations";

    private JSONArray getData() throws Exception {
        try {
            //Connect to the provided API
            URL url = new URL(URLString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new Exception("Cannont connect to citzbik.es");

            //Parse data
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            StringBuffer response = new StringBuffer();
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                response.append(readLine);
            }
            bufferedReader.close();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response.toString());
            JSONObject networkObject = (JSONObject) jsonObject.get("network");
            JSONArray jsonArray = (JSONArray) networkObject.get("stations");


            return jsonArray;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public GeneralData getGeneralData() throws Exception {
        try {
            JSONArray data = getData();
            long activeStations = 0, numberOfBikes = 0;
            for (int i = 0; i < data.size(); i++) {
                JSONObject station = (JSONObject) data.get(i);
                JSONObject extra = (JSONObject) station.get("extra");
                if (extra.get("status").toString().equals("Operative")) {
                    numberOfBikes += Integer.parseInt(station.get("free_bikes").toString());
                    activeStations++;
                }
            }
            return new GeneralData(numberOfBikes, activeStations);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public JSONObject gerNearestStation(double latitude, double longitude) throws Exception {
        try {
            double minuum = 0;
            double temp = 0;
            boolean first = true;
            JSONArray data = getData();
            JSONObject best = null;
            for (int i = 0; i < data.size(); i++) {
                JSONObject station = (JSONObject) data.get(i);
                JSONObject extra = (JSONObject) station.get("extra");
                if (extra.get("status").toString().equals("Operative")) {
                    double lat = Double.parseDouble(station.get("latitude").toString());
                    double lng = Double.parseDouble(station.get("longitude").toString());
                    temp = distance(latitude, lat, longitude, lng);
                    if (first) {
                        minuum = temp;
                        best = station;
                        first = false;
                        continue;
                    }else if (temp < minuum) {
                        minuum = temp;
                        best = station;
                    }
                }
            }
            return best;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private double distance(double lat1, double lat2, double lng1, double lng2) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;
        return dist;
    }


}
