//package com.awecode.muscn.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.awecode.muscn.R;
//import com.awecode.muscn.model.http.eplmatchweek.EplMatchweekFixturesResponse;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by suresh on 9/25/16.
// */
//
//public class MatchFixutreRecyclerviewAdapter extends RecyclerView.Adapter<MatchFixutreRecyclerviewAdapter.FixtureViewHolder> {
//    private Context context;
//    private EplMatchweekFixturesResponse eplMatchweekFixturesResponse;
//
//    public MatchFixutreRecyclerviewAdapter(Context context, EplMatchweekFixturesResponse eplMatchweekFixturesResponse){
//        this.context = context;
//        this.eplMatchweekFixturesResponse = eplMatchweekFixturesResponse;
//    }
//
//    @Override
//    public FixtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_fixture_row_item, parent,false);
//        FixtureViewHolder albumViewHolder = new FixtureViewHolder(view);
//        return albumViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(FixtureViewHolder holder, int position) {
//
////        if (position<eplMatchweekFixturesResponse.get20160924().size()){
//            Log.v("TEST","inside bind 24");
//            holder.fixtureHomeTeam.setText(eplMatchweekFixturesResponse.get20160924().get(position).getHomeTeam());
//            holder.fixtureAwayTeam.setText(eplMatchweekFixturesResponse.get20160924().get(position).getAwayTeam());
//            holder.matchScore.setText(eplMatchweekFixturesResponse.get20160924().get(position).getScore());
////        }
////        if (position >=eplMatchweekFixturesResponse.get20160924().size()-1 && position<(eplMatchweekFixturesResponse.get20160924().size()+eplMatchweekFixturesResponse.get20160925().size())){
////            Log.v("TEST","inside bind 25: "+position);
////            int size = eplMatchweekFixturesResponse.get20160924().size();
////            holder.fixtureHomeTeam.setText(eplMatchweekFixturesResponse.get20160925().get(size-position-1).getHomeTeam());
////            holder.fixtureAwayTeam.setText(eplMatchweekFixturesResponse.get20160925().get(size-position-1).getAwayTeam());
////            holder.matchScore.setText(eplMatchweekFixturesResponse.get20160925().get(size-position-1).getScore());
////        }
////        while ((position >eplMatchweekFixturesResponse.get20160924().size()+eplMatchweekFixturesResponse.get20160925().size()-1) && position<(eplMatchweekFixturesResponse.get20160924().size()+eplMatchweekFixturesResponse.get20160925().size()+eplMatchweekFixturesResponse.get20160927().size())){
////            Log.v("TEST","inside bind 27");
////            holder.fixtureHomeTeam.setText(eplMatchweekFixturesResponse.get20160924().get(position).getHomeTeam());
////            holder.fixtureAwayTeam.setText(eplMatchweekFixturesResponse.get20160924().get(position).getAwayTeam());
////            holder.matchScore.setText(eplMatchweekFixturesResponse.get20160924().get(position).getScore());
////        }
//
//    }
//
//    @Override
//    public int getItemCount() {
////        Log.v("TEST","returning size"+eplMatchweekFixturesResponse.get20160924().size());
////        Log.v("TEST","size: "+(eplMatchweekFixturesResponse.get20160927().size()+eplMatchweekFixturesResponse.get20160925().size()+eplMatchweekFixturesResponse.get20160924().size()));
////        return (eplMatchweekFixturesResponse.get20160927().size()+eplMatchweekFixturesResponse.get20160925().size()+eplMatchweekFixturesResponse.get20160924().size());
//            return eplMatchweekFixturesResponse.get20160924().size();
//    }
//
//    static class FixtureViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.fixtureKickoffTime)
//        TextView fixtureKickoffTime;
//        @BindView(R.id.fixtureHomeTeam)
//        TextView fixtureHomeTeam;
////        @BindView(R.id.fixtureHomeTeamScore)
////        TextView fixtureHomeTeamScore;
////        @BindView(R.id.fixtureAwayTeamScore)
////        TextView fixtureAwayTeamScore;
//        @BindView(R.id.matchScore)
//        TextView matchScore;
//        @BindView(R.id.fixtureAwayTeam)
//        TextView fixtureAwayTeam;
//        @BindView(R.id.matchFixtureRowItem)
//        LinearLayout matchFixtureRowItem;
//
//        FixtureViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//    }
//}
