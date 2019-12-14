package com.iplanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IPLAnalyserTest {

    private static final String IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH
            ="src/test/resources/IPL2019FactsheetMostRuns.csv";

    @Test
    public void givenIndiaCensusData_ShouldReturnCorrectRecords() {
        IPLAnalyser iplAnalyser = new IPLAnalyser();
        Map<String, IPLMostRunsCSV> map = iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        Assert.assertEquals(100, map.size());
    }

}
