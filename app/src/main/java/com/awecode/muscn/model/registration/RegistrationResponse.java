
package com.awecode.muscn.model.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("dev_id")
    @Expose
    private String devId;
    @SerializedName("reg_id")
    @Expose
    private String regId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("user")
    @Expose
    private Object user;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RegistrationResponse() {
    }

    /**
     * 
     * @param isActive
     * @param id
     * @param deviceType
     * @param name
     * @param devId
     * @param user
     * @param regId
     */
    public RegistrationResponse(Integer id, String devId, String regId, String name, Boolean isActive, String deviceType, Object user) {
        super();
        this.id = id;
        this.devId = devId;
        this.regId = regId;
        this.name = name;
        this.isActive = isActive;
        this.deviceType = deviceType;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

}
