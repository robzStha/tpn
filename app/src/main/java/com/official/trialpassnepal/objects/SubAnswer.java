package com.official.trialpassnepal.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubAnswer {

    @SerializedName("qid")
    @Expose
    private String qid;
    @SerializedName("answer")
    @Expose
    private String answer;

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
     * @return The answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer The answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

}