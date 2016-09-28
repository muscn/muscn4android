
package com.awecode.muscn.model.http.leaguetable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeagueTableResponse {

    @SerializedName("a")
    @Expose
    private String a;
    @SerializedName("d")
    @Expose
    private String d;
    @SerializedName("f")
    @Expose
    private String f;
    @SerializedName("l")
    @Expose
    private String l;
    @SerializedName("p")
    @Expose
    private String p;
    @SerializedName("live")
    @Expose
    private Boolean live;
    @SerializedName("gd")
    @Expose
    private String gd;
    @SerializedName("w")
    @Expose
    private String w;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("pts")
    @Expose
    private String pts;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LeagueTableResponse() {
    }

    /**
     * 
     * @param position
     * @param w
     * @param f
     * @param d
     * @param name
     * @param p
     * @param a
     * @param gd
     * @param l
     * @param pts
     * @param live
     */
    public LeagueTableResponse(String a, String d, String f, String l, String p, Boolean live, String gd, String w, String position, String pts, String name) {
        this.a = a;
        this.d = d;
        this.f = f;
        this.l = l;
        this.p = p;
        this.live = live;
        this.gd = gd;
        this.w = w;
        this.position = position;
        this.pts = pts;
        this.name = name;
    }

    /**
     * 
     * @return
     *     The a
     */
    public String getA() {
        return a;
    }

    /**
     * 
     * @param a
     *     The a
     */
    public void setA(String a) {
        this.a = a;
    }

    /**
     * 
     * @return
     *     The d
     */
    public String getD() {
        return d;
    }

    /**
     * 
     * @param d
     *     The d
     */
    public void setD(String d) {
        this.d = d;
    }

    /**
     * 
     * @return
     *     The f
     */
    public String getF() {
        return f;
    }

    /**
     * 
     * @param f
     *     The f
     */
    public void setF(String f) {
        this.f = f;
    }

    /**
     * 
     * @return
     *     The l
     */
    public String getL() {
        return l;
    }

    /**
     * 
     * @param l
     *     The l
     */
    public void setL(String l) {
        this.l = l;
    }

    /**
     * 
     * @return
     *     The p
     */
    public String getP() {
        return p;
    }

    /**
     * 
     * @param p
     *     The p
     */
    public void setP(String p) {
        this.p = p;
    }

    /**
     * 
     * @return
     *     The live
     */
    public Boolean getLive() {
        return live;
    }

    /**
     * 
     * @param live
     *     The live
     */
    public void setLive(Boolean live) {
        this.live = live;
    }

    /**
     * 
     * @return
     *     The gd
     */
    public String getGd() {
        return gd;
    }

    /**
     * 
     * @param gd
     *     The gd
     */
    public void setGd(String gd) {
        this.gd = gd;
    }

    /**
     * 
     * @return
     *     The w
     */
    public String getW() {
        return w;
    }

    /**
     * 
     * @param w
     *     The w
     */
    public void setW(String w) {
        this.w = w;
    }

    /**
     * 
     * @return
     *     The position
     */
    public String getPosition() {
        return position;
    }

    /**
     * 
     * @param position
     *     The position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 
     * @return
     *     The pts
     */
    public String getPts() {
        return pts;
    }

    /**
     * 
     * @param pts
     *     The pts
     */
    public void setPts(String pts) {
        this.pts = pts;
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

}
