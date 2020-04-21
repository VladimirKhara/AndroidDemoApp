package com.example.easyclusterapp;

public class ClusterResult {
    private String idx;
    private String d;
    private String centr;
    private int imgRes;
    private int imgLike;
    private int imgDislike;

    public ClusterResult(String idx, String d, String centr, int imgRes, int imgLike, int imgDislike) {
        this.idx = idx;
        this.d = d;
        this.centr = centr;
        this.imgRes = imgRes;
        this.imgLike = imgLike;
        this.imgDislike = imgDislike;
    }

    public String getIdx() {
        return this.idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getD() {
        return this.d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getCentr() {
        return this.centr;
    }

    public void setCentr(String centr) {
        this.centr = centr;
    }

    public int getImgRes() {
        return this.imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public int getImgLike() {
        return this.imgLike;
    }

    public void setImgLike(int imgLike) {
        this.imgLike = imgLike;
    }

    public int getImgDislike() {
        return this.imgDislike;
    }

    public void setImgDislike(int imgDislike) {
        this.imgDislike = imgDislike;
    }
}
