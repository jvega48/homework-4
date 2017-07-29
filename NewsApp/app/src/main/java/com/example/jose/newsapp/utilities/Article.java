package com.example.jose.newsapp.utilities;


public class Article {

    private String title;
    private String published_date;
    private  String abstr;
    private String thumbURL;
    private String URL;

    public Article(String title, String published_date, String abstr, String thumbURL, String url) {
        this.title = title;
        this.published_date = published_date;
        this.abstr = abstr;
        this.thumbURL = thumbURL;
        URL = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public String getAbstr() {
        return abstr;
    }

    public void setAbstr(String abstr) {
        this.abstr = abstr;
    }

    public String getThumbURL() {
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
