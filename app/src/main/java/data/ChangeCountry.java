package data;
import android.app.Activity;
import android.content.SharedPreferences;

import com.example.micke.weatherapp.MainActivity;

import Databases.Database;

public class ChangeCountry {

    SharedPreferences preferences;

    public ChangeCountry(Activity activity){

        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getDefaultCountry(){
        return preferences.getString("country", "Toronto, CA");
    }


    public void setCountry(String cityName, String countryName){

        String newCity = cityName + ", " + countryName;

        preferences.edit().putString("country", newCity).apply();
    }





}
