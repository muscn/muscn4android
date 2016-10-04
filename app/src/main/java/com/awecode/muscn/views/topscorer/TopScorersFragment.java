package com.awecode.muscn.views.topscorer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.TopScorerAdapter;
import com.awecode.muscn.model.http.topscorers.TopScorersResponse;
import com.awecode.muscn.model.listener.RecyclerViewScrollListener;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
import com.awecode.muscn.views.HomeActivity;
import com.awecode.muscn.views.MasterFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by surensth on 9/28/16.
 */

public class TopScorersFragment extends MasterFragment {
    @BindView(R.id.topScorersRecyclerView)
    RecyclerView mRecyclerView;

    TopScorerAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    RecyclerViewScrollListener mRecyclerViewScrollListener;

    public static TopScorersFragment newInstance() {
        TopScorersFragment fragment = new TopScorersFragment();
        return fragment;
    }

    public TopScorersFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showProgressView(getString(R.string.loading_top_scorers));
        mRecyclerViewScrollListener = (RecyclerViewScrollListener) this.getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_scorers, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mActivity.setCustomTitle(R.string.top_scorers);
        if (Util.checkInternetConnection(mContext))
            requestTopScorers();
        else {
            ((HomeActivity) mContext).noInternetConnectionDialog(mContext);
        }
    }

    private void requestTopScorers() {
        MuscnApiInterface mApiInterface = getApiInterface();
        Observable<List<TopScorersResponse>> call = mApiInterface.getTopScorers();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TopScorersResponse>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        mActivity.showErrorView(e.getMessage() + ". Try again");
                        mActivity.noInternetConnectionDialog(mContext);
                    }

                    @Override
                    public void onNext(List<TopScorersResponse> topScorersResponses) {
                        setUpAdapter(topScorersResponses);
                    }
                });
    }
    private void setUpAdapter(List<TopScorersResponse> topScorersResponses){
        mAdapter = new TopScorerAdapter(mContext, topScorersResponses);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerViewScrollListener.onRecyclerViewScrolled(mRecyclerView);
    }
}
