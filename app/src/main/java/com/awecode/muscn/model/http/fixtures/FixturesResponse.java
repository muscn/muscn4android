
package com.awecode.muscn.model.http.fixtures;

import android.text.TextUtils;

import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.prefs.Prefs;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FixturesResponse {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList<Result>();


    /**
     * save fixtures
     *
     * @param fixturesResponse
     */
    public static void save_fixtures(FixturesResponse fixturesResponse) {
        try {
            Prefs.putString(Constants.PREFS_FIXTURES, new Gson().toJson(fixturesResponse).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FixturesResponse get_results() {
        FixturesResponse fixturesResponse = null;
        String saved_fixtures = Prefs.getString(Constants.PREFS_FIXTURES, "");
        if (!TextUtils.isEmpty(saved_fixtures))
            fixturesResponse = new Gson().fromJson(saved_fixtures, FixturesResponse.class);

        return fixturesResponse;

    }

    /**
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return The next
     */
    public String getNext() {
        return next;
    }

    /**
     * @param next The next
     */
    public void setNext(String next) {
        this.next = next;
    }

    /**
     * @return The previous
     */
    public Object getPrevious() {
        return previous;
    }

    /**
     * @param previous The previous
     */
    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    /**
     * @return The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

}
