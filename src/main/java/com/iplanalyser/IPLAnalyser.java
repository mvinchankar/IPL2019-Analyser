package com.iplanalyser;

import com.csvbuilder.CSVBuilderException;
import com.csvbuilder.CSVBuilderFactory;
import com.csvbuilder.ICSBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toCollection;

public class IPLAnalyser {

    Map<String, IPLDAO> iplHashMap = null;
    Map<FieldsToSort, Comparator<IPLDAO>> fields = null;
    private PlayerStats statistics;

    public IPLAnalyser(PlayerStats statistics) {
        this.fields = new HashMap<>();
        this.statistics = statistics;
        this.fields.put(FieldsToSort.BY_AVERAGE, Comparator.comparing(census ->
                census.average, Comparator.reverseOrder()));
        this.fields.put(FieldsToSort.BY_STRIKERATE, Comparator.comparing(census ->
                census.strikeRate, Comparator.reverseOrder()));
        this.fields.put(FieldsToSort.BY_4s_AND_6s, new SortMethodContainer().reversed());
        this.fields.put(FieldsToSort.BY_STRIKERATE_WITHMOST_4sAND6s, new SortMethodContainer()
                .reversed().thenComparing(compare -> compare.strikeRate));
        Comparator<IPLDAO> comparator = Comparator.comparing(compare -> compare.average);
        this.fields.put(FieldsToSort.BY_AVERAGE_WITH_STRIKE_RATE, comparator.thenComparing(compare ->
                compare.strikeRate).reversed());
        Comparator<IPLDAO> comparator1 = Comparator.comparing(compare -> compare.runsScored);
        this.fields.put(FieldsToSort.BY_AVERAGE_WITH_MOST_RUNS, comparator1.thenComparing(compare1 ->
                compare1.average).reversed());
        this.fields.put(FieldsToSort.BY_TOP_BOWLING_AVERAGES, Comparator.comparing(census ->
                census.averageOfBowler));
        this.fields.put(FieldsToSort.BY_TOP_BOWLING_STRIKING_RATES, Comparator.comparing(census ->
                census.strikeRatesOfBowler));
        this.fields.put(FieldsToSort.BY_TOP_BOWLING_ECONOMY_RATES, Comparator.comparing(census ->
                census.economyOfBowler));
        this.fields.put(FieldsToSort.BY_TOP_BOWLING_STRIKING_RATES_WITH_MOST_4Ws_AND_5Ws, new
                SortingWithMost4WsAnd5Ws().reversed().thenComparing(compare -> compare.strikeRatesOfBowler));
        Comparator<IPLDAO> comparator2 = Comparator.comparing(compare -> compare.averageOfBowler);
        this.fields.put(FieldsToSort.BY_GREAT_AVERAGE_RATE_WITH_BEST_STRIKING_RATE, comparator2.
                thenComparing(compare -> compare.strikeRatesOfBowler));
    }

    public int loadIPLData(String csvFilePath) throws AnalyserException {
        IPLAdapter censusFactory = StatisticsFactory.StatisticsObject(statistics);
        iplHashMap = censusFactory.loadCensusData(csvFilePath);
        return iplHashMap.size();
    }

    public String getAverageWiseSortedIPLData(FieldsToSort fieldName) throws AnalyserException {
        if (iplHashMap == null || iplHashMap.size() == 0) {
            throw new AnalyserException("No Census Data", AnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList arrayList = iplHashMap.values().stream()
                .sorted(this.fields.get(fieldName))
                .map(censusDAO -> censusDAO.getStatsDTO(statistics))
                .collect(toCollection(ArrayList::new));
        String sortedStateCensus = new Gson().toJson(arrayList);
        return sortedStateCensus;
    }

}
