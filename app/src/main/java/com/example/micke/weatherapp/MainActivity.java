package com.example.micke.weatherapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.Layout;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import Adapter.SearchAdapter;
import Databases.Database;
import components.Weather;
import data.ChangeCity;
import data.ChangeCountry;
import data.dataParser;
import data.webData;

public class MainActivity extends AppCompatActivity{


    //ChangeCity defaultCity = new ChangeCity(MainActivity.this);

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter searchAdapter;


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String code){

        this.countryCode = code;

    }

    private String countryCode = "CA";

    String previousString;

    MaterialSearchBar searchBar;
    List<String> suggestList = new ArrayList<>();

    Database dataBase;


    View wrap_background;

    private static TextView cityName;
    private static ImageView weatherIcon;
    private static TextView temp;
    private static TextView weatherDes;
    //private static ImageView sunsetIcon;
    //private static ImageView sunriseIcon;
    private static TextView sunriseTime;
    private static TextView sunsetTime;


    static Weather currWeather = new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wrap_background = findViewById(R.id.total_wrap);



        cityName = (TextView) findViewById(R.id.cityName);
        weatherIcon = (ImageView) findViewById(R.id.weatherIcon);
        temp = (TextView) findViewById(R.id.temperature);
        weatherDes = (TextView) findViewById(R.id.description);
        sunriseTime = (TextView) findViewById(R.id.sunriseTime);
        sunsetTime = (TextView) findViewById(R.id.sunsetTime);

        //sunriseIcon = (ImageView) findViewById(R.id.sunriseIcon);
        //sunsetIcon = (ImageView) findViewById(R.id.sunsetIcon);


        //sunrise.setImageBitmap(BitmapFactory.decodeFile("drawable-v24/sunrise.png"));
        //sunset.setImageBitmap(BitmapFactory.decodeFile("drawable-v24/sunset.png"));
        //sunriseIcon.setImageResource(R.drawable.sunrise);
        //sunsetIcon.setImageResource(R.drawable.sunset);


        ChangeCity changeCity = new ChangeCity(MainActivity.this);


        executeWeather(changeCity.getDefaultCity());
        //executeWeather("Toronto, CA");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setFocusable(false);
        //recyclerView.clearOnScrollListeners();





        searchBar = (MaterialSearchBar)findViewById(R.id.search_bar);
        //searchBar.hideSuggestionsList();





        dataBase = new Database(this);

        //searchBar.setHint("Change City");
        searchBar.setCardViewElevation(10);
        loadSuggestList();

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(searchBar.getText().equals("")){
                    searchAdapter = new SearchAdapter(getBaseContext(), dataBase.getCityByName("a", countryCode));
                    recyclerView.setAdapter(searchAdapter);
                }else{
                    //searchAdapter = new SearchAdapter(MainActivity.this, dataBase.
                    //       getCityByName(searchBar.getText()));
                    //recyclerView.setAdapter(searchAdapter);

                    Iterator<String> cities = suggestList.iterator();
                    while(cities.hasNext()){
                        String o = cities.next();
                        cities.remove();
                    }


                    suggestList = dataBase.getStringCityByName(searchBar.getText(), countryCode);

                    //loadSuggestList(searchBar.getText());

                    List<String> suggest = new ArrayList<>();
                    for(String search:suggestList){
                        if(search.toLowerCase().contains(searchBar.getText().toLowerCase())){
                            suggest.add(search);
                        }
                        searchBar.setLastSuggestions(suggest);

                    }

                }

            }



            @Override
            public void afterTextChanged(Editable editable) {




            }
        });

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled){
                    //searchAdapter = new SearchAdapter(getBaseContext(), dataBase.getCityByName("a"));
                    //recyclerView.setAdapter(searchAdapter);

//                    String searchedText = searchBar.getText().toLowerCase();
//                    ChangeCity changeCity = new ChangeCity(MainActivity.this);
//                    changeCity.setCity(searchedText);
//                    String newCity = changeCity.getDefaultCity();
//
//                    executeWeather(newCity);
//                    hideKeyboard(MainActivity.this);

                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
                hideKeyboard(MainActivity.this);
                searchBar.clearSuggestions();
                searchBar.disableSearch();
                loadSuggestList();

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


        //searchAdapter = new SearchAdapter(this, dataBase.getCityByName("a", countryCode));
        //recyclerView.setAdapter(searchAdapter);
    }




    private void startSearch(String text){

        //String cityName = dataBase.getCityByName(text)[0];

        //searchAdapter = new SearchAdapter(this, dataBase.getCityByName(text));
        //recyclerView.setAdapter(searchAdapter);

        //final ChangeCity changeCity = new ChangeCity(MainActivity.this);
        executeWeather(text);
        //changeCity.setCity(text);

        //String newCity = changeCity.getDefaultCity();

        //executeWeather(newCity);


    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void loadSuggestList(){

        Iterator<String> cities = suggestList.iterator();
        while(cities.hasNext()){
            String o = cities.next();
            cities.remove();
        }

        suggestList = dataBase.getNames("A", countryCode);

        searchBar.setLastSuggestions(suggestList);
    }

    private void loadSuggestList(String text){

        suggestList = dataBase.getNames(text, countryCode);

        searchBar.setLastSuggestions(suggestList);
    }




    public void executeWeather(String location){

        getWeather currWeather = new getWeather();

        currWeather.execute(new String[]{location});

    }



    private static class getIcon extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            weatherIcon.setImageBitmap(bitmap);

        }


        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = "http://openweathermap.org/img/w/" + strings[0] + ".png";
            Bitmap currIcon = null;
            try{
                InputStream in = new java.net.URL(url).openStream();
                currIcon = BitmapFactory.decodeStream(in);
            }catch(Exception e){
                Log.v("Error", e.getMessage());
                e.printStackTrace();
            }
            return currIcon;
        }
    }


    private void changeBackground(String iconDes){

        if(iconDes.equals( "01d")){
            wrap_background.setBackgroundResource(R.color.sunnyDay);
        }else if(iconDes.equals("02d")){
            wrap_background.setBackgroundResource(R.color.slightCloud);
        }else if(iconDes.equals("03d")){
            wrap_background.setBackgroundResource(R.color.cloud);
        }else if(iconDes.equals("04d")){
            wrap_background.setBackgroundResource(R.color.veryCloud);
        }else if(iconDes.equals("09d")){
            wrap_background.setBackgroundResource(R.color.showerRain);
        }else if(iconDes.equals("10d")){
            wrap_background.setBackgroundResource(R.color.rain);
        }else if(iconDes.equals("11d")){
            wrap_background.setBackgroundResource(R.color.thunderstorm);
        }else if(iconDes.equals("13d")){
            wrap_background.setBackgroundResource(R.color.snow);
        }else if(iconDes.equals("50d")){
            wrap_background.setBackgroundResource(R.color.fog);
        }else if(iconDes.equals("01n")){
            wrap_background.setBackgroundResource(R.color.night);
        }else if(iconDes.equals("02n")){
            wrap_background.setBackgroundResource(R.color.slightCloudNight);
        }else if(iconDes.equals("03n")){
            wrap_background.setBackgroundResource(R.color.cloud);
        }else if(iconDes.equals("04n")){
            wrap_background.setBackgroundResource(R.color.veryCloud);
        }else if(iconDes.equals("09n")){
            wrap_background.setBackgroundResource(R.color.showerRain);
        }else if(iconDes.equals("10n")){
            wrap_background.setBackgroundResource(R.color.rain);
        }else if(iconDes.equals("11n")){
            wrap_background.setBackgroundResource(R.color.thunderstorm);
        }else if(iconDes.equals("13n")){
            wrap_background.setBackgroundResource(R.color.snow);
        }else if(iconDes.equals("50n")){
            wrap_background.setBackgroundResource(R.color.fog);
        }

    }

    private class getWeather extends AsyncTask<String, Void, Weather>{
        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);



            if(weather!=null){
                cityName.setText(weather.location.getCity());

                String currTemp = Integer.toString((int) ((weather.temp)-273.15)) + "°C";

                DateFormat df = DateFormat.getTimeInstance();
                String sunriseDate = df.format(new Date(weather.location.getSunrise()));
                String sunsetDate = df.format(new Date(weather.location.getSunset()));

                changeBackground(weather.weatherIcon);
                temp.setText(currTemp);
                weatherDes.setText(weather.description);
                sunriseTime.setText(sunriseDate);
                sunsetTime.setText(sunsetDate);



            }else{
                System.out.println("Error");
            }


        }

        @Override
        protected Weather doInBackground(String... strings) {

            String weatherData = "";

            final MainActivity parent = MainActivity.this;


            try {
                //Log.v("Data: ", currWeather.location.getCity());
                weatherData = ((new webData()).weatherData(strings[0]));
                currWeather = dataParser.weatherInfo(weatherData);
                getIcon currIcon = new getIcon();
                currIcon.execute(currWeather.weatherIcon);



                Log.v("icon: ", currWeather.weatherIcon);

            }catch (FileNotFoundException e) {
                Log.v("Data: ", e.toString());
                parent.runOnUiThread(new Runnable() {
                    public void run() {

                        CharSequence text = "Sorry, there is no data existing for this city";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(MainActivity.this, text, duration);

                        toast.setGravity(Gravity.CENTER, 0, 0);

                        toast.show();
                    }
                });

            }


            previousString = strings[0];

            Log.v("Data: ", currWeather.location.getCity());
            Log.v("Data: ", Integer.toString(currWeather.temp));


            return currWeather;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    private void showCountrySelection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Change country");

        final EditText countryInput = new EditText(MainActivity.this);
        countryInput.setInputType(InputType.TYPE_CLASS_TEXT);
        countryInput.setHint("United States");
        builder.setView(countryInput);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(dataBase.CheckIsDataAlreadyInDBorNot(countryInput.getText().toString().toLowerCase())){

                    String full_value = dataBase.getValueAtRow(countryInput.getText().toString());

                    List<String> elephantList = Arrays.asList(full_value.split(","));

                    //if(elephantList.size() == 3){
                        String cityName = elephantList.get(2).trim();
                        String countryCode = elephantList.get(1).trim();

                   // }else if(elephantList.size() == 4){
                       // String cityName = elephantList.get(2).trim();

                        //cityName = cityName + ", " + elephantList.get(3).trim();

                       // String countryCode = elephantList.get(1).trim();
                    //}





                    ChangeCountry changeCountry = new ChangeCountry(MainActivity.this);
                    changeCountry.setCountry(cityName.toString(), countryCode);

                    setCountryCode(countryCode);

                    String newCity = changeCountry.getDefaultCountry();

                    executeWeather(newCity);
                    loadSuggestList();

                }else{


                    CharSequence text = "Sorry, invalid country input";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(MainActivity.this, text, duration);

                    toast.setGravity(Gravity.CENTER, 0, 0);

                    toast.show();
                }


                }

            });

        builder.show();
    }

//    private void showChangeCityMenu(){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setTitle("Choose a different city");
//
//        final EditText cityInput = new EditText(MainActivity.this);
//        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
//        cityInput.setHint("Vancouver, CA");
//        builder.setView(cityInput);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                //final ChangeCity changeCity = new ChangeCity(MainActivity.this);
//
//
//                executeWeather(cityInput.getText().toString());
//
//                }
//
//                //changeCity.setCity(cityInput.getText().toString());
//
//                //String newCity = changeCity.getDefaultCity();
//
//
//        });



//        builder.show();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.changeCity) {
            showCountrySelection();
        }

        return super.onOptionsItemSelected(item);
    }
}
