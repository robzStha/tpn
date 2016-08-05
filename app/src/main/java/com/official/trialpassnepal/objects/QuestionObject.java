package com.official.trialpassnepal.objects;

import java.io.Serializable;

/**
 * Created by Ferrari on 10/3/2015.
 */
public class QuestionObject implements Serializable {

    private String question;
    private int qid;
    private String imgLoc;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getImgLoc() {
        return imgLoc;
    }

    public void setImgLoc(String imgLoc) {
        this.imgLoc = imgLoc;
    }
}
