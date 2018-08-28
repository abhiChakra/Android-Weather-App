package Databases;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import model.city;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME= "city_list_final3.db";
    private static int DB_VER = 1;

    Boolean queried = false;

    ArrayList<String> queriedTables = new ArrayList<>();

    public Database(Context context){


        super(context, DB_NAME, null, DB_VER);


    }




    public List<city> getCity(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlCities = {"city"};

        String tableName = "CA";







        qb.setTables(tableName);


        Cursor cursor = qb.query(db, sqlCities, null, null, null, null, null);

        List<city> result = new ArrayList<>();
        if (cursor.moveToFirst()){

            do{
                city city = new city();

                if(cursor.getString(cursor.getColumnIndex("city")).equals("")){
                    continue;

                }else {
                    city.setCityName(cursor.getString(cursor.getColumnIndex("city")));

                    result.add(city);
                }

            }while (cursor.moveToNext());

        }
        return result;
    }

    public List<String> getNames(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlCities = {"A"};

        String tableName = "CA";

        queryIndex(db, tableName);

        qb.setTables(tableName);

        Cursor cursor = qb.query(db, sqlCities, null, null, null, null, null);

        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()){

            do{
                if(cursor.getString(cursor.getColumnIndex("A")) == ""){


                }else {
                    result.add(cursor.getString(cursor.getColumnIndex("A")));
                }



            }while (cursor.moveToNext());

        }
        return result;


    }


    public List<String> getCountries(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlCities = {"countries"};
        String tableName = "countries";


        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlCities, null, null , null, null, null);
        List<String> dataBaseCountries = new ArrayList<>();
        if(cursor.moveToFirst()){

            do{
                dataBaseCountries.add(cursor.getString(cursor.getColumnIndex("countries")));

            }while(cursor.moveToNext());

        }

        return dataBaseCountries;
    }



    public List<String> getNames(String searchCity, String countryCode){


        String firstLetter = searchCity.substring(0,1);

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlCities = {firstLetter.toUpperCase()};

        //String[] sqlCities = {"A"};

        //String tableName = searchCity.substring(0,1);

        String tableName = "";

        if (countryCode.equals("IN")){

            tableName = "INDIA";

        }else{
            tableName = countryCode;
        }

        qb.setTables(tableName);

        queryIndex(db, tableName);

        Cursor cursor = qb.query(db, sqlCities, String.format("%s LIKE ?", firstLetter.toUpperCase()), new String[]{"%"+searchCity+"%"}, null, null, null);
        //Cursor cursor = qb.query(db, sqlCities, null, null , null, null, null);

        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()){

            do{


                if(cursor.getString(cursor.getColumnIndex(firstLetter.toUpperCase())).equals("")){
                    continue;
                }else{

                    result.add(cursor.getString(cursor.getColumnIndex(firstLetter.toUpperCase())));

                }


                //result.add(cursor.getString(cursor.getColumnIndex(firstLetter.toUpperCase())));

            }while (cursor.moveToNext());

        }
        return result;


    }

    private boolean queryIndex(SQLiteDatabase db, String tableName){

        if(queriedTables.size() == 0){
            return false;

        } else if(queriedTables.contains(tableName)){
            return false;
        }else {

            db.execSQL("CREATE INDEX citiesIndex ON " + tableName + "(city)");

            queriedTables.add(tableName);

            return true;
        }
    }



//    public boolean checkInDatabase(String countryName){
//
//        SQLiteDatabase db = getReadableDatabase();
//        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//
//        String[] sqlCities = {"countries"};
//
//        String tableName = "countries_fill";
//        qb.setTables(tableName);
//
//
//        queryIndex(db, tableName);
//
//        Cursor cursor = qb.query(db, sqlCities, String.format("%s LIKE ?", firstLetter.toUpperCase()), new String[]{"%"+searchCity+"%"}, null, null, null);
//        //Cursor cursor = qb.query(db, sqlCities, "city LIKE ?", new String[]{"%"+searchCity+"%"}, null, null, null);
//
//        return result;
//    }


    public boolean CheckIsDataAlreadyInDBorNot(String search_country) {

        if(search_country == null){
            return false;
        }

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlCities = {"countries"};
        String tableName = "countries_fullnames";
        qb.setTables(tableName);

        queryIndex(db, tableName);

        //Cursor cursor = qb.query(db, sqlCities, String.format("%s LIKE ?", search_country), new String[]{"%"+searchCity+"%"}, null, null, null);

        //String tableName = "countries_fullform";
        Cursor cursor = qb.query(db, sqlCities, String.format("%s LIKE ?", "countries"), new String[]{"%"+search_country+"%"}, null, null, null);
        //Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }


    public String getValueAtRow(String searchCountry){


        String value = "";

        if(searchCountry == null){
            return null;
        }

        //String firstLetter = searchCity.substring(0,1);

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlCities = {"countries"};

        //String tableName = searchCity.substring(0,1);

        String tableName = "countries_fullFinalnames";
        qb.setTables(tableName);


        queryIndex(db, tableName);
        Cursor cursor = qb.query(db, sqlCities, String.format("%s LIKE ?", "countries"), new String[]{"%"+searchCountry+"%"}, null, null, null);
        //Cursor cursor = qb.query(db, sqlCities, "city LIKE ?", new String[]{"%"+searchCity+"%"}, null, null, null);


        if (cursor.moveToFirst()){

            do{

                if(cursor.getString(cursor.getColumnIndex("countries")) == ""){
                    continue;

                }else{

                    value = cursor.getString(cursor.getColumnIndex("countries"));
                    break;

                }

            }while (cursor.moveToNext());

        }
        return value;

    }


    public List<String> getStringCityByName(String searchCity, String countryCode){


        if(searchCity == null){
            return null;
        }

        String firstLetter = searchCity.substring(0,1);

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlCities = {firstLetter.toUpperCase()};

        //String tableName = searchCity.substring(0,1);

        String tableName = "";

        if (countryCode.equals("IN")){

            tableName = "INDIA";

        }else{
            tableName = countryCode;
        }


        qb.setTables(tableName);


        queryIndex(db, tableName);

        Cursor cursor = qb.query(db, sqlCities, String.format("%s LIKE ?", firstLetter.toUpperCase()), new String[]{"%"+searchCity+"%"}, null, null, null);
        //Cursor cursor = qb.query(db, sqlCities, "city LIKE ?", new String[]{"%"+searchCity+"%"}, null, null, null);

        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()){



            do{

                if(cursor.getString(cursor.getColumnIndex(firstLetter.toUpperCase())) == ""){
                    continue;

                }else{

                    city city = new city();

                    city.setCityName(cursor.getString(cursor.getColumnIndex(firstLetter.toUpperCase())));
                    result.add(city.getCityName());

                }





                //city.setCityName(cursor.getString(cursor.getColumnIndex(firstLetter.toUpperCase())));



            }while (cursor.moveToNext());

        }
        return result;

    }


    public List<city> getCityByName(String searchCity, String countryCode){

        if(searchCity == null){
            return null;
        }

        String firstLetter = searchCity.substring(0,1);

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlCities = {firstLetter.toUpperCase()};

        //String tableName = searchCity.substring(0,1);

        String tableName = "";

        if (countryCode.equals("IN")){

            tableName = "INDIA";

        }else{
            tableName = countryCode;
        }

        qb.setTables(tableName);


        queryIndex(db, tableName);


        Cursor cursor = qb.query(db, sqlCities, String.format("%s LIKE ?", firstLetter.toUpperCase()), new String[]{"%"+searchCity+"%"}, null, null, null);

        List<city> result = new ArrayList<>();
        if (cursor.moveToFirst()){


            do{

                if(cursor.getString(cursor.getColumnIndex(firstLetter.toUpperCase())) == ""){
                    continue;

                }else{
                    city city = new city();
                    city.setCityName(cursor.getString(cursor.getColumnIndex(firstLetter.toUpperCase())));

                    result.add(city);

                }



            }while (cursor.moveToNext());

        }
        return result;

    }


}
