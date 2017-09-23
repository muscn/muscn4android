package com.awecode.muscn.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.partners.PartnersResult;
import com.awecode.muscn.model.listener.PartnerClickListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by surensth on 9/28/16.
 */

public class PartnersAdapter extends RealmRecyclerViewAdapter<PartnersResult, PartnersAdapter.PartnersViewHolder> {
    Context mContext;
    public PartnerClickListener partnerClickListener;

    public PartnersAdapter(OrderedRealmCollection<PartnersResult> data) {
        super(data, true);
    }


    @Override
    public PartnersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.partners_row_item, parent, false);
        return new PartnersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartnersViewHolder holder, int position) {
        final PartnersResult partnersResult = getItem(position);
        if (partnersResult != null) {
            if (!TextUtils.isEmpty(partnersResult.getName()))
                holder.mPartnerNameTextView.setText(partnersResult.getName());
            if (!TextUtils.isEmpty(partnersResult.getPartnership()))
                holder.mPartnershipTextView.setText(partnersResult.getPartnership());
            if (!TextUtils.isEmpty(partnersResult.getLogo())) {
                Log.v("test","logo exists");
                Picasso.with(mContext).load(Uri.parse(partnersResult.getLogo())).into(holder.mPartnerLogo);
            }
            holder.mPartnerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (partnersResult.getUrl() != null)
                        partnerClickListener.onClickPartner(partnersResult.getUrl());
                }
            });
        }
    }

    public static class PartnersViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.partnershipTextView)
        TextView mPartnershipTextView;
        @BindView(R.id.partnerNameTextView)
        TextView mPartnerNameTextView;
        @BindView(R.id.partnerLogo)
        ImageView mPartnerLogo;
        @BindView(R.id.partnerLayout)
        LinearLayout mPartnerLayout;

        public PartnersViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
