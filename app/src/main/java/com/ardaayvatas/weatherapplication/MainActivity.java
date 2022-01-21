package com.ardaayvatas.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText city;
    TextView tvHandL;
    TextView tv;
    TextView weatherDesc;
    String cityname = "";
    ImageView imageView;
    String img = "";
    String desc ="";
    int imgId;
    int highT;
    int lowT;
    int t;
    String windSpeed;
    String windDeg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHandL = findViewById(R.id.tvHandL);
        tv = findViewById(R.id.tv);
        city = findViewById(R.id.city);
        imageView = findViewById(R.id.imageView);
        weatherDesc = findViewById(R.id.description);
        new JsonTask().execute("https://api.openweathermap.org/data/2.5/weather?q="+"istanbul"+"&appid=205e6fce44d62ad88c78cd97586f9a48");
    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try
            {
                img = "w";
                JSONObject jsonObject = new JSONObject(result).getJSONObject("main");
                JSONObject jsonObject1 = new JSONObject(result).getJSONObject("wind");
                JSONObject jsonObject2 = new JSONObject(result).getJSONArray("weather").getJSONObject(0);
                JSONObject jsonObject3 = new JSONObject(result).getJSONArray("weather").getJSONObject(0);
                desc = ""+jsonObject3.getString("description");
                img = img +jsonObject2.getString("icon");
                imgId = getResources().getIdentifier(img,"drawable", "com.ardaayvatas.weatherapplication");
                imageView.setImageResource(imgId);
                t = (int) (jsonObject.getDouble("temp")-273.15);
                tv.setText(t+" C");
                highT = (int) (jsonObject.getDouble("temp_max")-273.15);
                lowT = (int) (jsonObject.getDouble("temp_min")-273.15);
                tvHandL.setText("H: "+highT+" C"+"\nL: "+lowT+" C");
                weatherDesc.setText(desc);
                windDeg = jsonObject1.getString("deg");
                windSpeed = jsonObject1.getString("speed");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void goSearch(View view)
    {
        cityname = city.getText().toString();

        new JsonTask().execute("https://api.openweathermap.org/data/2.5/weather?q="+cityname+"&appid=205e6fce44d62ad88c78cd97586f9a48");
    }

    public void goWind(View view)
    {
        Intent intent = new Intent(MainActivity.this,WindActivity.class);
        intent.putExtra("deg",windDeg);
        intent.putExtra("speed",windSpeed);
        intent.putExtra("cityname",cityname);
        startActivity(intent);
    }

}