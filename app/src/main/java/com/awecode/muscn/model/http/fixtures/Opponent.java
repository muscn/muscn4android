
package com.awecode.muscn.model.http.fixtures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Opponent extends RealmObject {

    @SerializedName("opponentId")
    @Expose
    private Integer opponentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("alternative_names")
    @Expose
    private String alternativeNames;
    @SerializedName("nick_name")
    @Expose
    private String nickName;
    @SerializedName("foundation_date")
    @Expose
    private String foundationDate;
    @SerializedName("crest")
    @Expose
    private String crest;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("wiki")
    @Expose
    private String wiki;
    @SerializedName("stadium")
    @Expose
    private String stadium;

}
