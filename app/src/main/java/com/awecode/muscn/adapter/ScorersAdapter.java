package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.resultdetails.Goal;
import com.awecode.muscn.model.http.resultdetails.ResultDetailsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by surensth on 3/14/17.
 */

public class ScorersAdapter extends RecyclerView.Adapter<ScorersAdapter.ScorersViewHolder> {

    private Context mContext;
    private ResultDetailsResponse mResultDetailsResponse;

    public ScorersAdapter(Context context, ResultDetailsResponse mResultDetailsResponse) {
        this.mContext = context;
        this.mResultDetailsResponse = mResultDetailsResponse;
    }

    @Override
    public ScorersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scorer_row_item, parent, false);
        return new ScorersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScorersViewHolder holder, int position) {
        Goal mGoal = mResultDetailsResponse.getGoals().get(position);
        holder.scorerTextView.setText(mGoal.getScorer());
        holder.scoreTimeTextView.setText(mGoal.getTime().toString() + "'");

        if (mGoal.getAssistBy() != null) {
            holder.assistTextView.setVisibility(View.VISIBLE);
            holder.assistTextView.setText("[Assist: " + mGoal.getAssistBy()+ "]");
        }
        else
            holder.assistTextView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mResultDetailsResponse.getGoals().size();
    }

    public static class ScorersViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.scorerTextView)
        TextView scorerTextView;
        @BindView(R.id.scoreTimeTextView)
        TextView scoreTimeTextView;
        @BindView(R.id.assistTextView)
        TextView assistTextView;

        public ScorersViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
