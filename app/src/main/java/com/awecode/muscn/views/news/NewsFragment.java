package com.awecode.muscn.views.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.awecode.muscn.R;
import com.awecode.muscn.adapter.NewsListAdapter;
import com.awecode.muscn.model.listener.NewsItemClickListener;
import com.awecode.muscn.model.simplexml.Item;
import com.awecode.muscn.model.simplexml.Rss;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.MasterFragment;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by munnadroid on 6/2/17.
 */

public class NewsFragment extends MasterFragment implements NewsItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RealmAsyncTask mTransaction;
    private int dbDataCount;
    private NewsListAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment_news;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity.setCustomTitle(R.string.news);

        initializeRecyclerView();
        checkInternetConnection();
    }

    /**
     * check internet, initialize request
     * if no internet, show message incase of empty db,
     * show saved data incase of data in db
     */
    private void checkInternetConnection() {
        dbDataCount = getTableDataCount(Item.class);
        if (dbDataCount > 1)
            setUpAdapter();

        if (Util.checkInternetConnection(mContext))
            requestManutdNewsFeed();
        else if (dbDataCount < 1)
            noInternetConnectionDialog();
    }


    private void requestManutdNewsFeed() {
        if (dbDataCount < 1)
            showProgressView(getString(R.string.loading_news));

        Observable<Rss> call = mFeedApiInterface.getManutdFeed();
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Rss>() {
                    @Override
                    public void onCompleted() {
                        mActivity.showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (dbDataCount < 1)
                            mActivity.noInternetConnectionDialog(mContext);
                    }

                    @Override
                    public void onNext(Rss rss) {
                        mActivity.showContentView();
                        if (rss != null
                                && rss.getChannel() != null) {
                            List<Item> itemList = rss.getChannel().getItem();
                            if (itemList != null
                                    && itemList.size() > 0)
                                deleteDbDataAndSave(itemList);
                        }

                    }
                });
    }


    /**
     * first delete the existing data from db
     */
    private void deleteDbDataAndSave(final List<Item> itemList) {
        try {
            //delete fixture results first
            mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.delete(Item.class);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    //now save fixture data
                    saveResponseData(itemList);
                    setUpAdapter();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * save data in db and return the arraylist for recyclerview adapter
     *
     * @return
     */
    private void saveResponseData(final List<Item> itemList) {
        Log.v("TAG", "saving the data: " + itemList.size());
        if (itemList != null && itemList.size() > 0) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(itemList);
            mRealm.commitTransaction();
        }
    }


    /**
     * populate leaguetable list in db
     */
    private void setUpAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            return;
        }
        mAdapter = new NewsListAdapter(mRealm.where(Item.class).findAll());
        mAdapter.mNewsItemClickListener=this;
        recyclerView.setAdapter(Util.getAnimationAdapter(mAdapter));
    }

    @Override
    public void onItemClickListener(Item item) {
        new FinestWebView.Builder(getActivity())
                .webViewBuiltInZoomControls(true)
                .show(item.getLink());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTransaction != null && !mTransaction.isCancelled())
            mTransaction.cancel();

    }

    public static Fragment newInstance() {
        return new NewsFragment();
    }

    private void initializeRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
    }
}
