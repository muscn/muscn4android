
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
    private Object nickName;
    @SerializedName("foundation_date")
    @Expose
    private Object foundationDate;
    @SerializedName("crest")
    @Expose
    private String crest;
    @SerializedName("color")
    @Expose
    private Object color;
    @SerializedName("wiki")
    @Expose
    private String wiki;
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
    public Object getNickName() {
        return nickName;
    }

    /**
     * 
     * @param nickName
     *     The nick_name
     */
    public void setNickName(Object nickName) {
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
    public String getCrest() {
        return crest;
    }

    /**
     * 
     * @param crest
     *     The crest
     */
    public void setCrest(String crest) {
        this.crest = crest;
    }

    /**
     * 
     * @return
     *     The color
     */
    public Object getColor() {
        return color;
    }

    /**
     * 
     * @param color
     *     The color
     */
    public void setColor(Object color) {
        this.color = color;
    }

    /**
     * 
     * @return
     *     The wiki
     */
    public String getWiki() {
        return wiki;
    }

    /**
     * 
     * @param wiki
     *     The wiki
     */
    public void setWiki(String wiki) {
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
