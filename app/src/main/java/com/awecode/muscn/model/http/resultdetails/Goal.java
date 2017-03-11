
package com.awecode.muscn.model.http.resultdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Goal {

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
    private Integer scorer;
    @SerializedName("assist_by")
    @Expose
    private Integer assistBy;
    @SerializedName("match")
    @Expose
    private Integer match;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Goal() {
    }

    /**
     * 
     * @param id
     * @param time
     * @param scorer
     * @param ownGoal
     * @param assistBy
     * @param match
     * @param penalty
     */
    public Goal(Integer id, Boolean penalty, Boolean ownGoal, String time, Integer scorer, Integer assistBy, Integer match) {
        super();
        this.id = id;
        this.penalty = penalty;
        this.ownGoal = ownGoal;
        this.time = time;
        this.scorer = scorer;
        this.assistBy = assistBy;
        this.match = match;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getPenalty() {
        return penalty;
    }

    public void setPenalty(Boolean penalty) {
        this.penalty = penalty;
    }

    public Boolean getOwnGoal() {
        return ownGoal;
    }

    public void setOwnGoal(Boolean ownGoal) {
        this.ownGoal = ownGoal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getScorer() {
        return scorer;
    }

    public void setScorer(Integer scorer) {
        this.scorer = scorer;
    }

    public Integer getAssistBy() {
        return assistBy;
    }

    public void setAssistBy(Integer assistBy) {
        this.assistBy = assistBy;
    }

    public Integer getMatch() {
        return match;
    }

    public void setMatch(Integer match) {
        this.match = match;
    }

}
