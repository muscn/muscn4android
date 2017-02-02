package com.awecode.muscn.model.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationPostData {

    @SerializedName("dev_id")
    @Expose
    private String devId;
    @SerializedName("reg_id")
    @Expose
    private String regId;
    @SerializedName("name")
    @Expose
    private String name;

    private String type;

    /**
     * No args constructor for use in serialization
     */
    public RegistrationPostData() {
    }

    /**
     * @param type
     * @param name
     * @param devId
     * @param regId
     */
    public RegistrationPostData(String devId, String regId, String name, String type) {
        super();
        this.devId = devId;
        this.regId = regId;
        this.name = name;
        this.type = type;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
