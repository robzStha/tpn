package com.official.trialpassnepal.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyncData {

    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("msg")
    @Expose
    private String msg;

    /**
     * @return The updatedDate
     */
    public String getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate The updated_date
     */
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return The data
     */
    public Data getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * @return The code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}