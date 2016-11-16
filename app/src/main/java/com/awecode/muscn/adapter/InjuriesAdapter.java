package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.injuries.InjuriesResponse;
import com.awecode.muscn.model.http.injuries.Result;
import com.awecode.muscn.util.Constants;
import com.awecode.muscn.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by surensth on 9/28/16.
 */

public class InjuriesAdapter extends RecyclerView.Adapter<InjuriesAdapter.InjuriesViewHolder> {
    Context mContext;
    InjuriesResponse injuriesResponse;
    private String type;
    private String returnDate;
    private String playerName;

    public InjuriesAdapter(Context mContext, InjuriesResponse injuriesResponse) {
        this.mContext = mContext;
        this.injuriesResponse = injuriesResponse;
    }

    @Override
    public InjuriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.injuries_row_item, parent, false);
        return new InjuriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InjuriesViewHolder holder, int position) {
        final Result injuredResult = injuriesResponse.getResults().get(position);
        if (injuredResult != null) {
//       White divider line is hide if the table item is last
            if (position >= 0 && position == getItemCount() - 1)
                holder.tableRowDivider.setVisibility(View.GONE);
            else
                holder.tableRowDivider.setVisibility(View.VISIBLE);

            playerName = injuredResult.getPlayerName();
            if (playerName != null)
                holder.players.setText(playerName);
            else
                holder.players.setText(R.string.no_data_string);

            type = injuredResult.getType();
            if (type != null) {
                type = type.replaceAll("\\s+", " ");//removing spaces
                holder.injury.setText(type);
            } else
                holder.injury.setText(R.string.no_data_string);

            returnDate = injuredResult.getReturnDate();
            if (returnDate != null) {
                returnDate = Util.dateFormatter(returnDate);//formating date in format "28 Sep, 2016"
                holder.returnDate.setText(returnDate);
            } else
                holder.returnDate.setText(R.string.no_data_string);
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return injuriesResponse.getCount();
    }

    public static class InjuriesViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.players)
        TextView players;
        @BindView(R.id.injury)
        TextView injury;
        @BindView(R.id.returnDate)
        TextView returnDate;
        @BindView(R.id.table_row_divider)
        View tableRowDivider;

        public InjuriesViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
