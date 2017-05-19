
package com.awecode.muscn.model.http.injuries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("player_name")
    @Expose
    private String playerName;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("injury_date")
    @Expose
    private String injuryDate;
    @SerializedName("return_date")
    @Expose
    private String returnDate;
    @SerializedName("return_date_confirmed")
    @Expose
    private Boolean returnDateConfirmed;
    @SerializedName("remarks")
    @Expose
    private Object remarks;
    @SerializedName("player")
    @Expose
    private Integer player;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Result() {
    }

    /**
     * 
     * @param id
     * @param injuryDate
     * @param player
     * @param playerName
     * @param remarks
     * @param returnDateConfirmed
     * @param type
     * @param returnDate
     */
    public Result(Integer id, String playerName, String type, String injuryDate, String returnDate, Boolean returnDateConfirmed, Object remarks, Integer player) {
        this.id = id;
        this.playerName = playerName;
        this.type = type;
        this.injuryDate = injuryDate;
        this.returnDate = returnDate;
        this.returnDateConfirmed = returnDateConfirmed;
        this.remarks = remarks;
        this.player = player;
    }

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
     *     The playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * 
     * @param playerName
     *     The player_name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The injuryDate
     */
    public String getInjuryDate() {
        return injuryDate;
    }

    /**
     * 
     * @param injuryDate
     *     The injury_date
     */
    public void setInjuryDate(String injuryDate) {
        this.injuryDate = injuryDate;
    }

    /**
     * 
     * @return
     *     The returnDate
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * 
     * @param returnDate
     *     The return_date
     */
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * 
     * @return
     *     The returnDateConfirmed
     */
    public Boolean getReturnDateConfirmed() {
        return returnDateConfirmed;
    }

    /**
     * 
     * @param returnDateConfirmed
     *     The return_date_confirmed
     */
    public void setReturnDateConfirmed(Boolean returnDateConfirmed) {
        this.returnDateConfirmed = returnDateConfirmed;
    }

    /**
     * 
     * @return
     *     The remarks
     */
    public Object getRemarks() {
        return remarks;
    }

    /**
     * 
     * @param remarks
     *     The remarks
     */
    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    /**
     * 
     * @return
     *     The player
     */
    public Integer getPlayer() {
        return player;
    }

    /**
     * 
     * @param player
     *     The player
     */
    public void setPlayer(Integer player) {
        this.player = player;
    }

}
