
package com.awecode.muscn.model.http.eplmatchweekdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompetitionYear {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("competition")
    @Expose
    private Competition competition;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CompetitionYear() {
    }

    /**
     * 
     * @param id
     * @param year
     * @param competition
     */
    public CompetitionYear(Integer id, Integer year, Competition competition) {
        super();
        this.id = id;
        this.year = year;
        this.competition = competition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
