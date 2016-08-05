package com.official.trialpassnepal.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionOption {

    @SerializedName("optionsid")
    @Expose
    private String optionsid;
    @SerializedName("qid")
    @Expose
    private String qid;
    @SerializedName("ansOptions")
    @Expose
    private String ansOptions;
    @SerializedName("validOption")
    @Expose
    private String validOption;

    /**
     * @return The optionsid
     */
    public String getOptionsid() {
        return optionsid;
    }

    /**
     * @param optionsid The optionsid
     */
    public void setOptionsid(String optionsid) {
        this.optionsid = optionsid;
    }

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
     * @return The ansOptions
     */
    public String getAnsOptions() {
        return ansOptions;
    }

    /**
     * @param ansOptions The ansOptions
     */
    public void setAnsOptions(String ansOptions) {
        this.ansOptions = ansOptions;
    }

    /**
     * @return The validOption
     */
    public String getValidOption() {
        return validOption;
    }

    /**
     * @param validOption The validOption
     */
    public void setValidOption(String validOption) {
        this.validOption = validOption;
    }

}
