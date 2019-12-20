package com.iplanalyser;

import java.util.Comparator;

public class SortingWithMost4WsAnd5Ws implements Comparator<IPLDAO> {

    @Override
    public int compare(IPLDAO ipldao1, IPLDAO ipldao2) {
        return ((ipldao1.bowlersWith4Wickets + ipldao1.bowlersWith5Wickets)
                - (ipldao2.bowlersWith4Wickets + ipldao2.bowlersWith5Wickets));
    };
}
