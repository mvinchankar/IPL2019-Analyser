package com.iplanalyser;

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

    @Test
    public void givenIndiaCensusData_IfCorrectCSVFile_ShouldReturnCorrectRecords() {
        IPLAnalyser iplAnalyser = new IPLAnalyser();
        Map<String, IPLMostRunsCSV> map = null;
        try {
            map = iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
            Assert.assertEquals(100, map.size());
        } catch (AnalyserException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenIndiaCensusData_IfInCorrectCSVFilePath_ShouldThrowException() {
        IPLAnalyser iplAnalyser = new IPLAnalyser();
        Map<String, IPLMostRunsCSV> map = null;
        try {
            map = iplAnalyser.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_INCORRECT_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.NO_SUCH_FILE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_IfInCorrectCSV_ShouldThrowException() {
        IPLAnalyser iplAnalyser = new IPLAnalyser();
        Map<String, IPLMostRunsCSV> map = null;
        try {
            map = iplAnalyser.loadIPLData(INCORRECT_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CSV_FILE_ISSUES, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_IfCorrectCSVButHasNullValues_ShouldThrowException() {
        IPLAnalyser iplAnalyser = new IPLAnalyser();
        Map<String, IPLMostRunsCSV> map = null;
        try {
            map = iplAnalyser.loadIPLData(NULL_VALUE_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CSV_FILE_ISSUES, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_IfCorrectCSVButDataNotLoad_ShouldThrowException() {
        IPLAnalyser iplAnalyser = new IPLAnalyser();
        try {
            iplAnalyser.loadIPLData(NULL_VALUE_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CSV_FILE_ISSUES, e.type);
        }
    }

}
