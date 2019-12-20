package com.iplanalyser;

import java.util.Comparator;

public class SortMethodContainer implements Comparator<IPLDAO> {

    @Override
    public int compare(IPLDAO iplMostRunsCSV, IPLDAO iplMostRunsCSV1) {
        return ((iplMostRunsCSV.numberOf6sScored * 6) + (iplMostRunsCSV.numberOf4sScored * 4))
                - ((iplMostRunsCSV1.numberOf6sScored * 6) + (iplMostRunsCSV1.numberOf4sScored * 4));
    }
}
