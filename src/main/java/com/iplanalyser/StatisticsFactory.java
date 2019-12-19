package com.iplanalyser;

public class StatisticsFactory {

    public static IPLAdapter StatisticsObject(PlayerStats stats) {
        if (stats.equals(PlayerStats.BATTING_STATS))
            return new BatsmanAdapter();
        if (stats.equals(PlayerStats.BOWLER_STATS))
            return new BowlingAdapter();
        return null;
    }
}
