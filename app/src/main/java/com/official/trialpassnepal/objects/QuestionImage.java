package com.official.trialpassnepal.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionImage {

    @SerializedName("qid")
    @Expose
    private String qid;
    @SerializedName("catid")
    @Expose
    private String catid;
    @SerializedName("imagePath")
    @Expose
    private String imagePath;

    /**
     * @return The qid
     */
    public String getQid() {
        return qid;
    }

    /**
     * @param qid The qid
     */
    public void setQid(String qid) {
        this.qid = qid;
    }

    /**
     * @return The catid
     */
    public String getCatid() {
        return catid;
    }

    /**
     * @param catid The catid
     */
    public void setCatid(String catid) {
        this.catid = catid;
    }

    /**
     * @return The imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath The imagePath
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
