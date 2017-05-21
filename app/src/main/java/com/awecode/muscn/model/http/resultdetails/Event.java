
package com.awecode.muscn.model.http.resultdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class Event  extends RealmObject{

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("m")
    @Expose
    private String m;
    @SerializedName("assist_by")
    @Expose
    private String assistBy;
    @SerializedName("scorer")
    @Expose
    private String scorer;
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("pen")
    @Expose
    private Boolean pen;
}
