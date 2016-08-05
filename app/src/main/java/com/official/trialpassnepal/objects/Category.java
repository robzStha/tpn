package com.official.trialpassnepal.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("catid")
    @Expose
    private String catid;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("categoryDescription")
    @Expose
    private String categoryDescription;
    @SerializedName("published")
    @Expose
    private String published;

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
     * @return The categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName The categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return The categoryDescription
     */
    public String getCategoryDescription() {
        return categoryDescription;
    }

    /**
     * @param categoryDescription The categoryDescription
     */
    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
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
