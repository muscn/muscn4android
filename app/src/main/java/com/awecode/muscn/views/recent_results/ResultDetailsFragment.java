package com.awecode.muscn.views.recent_results;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.TimeLineAdapter;
import com.awecode.muscn.model.http.resultdetails.ResultDetailsResponse;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.MasterFragment;

import butterknife.BindView;

/**
 * Created by surensth on 3/9/17.
 */

public class ResultDetailsFragment extends MasterFragment {

    private static final String TAG = "ResultDetailsFragment";

    ResultDetailsResponse response;
    @BindView(R.id.leagueNameTextView)
    TextView leagueNameTextView;
    @BindView(R.id.venueTextView)
    TextView venueTextView;
    @BindView(R.id.liveOnTextView)
    TextView liveOnTextView;
    @BindView(R.id.dateTextView)
    TextView dateTextView;
    @BindView(R.id.halfTimeScoreTextView)
    TextView halfTimeScoreTextView;
    @BindView(R.id.halfTimeLayout)
    LinearLayout halfTimeLayout;
    @BindView(R.id.liveOnLayout)
    LinearLayout liveOnLayout;
    LinearLayoutManager mLinearLayoutManager;
    TimeLineAdapter mAdapter;
    @BindView(R.id.timelineRecyclerView)
    RecyclerView timelineRecyclerView;

    public static ResultDetailsFragment newInstance(ResultDetailsResponse response) {
        ResultDetailsFragment fragment = new ResultDetailsFragment();
        fragment.setData(response);
        return fragment;
    }

    public ResultDetailsFragment() {

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_result_details_description;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setUpData(response);
    }

    private void setUpData(ResultDetailsResponse response) {
        if (response.getCompetitionName() != null) {
            leagueNameTextView.setText(response.getCompetitionName());
        }
        if (response.getVenue() != null)
            venueTextView.setText(response.getVenue());
        if (!TextUtils.isEmpty(response.getBroadcastOn())) {
            liveOnLayout.setVisibility(View.VISIBLE);
            liveOnTextView.setText(response.getBroadcastOn());
        } else
            liveOnLayout.setVisibility(View.GONE);


        if (response.getDatetime() != null)
            dateTextView.setText(Util.commonDateFormatter(response.getDatetime(), "yyyy-MM-dd'T'hh:mm:ss'Z'"));

        if (response.getData() != null) {
            if (response.getData().getHtScore() != null) {
                halfTimeScoreTextView.setText("Half Time Score: " + response.getData().getHtScore());
                halfTimeLayout.setVisibility(View.VISIBLE);
            } else
                halfTimeLayout.setVisibility(View.GONE);

            setUpRecyclerView(response);
        } else
            halfTimeLayout.setVisibility(View.GONE);

    }

    private void setUpRecyclerView(ResultDetailsResponse mResultDetailsResponse) {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        timelineRecyclerView.setLayoutManager(mLinearLayoutManager);
        timelineRecyclerView.setHasFixedSize(true);
        mAdapter = new TimeLineAdapter(mResultDetailsResponse);
        timelineRecyclerView.setAdapter(mAdapter);

    }


    private void setData(ResultDetailsResponse response) {
        this.response = response;
    }

}
