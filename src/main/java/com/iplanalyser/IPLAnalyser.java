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
    private IPLAdapter adapter;

    public IPLAnalyser(PlayerStats statistics) {
        container = new SortingContainer();
        this.statistics = statistics;
    }

    public void setAdapter(IPLAdapter adapter) {
        this.adapter = adapter;
    }

    public IPLAnalyser() {

    }

    public Map<String, IPLDAO> loadIPLData(String... csvFilePath) throws AnalyserException {
        iplHashMap = this.adapter.loadIPLData(csvFilePath);
        return iplHashMap;
    }

    public String getFieldWiseSortedIPLData(FieldsToSort fieldName, Map<String, IPLDAO> iplHashMap) throws AnalyserException {
        Comparator<IPLDAO> comparator = null;
        SortingContainer container = new SortingContainer();
        if (iplHashMap == null || iplHashMap.size() == 0) {
            throw new AnalyserException("NO_CENSUS_DATA", AnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        comparator = container.getData(fieldName);
        ArrayList arrayList = iplHashMap.values().stream()
                .sorted(comparator)
                .collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensus = new Gson().toJson(arrayList);
        return sortedStateCensus;
    }
}
