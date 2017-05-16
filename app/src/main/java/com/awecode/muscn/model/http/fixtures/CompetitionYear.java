
package com.awecode.muscn.model.http.fixtures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CompetitionYear extends RealmObject {

    @SerializedName("year")
    @Expose
    private Integer year;

    @SerializedName("competition")
    @Expose
    private Competition competition;

}
