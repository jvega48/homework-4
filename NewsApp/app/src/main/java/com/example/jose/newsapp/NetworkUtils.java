package com.example.jose.newsapp;

import android.net.Uri;
import android.util.Log;
import com.example.jose.newsapp.utilities.Article;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;



public class NetworkUtils {

    public static final String TAG ="Network Utils";
    public static final String MAIN_URL = "https://api.nytimes.com/svc/mostpopular/v2/mostviewed/U.S./30.json";
    public static final String PARAM_API_KEY= "api-key";
    /*
    *
    ****************************************************************
    * PLEASE READ THE PARAM API KEY IS REMOVE USE YOUR OWN API KEY *
    ****************************************************************
    *
    * */

    public static URL buildURL() {
        Uri uri = Uri.parse(MAIN_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, "f6498ed56d414cee9cd3bd4c0a34f559").build();

        URL url = null;
        try {
            String urlString = uri.toString();
            Log.d(TAG, "Url: " + urlString);
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner input = new Scanner(in);

            input.useDelimiter("\\A");
            String result = (input.hasNext()) ? input.next() : null;
            return result;

        }catch (IOException e){
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    public static ArrayList<Article> parseJSON(String json) throws JSONException {
        ArrayList<Article> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray items = main.getJSONArray("results");
        String imgUrl = null;

        for(int i = 0; i < items.length(); i++){
            JSONObject item = items.getJSONObject(i);
            String title = item.getString("title");
            String publishedDate = item.getString("published_date");
            String abstr = item.getString("abstract");
            String url = item.getString("url");

            JSONArray mediaObjects = item.optJSONArray("media");
            if(mediaObjects != null){
                JSONObject img = mediaObjects.getJSONObject(0);
                JSONArray metaData = img.getJSONArray("media-metadata");
                JSONObject thumbNailMeta = metaData.getJSONObject(0);
                imgUrl = thumbNailMeta.getString("url");
            }
            result.add(new Article(title, publishedDate, abstr, imgUrl, url));
        }
        Log.d(TAG, "final articles size: " + result.size());
        return result;
    }
}
