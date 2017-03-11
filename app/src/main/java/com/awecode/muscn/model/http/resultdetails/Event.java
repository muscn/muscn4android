
package com.awecode.muscn.model.http.resultdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("m")
    @Expose
    private String m;
    @SerializedName("assist_by")
    @Expose
    private String assistBy;
    @SerializedName("scorer")
    @Expose
    private String scorer;
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("pen")
    @Expose
    private Boolean pen;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Event() {
    }

    /**
     * 
     * @param pen
     * @param text
     * @param scorer
     * @param assistBy
     * @param team
     * @param m
     * @param type
     */
    public Event(String text, String m, String assistBy, String scorer, String team, String type, Boolean pen) {
        super();
        this.text = text;
        this.m = m;
        this.assistBy = assistBy;
        this.scorer = scorer;
        this.team = team;
        this.type = type;
        this.pen = pen;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getAssistBy() {
        return assistBy;
    }

    public void setAssistBy(String assistBy) {
        this.assistBy = assistBy;
    }

    public String getScorer() {
        return scorer;
    }

    public void setScorer(String scorer) {
        this.scorer = scorer;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getPen() {
        return pen;
    }

    public void setPen(Boolean pen) {
        this.pen = pen;
    }

}
