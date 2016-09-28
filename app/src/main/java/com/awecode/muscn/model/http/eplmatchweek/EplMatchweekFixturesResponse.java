
package com.awecode.muscn.model.http.eplmatchweek;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EplMatchweekFixturesResponse {

    @SerializedName("2016-09-24")
    @Expose
    private List<com.awecode.muscn.model.http.eplmatchweek._20160924> _20160924 = new ArrayList<com.awecode.muscn.model.http.eplmatchweek._20160924>();
    @SerializedName("2016-09-27")
    @Expose
    private List<com.awecode.muscn.model.http.eplmatchweek._20160927> _20160927 = new ArrayList<com.awecode.muscn.model.http.eplmatchweek._20160927>();
    @SerializedName("2016-09-25")
    @Expose
    private List<com.awecode.muscn.model.http.eplmatchweek._20160925> _20160925 = new ArrayList<com.awecode.muscn.model.http.eplmatchweek._20160925>();

    /**
     * 
     * @return
     *     The _20160924
     */
    public List<com.awecode.muscn.model.http.eplmatchweek._20160924> get20160924() {
        return _20160924;
    }

    /**
     * 
     * @param _20160924
     *     The 2016-09-24
     */
    public void set20160924(List<com.awecode.muscn.model.http.eplmatchweek._20160924> _20160924) {
        this._20160924 = _20160924;
    }

    /**
     * 
     * @return
     *     The _20160927
     */
    public List<com.awecode.muscn.model.http.eplmatchweek._20160927> get20160927() {
        return _20160927;
    }

    /**
     * 
     * @param _20160927
     *     The 2016-09-27
     */
    public void set20160927(List<com.awecode.muscn.model.http.eplmatchweek._20160927> _20160927) {
        this._20160927 = _20160927;
    }

    /**
     * 
     * @return
     *     The _20160925
     */
    public List<com.awecode.muscn.model.http.eplmatchweek._20160925> get20160925() {
        return _20160925;
    }

    /**
     * 
     * @param _20160925
     *     The 2016-09-25
     */
    public void set20160925(List<com.awecode.muscn.model.http.eplmatchweek._20160925> _20160925) {
        this._20160925 = _20160925;
    }

}
