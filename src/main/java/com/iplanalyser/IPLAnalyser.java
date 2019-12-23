package com.iplanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

public class IPLAnalyser {

    Map<String, IPLDAO> iplHashMap;
    private PlayerStats statistics;
    SortingContainer container;

    public IPLAnalyser(PlayerStats statistics) {
        container = new SortingContainer();
        this.statistics = statistics;
    }

    public int loadIPLData(String... csvFilePath) throws AnalyserException {
        IPLAdapter censusFactory = StatisticsFactory.StatisticsObject(statistics);
        iplHashMap = censusFactory.loadIPLData(csvFilePath);
        return iplHashMap.size();
    }

    public String getFieldWiseSortedIPLData(FieldsToSort fieldName) throws AnalyserException {
        Comparator<IPLDAO> comparator = null;
        if (iplHashMap == null || iplHashMap.size() == 0) {
            throw new AnalyserException("No Census Data", AnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        comparator = container.getParameter(fieldName);
        ArrayList arrayList = iplHashMap.values().stream()
                .sorted(comparator)
                .collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensus = new Gson().toJson(arrayList);
        return sortedStateCensus;
    }
}
