
package com.awecode.muscn.model.http.fixtures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class LiveScreening extends RealmObject {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("privileges")
    @Expose
    private String privileges;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("location")
    @Expose
    private String location;

}
