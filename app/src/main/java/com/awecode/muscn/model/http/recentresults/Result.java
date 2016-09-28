
package com.awecode.muscn.model.http.recentresults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("is_home_game")
    @Expose
    private Boolean isHomeGame;
    @SerializedName("opponent_name")
    @Expose
    private String opponentName;
    @SerializedName("mufc_score")
    @Expose
    private Integer mufcScore;
    @SerializedName("opponent_score")
    @Expose
    private Integer opponentScore;

    /**
     * 
     * @return
     *     The isHomeGame
     */
    public Boolean getIsHomeGame() {
        return isHomeGame;
    }

    /**
     * 
     * @param isHomeGame
     *     The is_home_game
     */
    public void setIsHomeGame(Boolean isHomeGame) {
        this.isHomeGame = isHomeGame;
    }

    /**
     * 
     * @return
     *     The opponentName
     */
    public String getOpponentName() {
        return opponentName;
    }

    /**
     * 
     * @param opponentName
     *     The opponent_name
     */
    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    /**
     * 
     * @return
     *     The mufcScore
     */
    public Integer getMufcScore() {
        return mufcScore;
    }

    /**
     * 
     * @param mufcScore
     *     The mufc_score
     */
    public void setMufcScore(Integer mufcScore) {
        this.mufcScore = mufcScore;
    }

    /**
     * 
     * @return
     *     The opponentScore
     */
    public Integer getOpponentScore() {
        return opponentScore;
    }

    /**
     * 
     * @param opponentScore
     *     The opponent_score
     */
    public void setOpponentScore(Integer opponentScore) {
        this.opponentScore = opponentScore;
    }

}
