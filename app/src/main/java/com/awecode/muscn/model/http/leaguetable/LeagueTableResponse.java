
package com.awecode.muscn.model.http.leaguetable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class LeagueTableResponse extends RealmObject {

    @SerializedName("a")
    @Expose
    private String a;
    @SerializedName("d")
    @Expose
    private String d;
    @SerializedName("f")
    @Expose
    private String f;
    @SerializedName("l")
    @Expose
    private String l;
    @SerializedName("p")
    @Expose
    private String p;
    @SerializedName("live")
    @Expose
    private Boolean live;
    @SerializedName("gd")
    @Expose
    private String gd;
    @SerializedName("w")
    @Expose
    private String w;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("pts")
    @Expose
    private String pts;
    @SerializedName("name")
    @Expose
    private String name;

}
