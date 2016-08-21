package com.official.trialpassnepal.objects;

/**
 * Created by SlowhandJr. on 8/5/16.
 */
public class QuestionAnswer {

    private String question, answer;
    private boolean isCorrect;

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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
