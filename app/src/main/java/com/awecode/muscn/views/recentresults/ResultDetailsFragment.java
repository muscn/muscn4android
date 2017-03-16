package com.awecode.muscn.views.recentresults;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.ScorersAdapter;
import com.awecode.muscn.model.http.resultdetails.ResultDetailsResponse;
import com.awecode.muscn.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by surensth on 3/9/17.
 */

public class ResultDetailsFragment extends Fragment {


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
    @BindView(R.id.goalsTextView)
    TextView goalsTextView;
    @BindView(R.id.goalFirstTeamName)
    TextView goalFirstTeamName;
    @BindView(R.id.firstTeamRecyclerView)
    RecyclerView firstTeamRecyclerView;
    @BindView(R.id.goalSecondTeamName)
    TextView goalSecondTeamName;
    @BindView(R.id.secondTeamRecyclerView)
    RecyclerView secondTeamRecyclerView;
    @BindView(R.id.goals_main_layout)
    LinearLayout goalsMainLayout;

    LinearLayoutManager mLinearLayoutManager;
    ScorersAdapter mAdapter;

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
            leagueNameTextView.setText("League : " + response.getCompetitionName());
            Log.v("hehe", "league name " + response.getCompetitionName());
        }
        if (response.getVenue() != null)
            venueTextView.setText("Venue : " + response.getVenue());
        if (response.getBroadcastOn() != null || response.getBroadcastOn() != "")
            liveOnTextView.setText("Live on " + response.getBroadcastOn());
        else
            liveOnTextView.setText("");

        if (response.getDatetime() != null)
            dateTextView.setText("Date : " + Util.commonDateFormatter(response.getDatetime(), "yyyy-MM-dd'T'hh:mm:ss'Z'"));

        if (response.getData() != null) {
            Log.v("hehe", "data present " + response.getData());
            if (response.getData().getHtScore() != null)
                halfTimeScoreTextView.setText("Half Time Score: " + response.getData().getHtScore());
        }
//setting goal scored by which player
        if (response.getGoals() != null) {
            if (response.getIsHomeGame()) {
                setUpRecyclerView(firstTeamRecyclerView, response);
                goalFirstTeamName.setText(getString(R.string.manchester_united));
                goalSecondTeamName.setText(response.getOpponentName());
            } else {
                setUpRecyclerView(secondTeamRecyclerView, response);
                goalSecondTeamName.setText(getString(R.string.manchester_united));
                goalFirstTeamName.setText(response.getOpponentName());
            }

        }
    }

    private void setUpRecyclerView(RecyclerView mRecyclerView, ResultDetailsResponse mResultDetailsResponse) {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ScorersAdapter(getActivity(), mResultDetailsResponse);
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setNestedScrollingEnabled(false);

    }


    private void setData(ResultDetailsResponse response) {
        this.response = response;
    }

}
