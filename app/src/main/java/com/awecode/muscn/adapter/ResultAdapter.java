package com.awecode.muscn.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awecode.muscn.R;
import com.awecode.muscn.model.http.recent_results.RecentResultData;
import com.awecode.muscn.model.listener.ResultItemClickListener;
import com.awecode.muscn.util.Util;
import com.awecode.muscn.util.retrofit.ServiceGenerator;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by suresh on 3/28/16.
 */
public class ResultAdapter extends RealmRecyclerViewAdapter<RecentResultData, RecyclerView.ViewHolder> {
    public static final String TAG = ResultAdapter.class.getSimpleName();
    private Context context;

    public ResultItemClickListener mResultItemClickListener;

    public ResultAdapter(@Nullable OrderedRealmCollection<RecentResultData> data) {
        super(data, true);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_recent_result, parent, false);
        HeaderViewHolder header = new HeaderViewHolder(view);
        return header;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final RecentResultData recentResultData = getItem(position);
        final HeaderViewHolder itemController = (HeaderViewHolder) holder;


        if (recentResultData.getVenue().contains(","))
            itemController.eplMatchweekTimeandHomeGround.setText(Util.commonDateFormatter(recentResultData.getDatetime(),
                    "yyyy-MM-dd'T'hh:mm:ss'Z'") + "\n" + recentResultData.getVenue().substring(0, recentResultData.getVenue().indexOf(",")));
        else
            itemController.eplMatchweekTimeandHomeGround.setText(Util.commonDateFormatter(recentResultData.getDatetime(),
                    "yyyy-MM-dd'T'hh:mm:ss'Z'") + "\n" + recentResultData.getVenue());


        if (recentResultData.getIsHomeGame()) {
            itemController.eplMatchweekHomeTeamShortName.setText(R.string.manutd_shortname);
            if (recentResultData.getMufcScore() == null) {
                itemController.eplMatchweekHomeTeamScore.setText("");
            } else
                itemController.eplMatchweekHomeTeamScore.setText(recentResultData.getMufcScore().toString());
            if (recentResultData.getOpponentShortName() == null || recentResultData.getOpponentShortName().isEmpty())
                itemController.eplMatchweekAwayTeamShortName.setText(recentResultData.getOpponentName().substring(0, 3).toUpperCase());
            else
                itemController.eplMatchweekAwayTeamShortName.setText(recentResultData.getOpponentShortName().toUpperCase());

            if (recentResultData.getOpponentScore() == null) {
                itemController.eplMatchweekAwayTeamScore.setText("?");
            } else
                itemController.eplMatchweekAwayTeamScore.setText(recentResultData.getOpponentScore().toString());
            itemController.eplMatchweekFixtureHomeTeamLogo.setImageResource(R.drawable.logo_manutd);
            Picasso.with(context).load(ServiceGenerator.API_BASE_URL + recentResultData.getOpponentCrest()).into(itemController.eplMatchweekFixtureAwayTeamLogo);

        } else {
            itemController.eplMatchweekAwayTeamShortName.setText(R.string.manutd_shortname);
            if (recentResultData.getOpponentScore() == null) {
                itemController.eplMatchweekHomeTeamScore.setText("?");
            } else
                itemController.eplMatchweekHomeTeamScore.setText(recentResultData.getOpponentScore().toString());
            if (recentResultData.getOpponentShortName() == null || recentResultData.getOpponentShortName().isEmpty())
                itemController.eplMatchweekHomeTeamShortName.setText(recentResultData.getOpponentName().substring(0, 3).toUpperCase());
            else
                itemController.eplMatchweekHomeTeamShortName.setText(recentResultData.getOpponentShortName());
            if (recentResultData.getMufcScore() == null) {
                itemController.eplMatchweekAwayTeamScore.setText("?");
            } else
                itemController.eplMatchweekAwayTeamScore.setText(recentResultData.getMufcScore().toString());

            Picasso.with(context).load(ServiceGenerator.API_BASE_URL + recentResultData.getOpponentCrest())
                    .into(itemController.eplMatchweekFixtureHomeTeamLogo);
            itemController.eplMatchweekFixtureAwayTeamLogo.setImageResource(R.drawable.logo_manutd);
        }
        itemController.matchResultRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultItemClickListener.onRecentResultClicked(recentResultData.getId());
            }
        });


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

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

