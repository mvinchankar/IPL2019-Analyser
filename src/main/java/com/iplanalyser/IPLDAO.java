package com.iplanalyser;

public class IPLDAO {

    public String playerName;
    public int matchPlayed;
    public int runsScored;
    public double average;
    public double strikeRate;
    public int numberOf4sScored;
    public int numberOf6sScored;
    public double averageOfBowler;
    public double strikeRatesOfBowler;
    public double economyOfBowler;
    public int bowlersWith4Wickets;
    public int bowlersWith5Wickets;


    public IPLDAO(IPLMostRunsCSV next) {
        playerName = next.playerName;
        matchPlayed = next.matchPlayed;
        average = next.average;
        runsScored = next.runsScored;
        strikeRate = next.strikeRate;
        numberOf4sScored = next.numberOf4sScored;
        numberOf6sScored = next.numberOf6sScored;
    }

    public IPLDAO(IPLBowlersCSV next) {
        playerName = next.playerName;
        averageOfBowler = next.avgOfBowler;
        strikeRatesOfBowler = next.strikeRateOfBowler;
        economyOfBowler = next.economyOfBowler;
        bowlersWith4Wickets=next.wickets4Taken;
        bowlersWith5Wickets =next.wickets5Taken;
    }

    public Object getStatsDTO(PlayerStats playerStats) {
        if (playerStats.equals(PlayerStats.BATTING_STATS)) {
            return new IPLMostRunsCSV(playerName, matchPlayed, average,
                    runsScored, strikeRate, numberOf4sScored, numberOf6sScored);
        }
        return new IPLBowlersCSV(playerName, averageOfBowler, strikeRatesOfBowler,economyOfBowler,bowlersWith4Wickets,bowlersWith5Wickets);
    }
}
