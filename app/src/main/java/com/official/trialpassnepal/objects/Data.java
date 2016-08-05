package com.official.trialpassnepal.objects;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("category")
    @Expose
    private List<Category> category = new ArrayList<Category>();
    @SerializedName("questions")
    @Expose
    private List<Question> questions = new ArrayList<Question>();
    @SerializedName("sub_answers")
    @Expose
    private List<SubAnswer> subAnswers = new ArrayList<SubAnswer>();
    @SerializedName("question_options")
    @Expose
    private List<QuestionOption> questionOptions = new ArrayList<QuestionOption>();
    @SerializedName("question_images")
    @Expose
    private List<QuestionImage> questionImages = new ArrayList<QuestionImage>();
    @SerializedName("driving_centers")
    @Expose
    private List<DrivingCenter> drivingCenters = new ArrayList<DrivingCenter>();

    /**
     * @return The category
     */
    public List<Category> getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    public void setCategory(List<Category> category) {
        this.category = category;
    }

    /**
     * @return The questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * @param questions The questions
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * @return The subAnswers
     */
    public List<SubAnswer> getSubAnswers() {
        return subAnswers;
    }

    /**
     * @param subAnswers The sub_answers
     */
    public void setSubAnswers(List<SubAnswer> subAnswers) {
        this.subAnswers = subAnswers;
    }

    /**
     * @return The questionOptions
     */
    public List<QuestionOption> getQuestionOptions() {
        return questionOptions;
    }

    /**
     * @param questionOptions The question_options
     */
    public void setQuestionOptions(List<QuestionOption> questionOptions) {
        this.questionOptions = questionOptions;
    }

    /**
     * @return The questionImages
     */
    public List<QuestionImage> getQuestionImages() {
        return questionImages;
    }

    /**
     * @param questionImages The question_images
     */
    public void setQuestionImages(List<QuestionImage> questionImages) {
        this.questionImages = questionImages;
    }

    /**
     * @return The drivingCenters
     */
    public List<DrivingCenter> getDrivingCenters() {
        return drivingCenters;
    }

    /**
     * @param drivingCenters The driving_centers
     */
    public void setDrivingCenters(List<DrivingCenter> drivingCenters) {
        this.drivingCenters = drivingCenters;
    }

}