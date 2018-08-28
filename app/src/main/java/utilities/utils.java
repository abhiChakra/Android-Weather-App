package utilities;

import org.json.JSONException;
import org.json.JSONObject;

public class utils {

    public static final String url1 = "https://api.openweathermap.org/data/2.5/weather?q=";

    public static final String url2 = "&APPID=0d54cf92fde53b497ffab3f9ef8358ab";

    public static final String ICON_URL = "http://openweathermap.org/img/w/";


    public static final String fiveDay_url1 = "https://samples.openweathermap.org/data/2.5/forecast?q=";




    public static JSONObject getObject(String attribute, JSONObject jsonObject) throws JSONException{

        return jsonObject.getJSONObject(attribute);

    }

    public static String getString(String attribute, JSONObject jsonObject) throws JSONException{

        return jsonObject.getString(attribute);

    }

    public static float getFloat(String attribute, JSONObject jsonObject) throws JSONException{

        return (float) jsonObject.getDouble(attribute);
    }


    public static int getInt(String attribute, JSONObject jsonObject) throws JSONException{
        return jsonObject.getInt(attribute);
    }

}
