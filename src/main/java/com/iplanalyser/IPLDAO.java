package com.iplanalyser;

public class IPLDAO {

    public String playerName;
    public int matchPlayed;
    public int runsScored;
    public double averageOfBatsmen;
    public double strikeRate;
    public int numberOf4sScored;
    public int numberOf6sScored;
    public double averageOfBowler;
    public double strikeRatesOfBowler;
    public double economyOfBowler;
    public int bowlersWith4Wickets;
    public int bowlersWith5Wickets;
    public int wicketsTaken;

    public IPLDAO(IPLMostRunsCSV next) {
        playerName = next.playerName;
        matchPlayed = next.matchPlayed;
        averageOfBatsmen = next.averageOfBatsmen;
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
        bowlersWith4Wickets = next.wickets4Taken;
        bowlersWith5Wickets = next.wickets5Taken;
        wicketsTaken = next.wicketsTaken;
    }

    public IPLDAO(IPLDAO iplCSV) {

    }

    public Object getStatsDTO(PlayerStats playerStats) {
        if (playerStats.equals(PlayerStats.BATTING_STATS)) return new IPLMostRunsCSV(playerName, matchPlayed, averageOfBatsmen,
                runsScored, strikeRate, numberOf4sScored, numberOf6sScored);
        if (playerStats.equals(PlayerStats.BOWLER_STATS))
            return new IPLBowlersCSV(playerName, averageOfBowler, strikeRatesOfBowler, economyOfBowler,
                    bowlersWith4Wickets, bowlersWith5Wickets, wicketsTaken);
        return null;
    }
}
