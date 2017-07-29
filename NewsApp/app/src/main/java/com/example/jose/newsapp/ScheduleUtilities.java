package com.example.jose.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.firebase.jobdispatcher.Driver;


public class ScheduleUtilities {

    private static final int SCHEDULE_INTERVAL_MINUTES = 360;
    private static final int SYNC_FLEXTIME_SECONDS = 60;
    private static final String NEWS_JOB_TAG = "news job tag";

    private static boolean isInitialized;

    //calls the firebase job dispatcher to refresh new news every SYNC_FLEXTIME_SECONDS
    synchronized public static void scheduleRefresh(@NonNull final Context context){

        if(isInitialized)
            return;


        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job constraintRefreshJob = dispatcher.newJobBuilder().setService(NewsJob.class)
                .setTag(NEWS_JOB_TAG).setConstraints(Constraint.ON_ANY_NETWORK).setLifetime(Lifetime.FOREVER)
                .setRecurring(true).setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_MINUTES,
                        SCHEDULE_INTERVAL_MINUTES + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true).build();
        Log.d(NEWS_JOB_TAG, "New Jobs Executing");
        dispatcher.schedule(constraintRefreshJob);
        isInitialized = true;

    }



}

