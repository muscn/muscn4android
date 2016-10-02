
package com.awecode.muscn.model.http.eplmatchweek;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EplMatchweekFixturesResponse  {

    @SerializedName("2016-10-02")
    @Expose
    private List<com.awecode.muscn.model.http.eplmatchweek._20161002> _20161002 = new ArrayList<com.awecode.muscn.model.http.eplmatchweek._20161002>();
    @SerializedName("2016-10-01")
    @Expose
    private List<com.awecode.muscn.model.http.eplmatchweek._20161001> _20161001 = new ArrayList<com.awecode.muscn.model.http.eplmatchweek._20161001>();

    /**
     * 
     * @return
     *     The _20161002
     */
    public List<com.awecode.muscn.model.http.eplmatchweek._20161002> get20161002() {
        return _20161002;
    }

    /**
     * 
     * @param _20161002
     *     The 2016-10-02
     */
    public void set20161002(List<com.awecode.muscn.model.http.eplmatchweek._20161002> _20161002) {
        this._20161002 = _20161002;
    }

    /**
     * 
     * @return
     *     The _20161001
     */
    public List<com.awecode.muscn.model.http.eplmatchweek._20161001> get20161001() {
        return _20161001;
    }

    /**
     * 
     * @param _20161001
     *     The 2016-10-01
     */
    public void set20161001(List<com.awecode.muscn.model.http.eplmatchweek._20161001> _20161001) {
        this._20161001 = _20161001;
    }

}
