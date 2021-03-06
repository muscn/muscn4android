package com.awecode.muscn.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.top_scorers.TopScorersResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by surensth on 9/28/16.
 */

public class TopScorerAdapter extends RealmRecyclerViewAdapter<TopScorersResponse, TopScorerAdapter.TopScorersViewHolder> {

    String players, goals;

    public TopScorerAdapter(@Nullable OrderedRealmCollection<TopScorersResponse> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }


    @Override
    public TopScorersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_scorers_row_item, parent, false);
        return new TopScorersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopScorersViewHolder holder, int position) {
        final TopScorersResponse topScorersResponse = getItem(position);
//       White divider line is hide if the table item is last
        if (topScorersResponse != null) {
            if (position == getItemCount() - 1)
                holder.tableRowDivider.setVisibility(View.GONE);
            else
                holder.tableRowDivider.setVisibility(View.VISIBLE);
            players = topScorersResponse.getName();
            goals = topScorersResponse.getScore().toString();

            if (players != null)
                holder.players.setText(players);
            else
                holder.players.setText(R.string.no_data_string);

            if (goals != null)
                holder.goals.setText(goals);
            else
                holder.goals.setText(R.string.no_data_string);

        } else
            return;
    }


    public static class TopScorersViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.players)
        TextView players;
        @BindView(R.id.goals)
        TextView goals;
        @BindView(R.id.table_row_divider)
        View tableRowDivider;

        public TopScorersViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
