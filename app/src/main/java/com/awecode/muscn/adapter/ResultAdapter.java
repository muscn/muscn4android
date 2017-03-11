package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.Item;
import com.awecode.muscn.model.http.recentresults.Result;
import com.awecode.muscn.model.listener.ResultItemClickListener;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.views.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by suresh on 3/28/16.
 */
public class ResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public static final String TAG = HomeActivity.class.getSimpleName();
    private Context context;
    private List<Item> itemList;
    public ResultItemClickListener mResultItemClickListener;

    public ResultAdapter(Context context, List<Item> item) {
        this.context = context;
        this.itemList = item;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.epl_matchweek_fixture_row_item, parent, false);
                HeaderViewHolder header = new HeaderViewHolder(view);
                return header;
            case CHILD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_result_expandedview_row_item, parent, false);
                return new ChildViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Item item = itemList.get(position);
        switch (item.type) {
            case HEADER:
                final Result result = itemList.get(position).getMatchResultResponse().getResults().get(position);
                final HeaderViewHolder itemController = (HeaderViewHolder) holder;
                if (result.getIsHomeGame()) {
                    itemController.eplMatchweekHomeTeamShortName.setText(R.string.manutd_shortname);
                    if (result.getMufcScore() == null) {
                        itemController.eplMatchweekHomeTeamScore.setText("");
                    } else
                        itemController.eplMatchweekHomeTeamScore.setText(result.getMufcScore().toString());
                    if (result.getOpponentShortName() == null || result.getOpponentShortName().isEmpty())
                        itemController.eplMatchweekAwayTeamShortName.setText(result.getOpponentName().substring(0, 3).toUpperCase());
                    else
                        itemController.eplMatchweekAwayTeamShortName.setText(result.getOpponentShortName().toUpperCase());
                    itemController.eplMatchweekTimeandHomeGround.setText(Util.commonDateFormatter(result.getDatetime(), "yyyy-MM-dd'T'hh:mm:ss'Z'") + "\nOldTraffod");
                    if (result.getOpponentScore() == null) {
                        itemController.eplMatchweekAwayTeamScore.setText("?");
                    } else
                        itemController.eplMatchweekAwayTeamScore.setText(result.getOpponentScore().toString());
                    itemController.eplMatchweekFixtureHomeTeamLogo.setImageResource(R.drawable.logo_manutd);
                    Picasso.with(context).load("http://manutd.org.np/" + result.getOpponentCrest()).into(itemController.eplMatchweekFixtureAwayTeamLogo);

                } else {
                    itemController.eplMatchweekAwayTeamShortName.setText(R.string.manutd_shortname);
                    if (result.getOpponentScore() == null) {
                        itemController.eplMatchweekHomeTeamScore.setText("?");
                    } else
                        itemController.eplMatchweekHomeTeamScore.setText(result.getOpponentScore().toString());
                    if (result.getOpponentShortName() == null || result.getOpponentShortName().isEmpty())
                        itemController.eplMatchweekHomeTeamShortName.setText(result.getOpponentName().substring(0, 3).toUpperCase());
                    else
                        itemController.eplMatchweekHomeTeamShortName.setText(result.getOpponentShortName());
                    if (result.getMufcScore() == null) {
                        itemController.eplMatchweekAwayTeamScore.setText("?");
                    } else
                        itemController.eplMatchweekAwayTeamScore.setText(result.getMufcScore().toString());
                    if (result.getVenue().contains(","))
                        itemController.eplMatchweekTimeandHomeGround.setText(Util.commonDateFormatter(result.getDatetime(), "yyyy-MM-dd'T'hh:mm:ss'Z'") + "\n" + result.getVenue().substring(0, result.getVenue().indexOf(",")));
                    else
                        itemController.eplMatchweekTimeandHomeGround.setText(Util.commonDateFormatter(result.getDatetime(), "yyyy-MM-dd'T'hh:mm:ss'Z'") + "\n" + result.getVenue());
                    Picasso.with(context).load("http://manutd.org.np/" + result.getOpponentCrest()).into(itemController.eplMatchweekFixtureHomeTeamLogo);
                    Log.v("TEST", "crest: " + result.getOpponentCrest());
                    itemController.eplMatchweekFixtureAwayTeamLogo.setImageResource(R.drawable.logo_manutd);
                }
                itemController.matchResultRowLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mResultItemClickListener.onRecentResultClicked(result.getId());
                    }
                });

                break;
            case CHILD:
                final ChildViewHolder itemcontroller = (ChildViewHolder) holder;
                itemcontroller.awayTeamExpandedDetailView.setVisibility(View.GONE);
                itemcontroller.expandedviewHomeTeamScore.setVisibility(View.GONE);
                itemcontroller.childViewScoreLayout.setVisibility(View.GONE);
                itemcontroller.childRefferalItem = item;
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * header view holder for recent results
     */
    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.table_row_divider)
        View tableRowDivider;
        @BindView(R.id.eplMatchweekFixtureHomeTeamLogo)
        ImageView eplMatchweekFixtureHomeTeamLogo;
        @BindView(R.id.eplMatchweekHomeTeamScore)
        TextView eplMatchweekHomeTeamScore;
        @BindView(R.id.eplMatchweekMatchStatus)
        TextView eplMatchweekMatchStatus;
        @BindView(R.id.eplMatchweekAwayTeamScore)
        TextView eplMatchweekAwayTeamScore;
        @BindView(R.id.eplMatchweekFixtureAwayTeamLogo)
        ImageView eplMatchweekFixtureAwayTeamLogo;
        @BindView(R.id.eplMatchweekHomeTeamShortName)
        TextView eplMatchweekHomeTeamShortName;
        @BindView(R.id.eplMatchweekTimeandHomeGround)
        TextView eplMatchweekTimeandHomeGround;
        @BindView(R.id.eplMatchweekAwayTeamShortName)
        TextView eplMatchweekAwayTeamShortName;
        @BindView(R.id.matchResultRowLayout)
        LinearLayout matchResultRowLayout;

        public Item headerRefferalItem;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    /**
     * expanded view holder for recent results
     */
    static class ChildViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.expandedViewEventTime)
        TextView expandedViewEventTime;
        @BindView(R.id.expandedEventPlayerName)
        TextView expandedEventPlayerName;
        @BindView(R.id.expandedEventImageview)
        ImageView expandedEventImageview;
        @BindView(R.id.expandedviewHomeTeamEventHolder)
        LinearLayout expandedviewHomeTeamEventHolder;
        @BindView(R.id.homeTeamExpandedDetailView)
        LinearLayout homeTeamExpandedDetailView;
        @BindView(R.id.expandedviewHomeTeamScore)
        TextView expandedviewHomeTeamScore;
        @BindView(R.id.expandedviewAwayTeamScore)
        TextView expandedviewAwayTeamScore;
        @BindView(R.id.expandedEventAwayImageview)
        ImageView expandedEventAwayImageview;
        @BindView(R.id.expandedEventAwayPlayerName)
        TextView expandedEventAwayPlayerName;
        @BindView(R.id.expandedviewAwayTeamEventHolder)
        LinearLayout expandedviewAwayTeamEventHolder;
        @BindView(R.id.awayTeamExpandedDetailView)
        LinearLayout awayTeamExpandedDetailView;
        @BindView(R.id.childViewScoreLayout)
        LinearLayout childViewScoreLayout;
        public Item childRefferalItem;

        public ChildViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

