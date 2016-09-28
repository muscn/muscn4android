package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.topscorers.TopScorersResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by surensth on 9/28/16.
 */

public class TopScorerAdapter extends RecyclerView.Adapter<TopScorerAdapter.TopScorersViewHolder> {
    Context mContext;
    List<TopScorersResponse> topScorersList;


    public TopScorerAdapter(Context mContext, List<TopScorersResponse> topScorersList) {
        this.mContext = mContext;
        this.topScorersList = topScorersList;
    }

    @Override
    public TopScorersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_scorers_row_item, parent, false);
        return new TopScorersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopScorersViewHolder holder, int position) {
        final TopScorersResponse topScorersResponse = topScorersList.get(position);
//       White divider line is hide if the table item is last
        if (position == getItemCount() - 1) {
            holder.tableRowDivider.setVisibility(View.GONE);
        }
        holder.players.setText(topScorersResponse.getName());
        holder.goals.setText(topScorersResponse.getScore().toString());
    }

    @Override
    public int getItemCount() {
        return topScorersList.size();
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
