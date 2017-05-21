
package com.awecode.muscn.model.http.resultdetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Getter;

@Getter
public class Data extends RealmObject{

    @SerializedName("ht_score")
    @Expose
    private String htScore;
    @SerializedName("events")
    @Expose
    private RealmList<Event> events;
    @SerializedName("minute")
    @Expose
    private String minute;
}
