
package com.awecode.muscn.model.http.resultdetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("ht_score")
    @Expose
    private String htScore;
    @SerializedName("events")
    @Expose
    private List<Event> events = null;
    @SerializedName("minute")
    @Expose
    private String minute;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param minute
     * @param events
     * @param htScore
     */
    public Data(String htScore, List<Event> events, String minute) {
        super();
        this.htScore = htScore;
        this.events = events;
        this.minute = minute;
    }

    public String getHtScore() {
        return htScore;
    }

    public void setHtScore(String htScore) {
        this.htScore = htScore;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

}
