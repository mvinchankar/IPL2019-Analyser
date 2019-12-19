package com.iplanalyser;

import java.util.Map;

public class BatsmanAdapter extends IPLAdapter {

    @Override
    public Map<String, IPLDAO> loadCensusData(String csvFilePath) throws AnalyserException {
        Map<String, IPLDAO> map = super.loadCensusData(IPLMostRunsCSV.class, csvFilePath);
        return map;
    }
}
