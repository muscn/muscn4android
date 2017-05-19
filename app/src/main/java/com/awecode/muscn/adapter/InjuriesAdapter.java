package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.injuries.InjuryResult;
import com.awecode.muscn.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by surensth on 9/28/16.
 */

public class InjuriesAdapter extends RealmRecyclerViewAdapter<InjuryResult, InjuriesAdapter.InjuriesViewHolder> {
    Context mContext;

    private String type;
    private String returnDate;
    private String playerName;

    public InjuriesAdapter(OrderedRealmCollection<InjuryResult> data) {
        super(data, true);
    }


    @Override
    public InjuriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.injuries_row_item, parent, false);
        return new InjuriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InjuriesViewHolder holder, int position) {
        final InjuryResult injuredInjuryResult = getItem(position);
        if (injuredInjuryResult != null) {
//       White divider line is hide if the table item is last
            if (position >= 0 && position == getItemCount() - 1)
                holder.tableRowDivider.setVisibility(View.GONE);
            else
                holder.tableRowDivider.setVisibility(View.VISIBLE);

            playerName = injuredInjuryResult.getPlayerName();
            if (playerName != null)
                holder.players.setText(playerName);
            else
                holder.players.setText(R.string.no_data_string);

            type = injuredInjuryResult.getType();
            if (type != null) {
                type = type.replaceAll("\\s+", " ");//removing spaces
                holder.injury.setText(type);
            } else
                holder.injury.setText(R.string.no_data_string);

            returnDate = injuredInjuryResult.getReturnDate();
            if (returnDate != null) {
                returnDate = Util.dateFormatter(returnDate);//formating date in format "28 Sep, 2016"
                holder.returnDate.setText(returnDate);
            } else
                holder.returnDate.setText(R.string.no_data_string);
        } else {
            return;
        }
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
