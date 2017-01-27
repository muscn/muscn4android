
package com.awecode.muscn.model.http.fixtures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CompetitionYear {

    @SerializedName("year")
    @Expose
    private Integer year;

    @SerializedName("competition")
    @Expose
    private Competition competition;

    public CompetitionYear() {
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
