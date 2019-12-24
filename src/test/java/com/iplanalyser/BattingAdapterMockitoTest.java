package com.iplanalyser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BattingAdapterMockitoTest {

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
    BatsmanAdapter ipl;

    @Before
    public void setUp() throws Exception {
        ipl = new BatsmanAdapter();
        this.map = new HashMap<>();
        this.adapter = mock(IPLAdapter.class);
        this.map.put("1", new IPLDAO("David Warner", 12, 12));
        this.map.put("2", new IPLDAO("KL Rahul", 14, 14));
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenLoadMostRunsCsvData_ShouldReturnCorrectRecords() {
        try {
            when(adapter.loadIPLData(IPLMostRunsCSV.class, "/home/slot1/IdeaProjects/IPLAnalyser/src/" +
                    "test/resources/Dummy.csv")).thenReturn(map);
            Map<String, IPLDAO> iplData = ipl.loadIPLData("/home/slot1/IdeaProjects/IPLAnalyser/src/" +
                    "test/resources/Dummy.csv");
            Assert.assertEquals(2, iplData.size());
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenNotLoadMostRunsCsvData_ShouldReturnIncorrectRecords() {
        try {
            when(adapter.loadIPLData(IPLMostRunsCSV.class, "/home/slot1/IdeaProjects/IPLAnalyser/src/" +
                    "test/resources/Dummy.csv")).thenReturn(map);
            Map<String, IPLDAO> iplData = ipl.loadIPLData("/home/slot1/IdeaProjects/IPLAnalyser/src/" +
                    "test/resources/Dummy.csv");
            Assert.assertNotEquals(3, iplData.size());
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostRunsData_IfInCorrectCSVFilePath_ShouldThrowException() {
        try {
            map = ipl.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_INCORRECT_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.NO_SUCH_FILE, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfInCorrectCSV_ShouldThrowException() {
        try {
            ipl.loadIPLData(INCORRECT_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIPLMostRunsData_IfCorrectCSVButHasNullValues_ShouldThrowException() {
        try {
            ipl.loadIPLData(NULL_VALUE_IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
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
