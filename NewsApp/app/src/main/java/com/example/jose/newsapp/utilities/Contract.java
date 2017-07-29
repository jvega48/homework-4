package com.example.jose.newsapp.utilities;

import android.provider.BaseColumns;


public class Contract  {

    //creating the table properties for the articles with its columns know as contract

    public static class TABLE_ARTICLES implements BaseColumns{
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_PUBLISHED_DATE ="date";
        public static final String COLUMN_NAME_ABSTRACT = "abstract";
        public static final String COLUMN_NAME_THUMBURL = "thumburl";
        public static final String COLUMN_NAME_URL = "url";
    }
}
