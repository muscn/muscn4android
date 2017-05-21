
package com.awecode.muscn.model.http.resultdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class Goal extends RealmObject{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("penalty")
    @Expose
    private Boolean penalty;
    @SerializedName("own_goal")
    @Expose
    private Boolean ownGoal;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("scorer")
    @Expose
    private String scorer;
    @SerializedName("assist_by")
    @Expose
    private String assistBy;
    @SerializedName("match")
    @Expose
    private Integer match;

}
