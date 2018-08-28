package data;

import android.app.Activity;
import android.content.SharedPreferences;

public class ChangeCity {

    SharedPreferences preferences;

    public ChangeCity(Activity activity){

        preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }


    public String getDefaultCity(){

        return preferences.getString("city", "Toronto,CA");

    }

    public void setCity(String city){
        preferences.edit().putString("city", city).apply();
    }

}
