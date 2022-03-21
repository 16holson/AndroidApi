package edu.weber.w01311060.cs3270a9;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import edu.weber.w01311060.cs3270a9.db.AppDatabase;
import edu.weber.w01311060.cs3270a9.models.Courses;

public class GetCourseTask extends AsyncTask<Context, Integer, String>
{
    private String json = "";
    private Context context;
    @Override
    protected String doInBackground(Context... contexts)
    {
        this.context = context;

        try
        {
            URL url = new URL("https://weber.instructure.com/api/v1/courses");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + Authorization.token);
            connection.connect();

            int status = connection.getResponseCode();

            switch (status)
            {
                case 200:
                case 201:
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    json = reader.readLine();
                    Log.d("Task", "json: " + json);
                    return json;
                default:
                    Log.d("Task", "Incorrect status");
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            Log.d("Task", "URL Malformed: " + e.getMessage());
        }
        catch (IOException e)
        {
            Log.d("Task", "IOException: " + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        Courses[] courses = jsonParse(json);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                AppDatabase.getInstance(context)
                        .getCourseDao()
                        .insertCourses(courses);
            }
        }).start();
    }

    private Courses[] jsonParse(String rawJson)
    {
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();

        Courses[] courses = null;

        try
        {
            courses = gson.fromJson(rawJson, Courses[].class);
        }
        catch (Exception e)
        {
            Log.d("Task", "Error converting json: " + e.getMessage());
        }
        return courses;
    }
}
