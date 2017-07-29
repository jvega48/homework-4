package com.example.jose.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jose.newsapp.utilities.Article;
import com.example.jose.newsapp.utilities.DBHelper;
import com.example.jose.newsapp.utilities.DatabaseUtils;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class RefreshTasks {

    public static final String ACTION_REFRESH = "refresh";

    //grabs results adds them into and array calling the network utils
    //every time the refresh item on the item menu is click it fires the refresh making new data available

    public static void refreshArticles(Context context) {
        ArrayList<Article> result = null;
        URL url = NetworkUtils.buildURL();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            DatabaseUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DatabaseUtils.insert(db, result);
            Log.d(ACTION_REFRESH , "Exectuing the refresh");

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();
    }
}
