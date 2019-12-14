package com.iplanalyser;

public class IPLDAO {

    public String playerName;
    public int matchPlayed;
    public double average;
    public double strikeRate;
    public int numberOf4sScored;
    public int numberOf6sScored;
    public IPLDAO(IPLMostRunsCSV next) {
        playerName = next.playerName;
        matchPlayed = next.matchPlayed;
        average = next.average;
        strikeRate = next.strikeRate;
        numberOf4sScored=next.numberOf4sScored;
        numberOf6sScored=next.numberOf6sScored;
    }
}
