package com.example.jose.newsapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class NewsJob extends JobService {

    AsyncTask mBackgroundTask;
    public static final String TAG = "news job - job service";

    //creates the fire base job calling the on start job executing the
    //on start job , do in background, post execute, and on stop job taking care of the background tasks

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

            mBackgroundTask = new AsyncTask() {

                @Override
                protected void onPreExecute() {
                    Toast.makeText(NewsJob.this, "News refreshed",  Toast.LENGTH_SHORT).show();
                    super.onPreExecute();
                    Log.d(TAG, "on pre execute ");
                }


                @Override
                protected Object doInBackground(Object[] objects) {
                    RefreshTasks.refreshArticles(NewsJob.this);
                    Log.d(TAG, "do in background execute ");
                    return null;

                }

                @Override
                protected void onPostExecute(Object o) {
                    jobFinished(jobParameters, false);
                    Log.d(TAG, "on post execute ");
                    super.onPostExecute(o);
                }
            };

            mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d(TAG, "on stop execute");
        if (mBackgroundTask != null)
            mBackgroundTask.cancel(false);

        return true;
    }
}
