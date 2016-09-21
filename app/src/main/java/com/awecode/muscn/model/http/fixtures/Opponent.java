
package com.awecode.muscn.model.http.fixtures;

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
    private Object foundationDate;
    @SerializedName("crest")
    @Expose
    private Object crest;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("wiki")
    @Expose
    private Object wiki;
    @SerializedName("stadium")
    @Expose
    private Object stadium;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * @param shortName
     *     The short_name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 
     * @return
     *     The alternativeNames
     */
    public String getAlternativeNames() {
        return alternativeNames;
    }

    /**
     * 
     * @param alternativeNames
     *     The alternative_names
     */
    public void setAlternativeNames(String alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    /**
     * 
     * @return
     *     The nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 
     * @param nickName
     *     The nick_name
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 
     * @return
     *     The foundationDate
     */
    public Object getFoundationDate() {
        return foundationDate;
    }

    /**
     * 
     * @param foundationDate
     *     The foundation_date
     */
    public void setFoundationDate(Object foundationDate) {
        this.foundationDate = foundationDate;
    }

    /**
     * 
     * @return
     *     The crest
     */
    public Object getCrest() {
        return crest;
    }

    /**
     * 
     * @param crest
     *     The crest
     */
    public void setCrest(Object crest) {
        this.crest = crest;
    }

    /**
     * 
     * @return
     *     The color
     */
    public String getColor() {
        return color;
    }

    /**
     * 
     * @param color
     *     The color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * 
     * @return
     *     The wiki
     */
    public Object getWiki() {
        return wiki;
    }

    /**
     * 
     * @param wiki
     *     The wiki
     */
    public void setWiki(Object wiki) {
        this.wiki = wiki;
    }

    /**
     * 
     * @return
     *     The stadium
     */
    public Object getStadium() {
        return stadium;
    }

    /**
     * 
     * @param stadium
     *     The stadium
     */
    public void setStadium(Object stadium) {
        this.stadium = stadium;
    }

}
