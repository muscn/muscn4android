package com.awecode.muscn.views.recent_results;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.TimeLineAdapter;
import com.awecode.muscn.model.http.resultdetails.ResultDetailsResponse;
import com.awecode.muscn.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by surensth on 3/9/17.
 */

public class ResultDetailsFragment extends Fragment {

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
//    @BindView(R.id.goalsTextView)
//    TextView goalsTextView;
//    @BindView(R.id.goalFirstTeamName)
//    TextView goalFirstTeamName;
//    @BindView(R.id.firstTeamRecyclerView)
//    RecyclerView firstTeamRecyclerView;
//    @BindView(R.id.goalSecondTeamName)
//    TextView goalSecondTeamName;
//    @BindView(R.id.secondTeamRecyclerView)
//    RecyclerView secondTeamRecyclerView;
//    @BindView(R.id.goals_main_layout)
//    LinearLayout goalsMainLayout;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_details_description, container, false);
        ButterKnife.bind(this, view);
        return view;
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

//setting goal scored by which player
//        if (response.getGoals() != null) {
//            if (response.getIsHomeGame()) {
//                setUpRecyclerView(firstTeamRecyclerView, response);
//                goalFirstTeamName.setText(getString(R.string.manchester_united));
//                goalSecondTeamName.setText(response.getOpponentName());
//            } else {
//                setUpRecyclerView(secondTeamRecyclerView, response);
//                goalSecondTeamName.setText(getString(R.string.manchester_united));
//                goalFirstTeamName.setText(response.getOpponentName());
//            }
//
//        }
    }

    private void setUpRecyclerView(ResultDetailsResponse mResultDetailsResponse) {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        timelineRecyclerView.setLayoutManager(mLinearLayoutManager);
        timelineRecyclerView.setHasFixedSize(true);
        mAdapter = new TimeLineAdapter(mResultDetailsResponse);
        timelineRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setNestedScrollingEnabled(false);

    }


    private void setData(ResultDetailsResponse response) {
        this.response = response;
    }

}
