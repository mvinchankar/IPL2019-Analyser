package com.iplanalyser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class BowlingAdapterTest {

    private static final String IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH
            = "src/test/resources/IPL2019FactsheetMostRuns.csv";
    private static final String IPL_2019_FACTSHEET_MOST_RUNS_CSV_INCORRECT_FILE_PATH
            = "src/main/resources/IPL2019FactsheetMostRuns.csv";
    private static final String INCORRECT_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH
            = "src/test/resources/IncorrectIPL2019FactsheetMostRuns.csv";
    private static final String NULL_VALUE_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH
            = "src/test/resources/IncorrectIPL2019FactsheetMostRuns.csv";
    private static final String IPL_2019_FACTSHEET_BOWLING_STATS
            = "/home/slot1/IdeaProjects/IPLAnalyser/src/test/resources/IPL2019FactsheetMostWkts.csv";

    Map<String, IPLDAO> map;

    @Before
    public void setUp() {
        this.map = new HashMap<>();
    }

    @Test
    public void givenIPLMostRunsData_IfCorrectCSVFile_ShouldReturnCorrectRecords() {
        IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
        try {
            iplAnalyser.setAdapter(new BowlingAdapter());
             map = iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
            Assert.assertEquals(100, map.size());
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostRunsData_IfInCorrectCSVFilePath_ShouldThrowException() {
        IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
        try {
            iplAnalyser.setAdapter(new BowlingAdapter());
            map = iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_INCORRECT_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.NO_SUCH_FILE, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfInCorrectCSV_ShouldThrowException() {
        IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
        try {
            iplAnalyser.setAdapter(new BowlingAdapter());
             map = iplAnalyser.loadIPLData(INCORRECT_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfCorrectCSVButHasNullValues_ShouldThrowException() {
        IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
        try {
            iplAnalyser.setAdapter(new BowlingAdapter());
             map = iplAnalyser.loadIPLData(NULL_VALUE_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfCorrectCSVButDataNotLoad_ShouldThrowException() {
        IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
        try {
            iplAnalyser.setAdapter(new BowlingAdapter());
            iplAnalyser.loadIPLData(NULL_VALUE_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
}
