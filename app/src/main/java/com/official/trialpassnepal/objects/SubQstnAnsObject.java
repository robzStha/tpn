package com.official.trialpassnepal.objects;

import java.io.Serializable;

/**
 * Created by SlowhandJr. on 7/17/16.
 */
public class SubQstnAnsObject implements Serializable {
    private int qid;
    private String question;
    private String answer;

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
