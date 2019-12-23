package com.iplanalyser;

import java.util.Comparator;

public class MostRunsAndMostWicketsComparator implements Comparator<IPLDAO> {
    @Override
    public int compare(IPLDAO ipldao1, IPLDAO ipldao2) {
        return (ipldao1.runsScored * ipldao1.wicketsTaken)
                - (ipldao2.runsScored * ipldao2.wicketsTaken);
    }
}
