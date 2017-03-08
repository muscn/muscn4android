
package com.awecode.muscn.model.http.eplmatchweekdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Opponent {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("alternative_names")
    @Expose
    private String alternativeNames;
    @SerializedName("nick_name")
    @Expose
    private String nickName;
    @SerializedName("foundation_date")
    @Expose
    private String foundationDate;
    @SerializedName("crest")
    @Expose
    private String crest;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("wiki")
    @Expose
    private String wiki;
    @SerializedName("stadium")
    @Expose
    private String stadium;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Opponent() {
    }

    /**
     * 
     * @param id
     * @param color
     * @param nickName
     * @param foundationDate
     * @param stadium
     * @param name
     * @param wiki
     * @param crest
     * @param shortName
     * @param alternativeNames
     */
    public Opponent(Integer id, String name, String shortName, String alternativeNames, String nickName, String foundationDate, String crest, String color, String wiki, String stadium) {
        super();
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.alternativeNames = alternativeNames;
        this.nickName = nickName;
        this.foundationDate = foundationDate;
        this.crest = crest;
        this.color = color;
        this.wiki = wiki;
        this.stadium = stadium;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(String alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(String foundationDate) {
        this.foundationDate = foundationDate;
    }

    public String getCrest() {
        return crest;
    }

    public void setCrest(String crest) {
        this.crest = crest;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

}
