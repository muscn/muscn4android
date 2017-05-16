
package com.awecode.muscn.model.http.fixtures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class Competition extends RealmObject{


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("order")
    @Expose
    private Integer order;

}
