package data;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utilities.utils;
import components.*;

public class FiveDayDataParser {

    public static Weather weatherInfo(String data){

        Weather weather = new Weather();

        try {

            JSONObject jsonObject = new JSONObject(data);

            Place location = new Place();

            JSONObject city = utils.getObject("city", jsonObject);
            location.setCity(utils.getString("name", city));
            location.setCountry(utils.getString("country", city));

            JSONArray jsonArray = jsonObject.getJSONArray("list");

            JSONObject jsonWeather1 = jsonArray.getJSONObject(0);





            JSONObject sys = utils.getObject("sys", jsonObject);

            location.setSunrise(utils.getInt("sunrise", sys));
            location.setSunset(utils.getInt("sunset", sys));

            weather.location = location;


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
