package com.official.trialpassnepal.objects;

/**
 * Created by Ferrari on 10/3/2015.
 */
public class AnswerObject {

    private String answer;
    private int optionsid;
    private boolean isCorrectAnswer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getOptionsid() {
        return optionsid;
    }

    public void setOptionsid(int optionsid) {
        this.optionsid = optionsid;
    }

    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }
}
