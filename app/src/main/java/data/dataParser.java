package data;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.WeakHashMap;

import utilities.utils;
import components.*;

public class dataParser {

    public static Weather weatherInfo(String data){

        Weather weather = new Weather();

        try {



            JSONObject jsonObject = new JSONObject(data);

            Place location = new Place();

            JSONObject coord = utils.getObject("coord", jsonObject);
            location.setLat(utils.getFloat("lat", coord));
            location.setLon(utils.getFloat("lon", coord));
            location.setCity(utils.getString("name", jsonObject));

            JSONObject sys = utils.getObject("sys", jsonObject);
            location.setCountry(utils.getString("country", sys));
            location.setSunrise(utils.getInt("sunrise", sys));
            location.setSunset(utils.getInt("sunset", sys));

            weather.location = location;

            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonWeather = jsonArray.getJSONObject(0);

            JSONObject currTemp = utils.getObject("main", jsonObject);
            weather.temp = utils.getInt("temp", currTemp);
            weather.weatherIcon = utils.getString("icon", jsonWeather);
            weather.description = utils.getString("description", jsonWeather);

            return weather;


        }catch (JSONException e){

            e.printStackTrace();
        }
        return null;
    }
}
