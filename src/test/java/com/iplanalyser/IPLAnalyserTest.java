package com.iplanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IPLAnalyserTest {

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

    @Test
    public void givenIPLMostRunsData_IfCorrectCSVFile_ShouldReturnCorrectRecords() {
        IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
        try {
            int map = iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
            Assert.assertEquals(100, map);
        } catch (AnalyserException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenIPLMostRunsData_IfInCorrectCSVFilePath_ShouldThrowException() {
        IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
        try {
            int map = iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_INCORRECT_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.NO_SUCH_FILE, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfInCorrectCSV_ShouldThrowException() {
        IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
        try {
            int map = iplAnalyser.loadIPLData(INCORRECT_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfCorrectCSVButHasNullValues_ShouldThrowException() {
        IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
        try {
            int map = iplAnalyser.loadIPLData(NULL_VALUE_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfCorrectCSVButDataNotLoad_ShouldThrowException() {
        IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
        try {
            iplAnalyser.loadIPLData(NULL_VALUE_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfSortedByAverage_ShouldReturnCorrectRecords() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
            iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
            String sortedResult = iplAnalyser.getAverageWiseSortedIPLData(FieldsToSort.BY_AVERAGE);
            IPLDAO[] csvs = new Gson().fromJson(sortedResult, IPLDAO[].class);
            Assert.assertEquals("MS Dhoni", csvs[0].playerName);
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostRunsData_IfSortedByStrikeRates_ShouldReturnCorrectRecords() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
            iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
            String sortedResult = iplAnalyser.getAverageWiseSortedIPLData(FieldsToSort.BY_STRIKERATE);
            IPLDAO[] csvs = new Gson().fromJson(sortedResult, IPLDAO[].class);
            Assert.assertEquals("Ishant Sharma", csvs[0].playerName);
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostRunsData_IfDataNotLoadedForSortingStrikeRates_ShouldThrowException() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
            String sortedResult = iplAnalyser.getAverageWiseSortedIPLData(FieldsToSort.BY_STRIKERATE);
            IPLDAO[] csvs = new Gson().fromJson(sortedResult, IPLDAO[].class);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfSortingForMost6sAnd4s_ShouldReturnCorrectRecords() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
            iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
            String sortedResult = iplAnalyser.getAverageWiseSortedIPLData(FieldsToSort.BY_4s_AND_6s);
            IPLDAO[] csvs = new Gson().fromJson(sortedResult, IPLDAO[].class);
            Assert.assertEquals("Andre Russell", csvs[0].playerName);
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostRunsData_IfSortingForMost6sAnd4sButDataNotLoad_ShouldThrowException() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
            String sortedResult = iplAnalyser.getAverageWiseSortedIPLData(FieldsToSort.BY_4s_AND_6s);
            IPLDAO[] csvs = new Gson().fromJson(sortedResult, IPLDAO[].class);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfSortingForMost6sAnd4sAndStrikingRates_ShouldReturnCorrectRecords() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
            iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
            String sortedResult = iplAnalyser.getAverageWiseSortedIPLData(FieldsToSort.BY_STRIKERATE_WITHMOST_4sAND6s);
            IPLDAO[] csvs = new Gson().fromJson(sortedResult, IPLDAO[].class);
            Assert.assertEquals("Andre Russell", csvs[0].playerName);
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostRunsData_IfSortingForMostAverageAndStrikingRates_ShouldReturnCorrectRecords() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
            iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
            String sortedResult = iplAnalyser.getAverageWiseSortedIPLData(FieldsToSort.BY_AVERAGE_WITH_STRIKE_RATE);
            IPLDAO[] csvs = new Gson().fromJson(sortedResult, IPLDAO[].class);
            Assert.assertEquals("MS Dhoni", csvs[0].playerName);
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostRunsData_IfSortingForMostAverageWithMostRuns_ShouldReturnCorrectRecords() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BATTING_STATS);
            iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
            String sortedResult = iplAnalyser.getAverageWiseSortedIPLData(FieldsToSort.BY_AVERAGE_WITH_MOST_RUNS);
            IPLDAO[] csvs = new Gson().fromJson(sortedResult, IPLDAO[].class);
            Assert.assertEquals("David Warner ", csvs[0].playerName);
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLBowlerData_IfSortingForMostAverageInBowlingStats_ShouldReturnCorrectRecords() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BOWLER_STATS);
            iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_BOWLING_STATS);
            String sortedResult = iplAnalyser.getAverageWiseSortedIPLData(FieldsToSort.BY_TOP_BOWLING_AVERAGES);
            IPLDAO[] csvs = new Gson().fromJson(sortedResult, IPLDAO[].class);
            Assert.assertEquals("Krishnappa Gowtham", csvs[0].playerName);
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLBowlerData_IfSortingForMostStrikeRateInBowlingStats_ShouldReturnCorrectRecords() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BOWLER_STATS);
            iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_BOWLING_STATS);
            String sortedResult = iplAnalyser.getAverageWiseSortedIPLData(FieldsToSort.BY_TOP_BOWLING_STRIKING_RATES);
            IPLDAO[] csvs = new Gson().fromJson(sortedResult, IPLDAO[].class);
            Assert.assertEquals("Krishnappa Gowtham", csvs[0].playerName);
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLBowlerData_IfSortingForEconomyRateInBowlingStats_ShouldReturnCorrectRecords() {
        try {
            IPLAnalyser iplAnalyser = new IPLAnalyser(PlayerStats.BOWLER_STATS);
            iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_BOWLING_STATS);
            String sortedResult = iplAnalyser.getAverageWiseSortedIPLData(FieldsToSort.BY_TOP_BOWLING_ECONOMY_RATES);
            IPLDAO[] csvs = new Gson().fromJson(sortedResult, IPLDAO[].class);
            Assert.assertEquals("Shivam Dube", csvs[0].playerName);
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }
}
