package com.awecode.muscn.views.injuries;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.InjuriesAdapter;
import com.awecode.muscn.adapter.TopScorerAdapter;
import com.awecode.muscn.model.http.injuries.InjuriesResponse;
import com.awecode.muscn.model.http.topscorers.TopScorersResponse;
import com.awecode.muscn.util.retrofit.MuscnApiInterface;
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

public class InjuriesFragment extends MasterFragment {
    @BindView(R.id.injuriesRecyclerView)
    RecyclerView mRecyclerView;

    InjuriesAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;

    public static InjuriesFragment newInstance() {
        InjuriesFragment fragment = new InjuriesFragment();
        return fragment;
    }

    public InjuriesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showProgressView(getString(R.string.loading_injuries));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_injuries, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mActivity.setCustomTitle(R.string.injuries);
        requestInjuries();
    }

    private void requestInjuries() {
        MuscnApiInterface mApiInterface = getApiInterface();
        Observable<InjuriesResponse> call = mApiInterface.getInjuredPlayers();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InjuriesResponse>() {
                    @Override
                    public void onCompleted() {
                        mActivity.showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.showErrorView(e.getMessage() + ". Try again");
                    }

                    @Override
                    public void onNext(InjuriesResponse injuriesResponse) {
                        setUpAdapter(injuriesResponse);
                    }
                });
    }

    private void setUpAdapter(InjuriesResponse injuriesResponse) {
        mAdapter = new InjuriesAdapter(mContext, injuriesResponse);
        mRecyclerView.setAdapter(mAdapter);
    }

}
