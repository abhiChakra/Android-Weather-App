package data;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import utilities.utils;

import javax.net.ssl.HttpsURLConnection;

public class FiveDayWebData {

    public String weatherData(String loc) throws FileNotFoundException {

        HttpsURLConnection connection = null;
        InputStream input = null;

        try {

            connection = (HttpsURLConnection) (new URL(utils.fiveDay_url1 + loc + utils.url2)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();


            StringBuilder stringBuilder = new StringBuilder();
            input = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));

            String n = null;

            while((n = bufferedReader.readLine()) != null){

                stringBuilder.append(n + "\r\n");
            }

            input.close();
            connection.disconnect();

            return stringBuilder.toString();

        } catch (FileNotFoundException e) {

            throw new FileNotFoundException();

        } catch (IOException e){

            e.printStackTrace();
        }


        return null;
    }
}
