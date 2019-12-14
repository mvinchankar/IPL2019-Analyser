package com.iplanalyser;

public class IPLDAO {

    public String playerName;
    public int matchPlayed;
    public double average;
    public double strikeRate;

    public IPLDAO(IPLMostRunsCSV next) {
        playerName = next.playerName;
        matchPlayed = next.matchPlayed;
        average = next.average;
        strikeRate = next.strikeRate;
    }
}
