package com.awecode.muscn.model.listener;

import com.awecode.muscn.model.http.fixtures.FixturesResponse;

/**
 * Created by suresh on 10/2/16.
 */

public interface FixturesApiListener {
    void onCallFixtures(FixturesResponse fixturesResponse);
}
