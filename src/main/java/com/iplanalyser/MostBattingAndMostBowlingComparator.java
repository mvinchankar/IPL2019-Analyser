package com.iplanalyser;

import java.util.Comparator;

public class MostBattingAndMostBowlingComparator implements Comparator<IPLDAO> {
    @Override
    public int compare(IPLDAO ipldao1, IPLDAO ipldao2) {
        return (int) ((ipldao1.averageOfBatsmen - (1d / ipldao1.averageOfBowler)) -
                (ipldao2.averageOfBatsmen - (1d / ipldao2.averageOfBowler)));
    }
}
