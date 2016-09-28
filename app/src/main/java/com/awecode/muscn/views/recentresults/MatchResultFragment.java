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

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.MatchResultExpandableRecyclerviewAdapter;
import com.awecode.muscn.model.Item;
import com.awecode.muscn.model.http.recentresults.RecentResultsResponse;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.awecode.muscn.views.MasterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by suresh on 9/26/16.
 */

public class MatchResultFragment extends MasterFragment {
    @BindView(R.id.matchResultRecyclerview)
    RecyclerView mMatchResultRecyclerview;

    private List<Item> data;
    private MatchResultExpandableRecyclerviewAdapter mMatchResultExpandableRecyclerviewAdapter;
    private MuscnApiInterface mApiInterface;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.match_result_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = new ArrayList<Item>();
        mActivity.setCustomTitle(R.string.recent_results);
        showProgressView(getString(R.string.loading_recent_results));
        requestMatchResults();
    }

    /**
     * populate match result, opponent name and match score
     */
    public void setupMatchResultRecyclerview(){
        mMatchResultRecyclerview.setHasFixedSize(true);
        mMatchResultRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMatchResultExpandableRecyclerviewAdapter = new MatchResultExpandableRecyclerviewAdapter(getActivity(),data);
        mMatchResultRecyclerview.setAdapter(mMatchResultExpandableRecyclerviewAdapter);
    }


    /**
     * fetch manutd recent match results list
     */
    public void requestMatchResults(){
        mApiInterface = ServiceGenerator.createService(MuscnApiInterface.class);
        Observable<RecentResultsResponse> call = mApiInterface.getResults();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecentResultsResponse>() {
                    @Override
                    public void onCompleted() {
                        mActivity.showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.showErrorView(e.getMessage() + ". Try again");
                    }

                    @Override
                    public void onNext(RecentResultsResponse fixturesResponse) {
                        for (int i=0;i<fixturesResponse.getResults().size();i++) {
                            Item demand = new Item(getActivity(), MatchResultExpandableRecyclerviewAdapter.HEADER, fixturesResponse);
                            demand.invisibleChildren = new ArrayList<>();
                            demand.invisibleChildren.add(new Item(getActivity(), MatchResultExpandableRecyclerviewAdapter.CHILD));
                            data.add(demand);
                        }
                        setupMatchResultRecyclerview();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
