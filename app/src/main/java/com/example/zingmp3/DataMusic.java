package com.example.zingmp3;

public class DataMusic {

    String numerical_order;
    String name_song;
    String name_singer;

    public DataMusic(String order, String song, String singer) {
        this.numerical_order = order;
        this.name_song = song;
        this.name_singer = singer;
    }

    public void setNumerical_order(String numerical_order) {
        this.numerical_order = numerical_order;
    }

    public void setName_song(String name_song) {
        this.name_song = name_song;
    }

    public void setName_singer(String name_singer) {
        this.name_singer = name_singer;
    }

    public String getNumerical_order() {
        return numerical_order;
    }

    public String getName_song() {
        return name_song;
    }

    public String getName_singer() {
        return name_singer;
    }

}
