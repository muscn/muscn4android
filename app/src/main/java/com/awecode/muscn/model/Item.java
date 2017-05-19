package com.awecode.muscn.model;

import android.content.Context;

import com.awecode.muscn.model.http.recent_results.RecentResultsResponse;

import java.util.List;

/**
 * Created by suresh on 4/8/16.
 */
public class Item {
    public Context context;
    public  int type;
    public  boolean isExpanded;
    public List<Item> invisibleChildren;
    private RecentResultsResponse recentResultsResponse;

    public RecentResultsResponse getMatchResultResponse() {
        return recentResultsResponse;
    }

    public void setMatchResultResponse(RecentResultsResponse recentResultsResponse) {
        this.recentResultsResponse = recentResultsResponse;
    }



    public Item(Context cntxt, int type) {
        this.type = type;
        this.context = cntxt;
    }

    public Item(Context cntxt, int type, RecentResultsResponse matchResultResponse) {
        this.type = type;
        this.context = cntxt;
        this.recentResultsResponse = matchResultResponse;
    }



    public void setIsExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    public boolean isExpanded() {
        return isExpanded;
    }
}
