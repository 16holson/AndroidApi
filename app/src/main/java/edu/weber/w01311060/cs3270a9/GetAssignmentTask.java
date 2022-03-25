package edu.weber.w01311060.cs3270a9;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

import edu.weber.w01311060.cs3270a9.models.Assignments;


public class GetAssignmentTask extends AsyncTask<String, Integer, String>
{
    private String json = "";
    private String courseId;
    private String[] assignments;
    public AsyncResponse mCallBack = null;

    public interface AsyncResponse
    {
        void processFinish(String[] output);
    }

    public GetAssignmentTask(AsyncResponse mCallBack)
    {
        this.mCallBack = mCallBack;
    }

    @Override
    protected String doInBackground(String... params)
    {

        courseId = params[0];

        try
        {
            URL url = new URL("https://weber.instructure.com/api/v1/courses/" + courseId + "/assignments?per_page=50");
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

        assignments = jsonParse(json);
        Log.d("Task", "assignments: " + assignments);
        mCallBack.processFinish(assignments);
    }

    private String[] jsonParse(String rawJson)
    {
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();

        Assignments[] a = null;

        try
        {
            a = gson.fromJson(rawJson, Assignments[].class);
        }
        catch (Exception e)
        {
            Log.d("Task", "Error converting json: " + e.getMessage());
        }

        //Too new
//        String[] temp = Arrays.stream(a)
//                              .map(assignment -> assignment.getName())
//                              .toArray(String[]::new);
        ArrayList<String> temp = new ArrayList<String>();
        if(a != null)
        {
            for(int i = 0; i < a.length; i++)
            {
                temp.add(a[i].getName());
            }
        }
        return temp.toArray(new String[temp.size()]);
    }
}
