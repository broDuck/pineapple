package com.broduck.domain;

/**
 * Created by broDuck on 2016-01-25.
 */
public class MusicVO {
    private Integer mno;
    private String title;
    private String artist;
    private String filepath;
    private Integer viewcnt;

    public Integer getMno() {
        return mno;
    }

    public void setMno(Integer mno) {
        this.mno = mno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getViewcnt() {
        return viewcnt;
    }

    public void setViewcnt(Integer viewcnt) {
        this.viewcnt = viewcnt;
    }

    @Override
    public String toString() {
        return "MusicVO{" +
                "mno=" + mno +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", filepath='" + filepath + '\'' +
                ", viewcnt=" + viewcnt +
                '}';
    }
}
