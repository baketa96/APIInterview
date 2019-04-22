import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.*;
import java.io.IOException;
import java.sql.Struct;


public class WorkWithData {

    private JSONArray array;
    private JSONObject temp;
    private JSONObject tempExtra;
    private int noOfOperational;
    private int noOfBikes;
    private JSONArray operativeArray;
    private double minimum;
    private double latitude;
    private double longitude;
    private double tempMin;
    private JSONObject tempNearest;

    public WorkWithData(JSONArray array) {
        this.array = array;
        noOfBikes = 0;
        noOfOperational = 0;
        operativeArray = new JSONArray();
        minimum = 10000;
        tempMin = 0;
        operativeArray = getOperative();
    }

    public int getNoOfOperative() {
       return   getOperative().size();
    }

    public int getNoOfBikes(){
        for (int i = 0; i<operativeArray.size(); i++){
            temp  = (JSONObject) operativeArray.get(i);
            noOfBikes += Integer.parseInt(temp.get("free_bikes").toString());
        }
        return noOfBikes;
    }

    public JSONArray getOperative() {
        for (int i = 0; i < array.size(); i++) {
            temp = (JSONObject) array.get(i);
            tempExtra = (JSONObject) temp.get("extra");
            if (tempExtra.get("status").toString().equalsIgnoreCase("Operative")) {
                operativeArray.add(temp);
            }
        }
        return operativeArray;
    }


    public JSONObject getNearest(double lat, double lng) {
        for(int i = 0; i<operativeArray.size(); i++){
            temp  = (JSONObject) operativeArray.get(i);
            latitude = Double.parseDouble(temp.get("latitude").toString());
            longitude = Double.parseDouble(temp.get("longitude").toString());
            tempMin = distance(lat,latitude, lng, longitude);
            if(tempMin<minimum){
                minimum = tempMin;
                tempNearest = temp;
            }
        }
        return tempNearest;
    }

    private double distance(double lat1, double lat2, double lng1, double lng2){
        double earthRadius = 6371;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;
        return dist;
    }
}
