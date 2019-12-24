package com.iplanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IPLAnalyserTestMockito {

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
    IPLAnalyser ipl;
    Map<String, IPLDAO> map;
    Comparator<IPLDAO> comparator;
    IPLAdapter adapter;

    @Before
    public void setUp() throws Exception {
        ipl = new IPLAnalyser();
        this.map = new HashMap<>();
        this.adapter = mock(IPLAdapter.class);
        this.map.put("Akshay", new IPLDAO("Abc", 123, 23.5));
        this.map.put("Shri", new IPLDAO("def", 123, 23.7));
        this.map.put("Mangesh", new IPLDAO("ghi", 123, 25.5));
        this.comparator = Comparator.comparing(compare -> compare.averageOfBatsmen);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenIPLMostRunsData_IfCorrectCSVFile_ShouldReturnCorrectRecords() {
        try {
            when(adapter.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH)).thenReturn(map);
            ipl.setAdapter(adapter);
            Map<String, IPLDAO> iplData = ipl.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
            Assert.assertEquals(3, iplData.size());
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostWicketsData_IfCorrectCSVFile_ShouldReturnCorrectRecords() {
        try {
            IPLAdapter adapter = mock(IPLAdapter.class);
            when(adapter.loadIPLData(IPL_2019_FACTSHEET_BOWLING_STATS)).thenReturn(this.map);
            IPLAnalyser analyser = new IPLAnalyser();
            analyser.setAdapter(adapter);
            Map<String, IPLDAO> iplData = analyser.loadIPLData(IPL_2019_FACTSHEET_BOWLING_STATS);
            Assert.assertEquals(3, iplData.size());
        } catch (AnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostRunsData_IfSortedCorrectCSVFile_ShouldReturnCorrectRecords() {
        try {
            when(adapter.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH)).thenReturn(map);
            ipl.setAdapter(adapter);
            Map<String, IPLDAO> iplData = ipl.loadIPLData(IPL_2019_FACTSHEET_MOST_RUNS_CSV_FILE_PATH);
            SortingContainer sortingContainer = mock(SortingContainer.class);
            when(sortingContainer.getData(FieldsToSort.BY_AVERAGE)).thenReturn(this.comparator);
            String sortedIPLData = ipl.getFieldWiseSortedIPLData(FieldsToSort.BY_AVERAGE, iplData);
            IPLDAO[] sortedData = new Gson().fromJson(sortedIPLData, IPLDAO[].class);
            Assert.assertEquals("Abc", sortedData[0].playerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostWicketsData_IfSortedCorrectCSVFile_ShouldReturnCorrectRecords() {
        try {
            when(adapter.loadIPLData(IPL_2019_FACTSHEET_BOWLING_STATS)).thenReturn(map);
            ipl.setAdapter(adapter);
            Map<String, IPLDAO> iplData = ipl.loadIPLData(IPL_2019_FACTSHEET_BOWLING_STATS);
            SortingContainer sortingContainer = mock(SortingContainer.class);
            when(sortingContainer.getData(FieldsToSort.BY_AVERAGE)).thenReturn(this.comparator);
            String sortedIPLData = ipl.getFieldWiseSortedIPLData(FieldsToSort.BY_AVERAGE, iplData);
            IPLDAO[] sortedData = new Gson().fromJson(sortedIPLData, IPLDAO[].class);
            Assert.assertEquals("ghi", sortedData[2].playerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIPLMostWicketsData_IfSortedInCorrectCSVFile_ShouldReturnException() {
        try {
            ipl.setAdapter(adapter);
            Map<String, IPLDAO> iplData = ipl.loadIPLData(IPL_2019_FACTSHEET_BOWLING_STATS);
            SortingContainer sortingContainer = mock(SortingContainer.class);
            when(sortingContainer.getData(FieldsToSort.BY_AVERAGE)).thenReturn(this.comparator);
            String sortedIPLData = ipl.getFieldWiseSortedIPLData(FieldsToSort.BY_AVERAGE, iplData);
            IPLDAO[] sortedData = new Gson().fromJson(sortedIPLData, IPLDAO[].class);
            Assert.assertEquals("ghi", sortedData[2].playerName);
        } catch (AnalyserException e) {
            Assert.assertEquals(AnalyserException.ExceptionType.NO_CENSUS_DATA,e.type);
        }
    }
}
