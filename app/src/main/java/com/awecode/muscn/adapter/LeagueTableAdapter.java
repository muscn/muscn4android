package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.leaguetable.LeagueTableResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by surensth on 9/27/16.
 */

public class LeagueTableAdapter extends RecyclerView.Adapter<LeagueTableAdapter.LeagueViewHolder> {
    Context mContext;
    List<LeagueTableResponse> leagueList;

    public LeagueTableAdapter(Context mContext, List<LeagueTableResponse> leagueList) {
        this.mContext = mContext;
        this.leagueList = leagueList;
    }

    @Override
    public LeagueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.league_table_row, parent, false);
        return new LeagueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeagueViewHolder holder, int position) {
        final LeagueTableResponse leagueTableResponse = leagueList.get(position);

        holder.serialNumber.setText(leagueTableResponse.getPosition());
        holder.club.setText(leagueTableResponse.getName());
        holder.p.setText(leagueTableResponse.getP());
        holder.gd.setText(leagueTableResponse.getGd());
        holder.pts.setText(leagueTableResponse.getPts());

    }

    @Override
    public int getItemCount() {
        return leagueList.size();
    }

    public static class LeagueViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.serialNumber)
        TextView serialNumber;
        @BindView(R.id.club)
        TextView club;
        @BindView(R.id.p)
        TextView p;
        @BindView(R.id.gd)
        TextView gd;
        @BindView(R.id.pts)
        TextView pts;

        public LeagueViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
