package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.simplexml.Item;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by surensth on 9/28/16.
 */

public class NewsListAdapter extends RealmRecyclerViewAdapter<Item, NewsListAdapter.DataViewHolder> {
    Context mContext;

    public NewsListAdapter(OrderedRealmCollection<Item> data) {
        super(data, true);
    }


    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        final Item data = getItem(position);
        holder.titleTextView.setText(data.getTitle());
        holder.descriptionTextView.setText(data.getDescription());
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        @BindView(R.id.titleTextView)
        TextView titleTextView;
        @BindView(R.id.descriptionTextView)
        TextView descriptionTextView;
        @BindView(R.id.rowLayout)
        LinearLayout rowLayout;

        public DataViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
