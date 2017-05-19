
package com.awecode.muscn.model.http.top_scorers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class TopScorersResponse extends RealmObject {

    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("name")
    @Expose
    private String name;

}
