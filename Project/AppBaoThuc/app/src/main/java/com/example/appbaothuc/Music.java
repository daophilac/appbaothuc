package com.example.appbaothuc;

import java.util.Comparator;

public class Music {
    private String url;
    private String name;

    public Music(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }








    public static class UrlComparator implements Comparator<Music>{
        @Override
        public int compare(Music o1, Music o2) {
            return o1.url.compareToIgnoreCase(o2.url);
        }
    }
    public static class NameComparator implements Comparator<Music>{
        @Override
        public int compare(Music o1, Music o2) {
            return o1.name.compareToIgnoreCase(o2.name);
        }
    }
}