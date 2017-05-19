
package com.awecode.muscn.model.http.injuries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class InjuryResult extends RealmObject{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("player_name")
    @Expose
    private String playerName;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("injury_date")
    @Expose
    private String injuryDate;
    @SerializedName("return_date")
    @Expose
    private String returnDate;
    @SerializedName("return_date_confirmed")
    @Expose
    private Boolean returnDateConfirmed;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("player")
    @Expose
    private Integer player;
}
