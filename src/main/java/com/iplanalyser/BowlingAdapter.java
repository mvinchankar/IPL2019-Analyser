package com.iplanalyser;

import java.util.Map;

public class BowlingAdapter extends IPLAdapter {

    @Override
    public Map<String, IPLDAO> loadCensusData(String csvFilePath) throws AnalyserException {
        Map<String, IPLDAO> map = super.loadCensusData(IPLBowlersCSV.class, csvFilePath);
        return map;
    }
}
