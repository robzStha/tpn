package com.official.trialpassnepal.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("qid")
    @Expose
    private String qid;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("qtype")
    @Expose
    private String qtype;
    @SerializedName("published")
    @Expose
    private String published;

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
     * @return The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question The question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return The qtype
     */
    public String getQtype() {
        return qtype;
    }

    /**
     * @param qtype The qtype
     */
    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    /**
     * @return The published
     */
    public String getPublished() {
        return published;
    }

    /**
     * @param published The published
     */
    public void setPublished(String published) {
        this.published = published;
    }

}