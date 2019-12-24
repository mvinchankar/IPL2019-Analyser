package com.iplanalyser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BowlingAdapterMockitoTest {

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
    IPLAdapter adapter;
    BowlingAdapter ipl;

    @Before
    public void setUp() throws Exception {
        ipl = new BowlingAdapter();
        this.map = new HashMap<>();
        this.adapter = mock(IPLAdapter.class);
        this.map.put("1",new IPLDAO("Imran Tahir",17,17));
        this.map.put("2",new IPLDAO("Kagiso Rabada",12,12));
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenIPLMostRunsData_IfCorrectCSVFile_ShouldReturnCorrectRecords() {
        try {
            when(adapter.loadIPLData(IPLMostRunsCSV.class, "/home/slot1/IdeaProjects/IPLAnalyser/src/" +
                    "test/resources/Dummy1.csv")).thenReturn(map);
            map = ipl.loadIPLData("/home/slot1/IdeaProjects/IPLAnalyser/src/" +
                    "test/resources/Dummy1.csv");
            Assert.assertEquals(2, map.size());
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
        try {
            map = ipl.loadIPLData(INCORRECT_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfCorrectCSVButHasNullValues_ShouldThrowException() {
        try {
            map = ipl.loadIPLData(NULL_VALUE_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfCorrectCSVButDataNotLoad_ShouldThrowException() {
        try {
            ipl.loadIPLData(NULL_VALUE_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

}
