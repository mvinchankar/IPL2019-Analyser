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

    Map<String, IPLDAO> iplMostRunsCSVHashMapMap = new HashMap<>();
    Map<FieldsToSort, Comparator<IPLDAO>> fields = null;

    public IPLAnalyser() {
        this.fields = new HashMap<>();
        this.fields.put(FieldsToSort.BY_AVERAGE, Comparator.comparing(census ->
                census.average, Comparator.reverseOrder()));
        this.fields.put(FieldsToSort.BY_STRIKERATE, Comparator.comparing(census ->
                census.strikeRate, Comparator.reverseOrder()));
    }

    public <T> Map<String, IPLDAO> loadIPLData(String filePath) throws AnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IPLMostRunsCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IPLMostRunsCSV.class);
            Iterable<IPLMostRunsCSV> censusDAOIterable = () -> censusCSVIterator;
            StreamSupport.stream(censusDAOIterable.spliterator(), false)
                    .forEach(censusCSV -> iplMostRunsCSVHashMapMap.put(censusCSV.playerName, new IPLDAO(censusCSV)));
            return iplMostRunsCSVHashMapMap;
        } catch (NoSuchFileException e) {
            throw new AnalyserException(e.getMessage(), AnalyserException.ExceptionType.NO_SUCH_FILE);
        } catch (CSVBuilderException | IOException e) {
            throw new AnalyserException(e.getMessage(), AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public String getAverageWiseSortedIPLData(FieldsToSort fieldName) throws AnalyserException {
        if (iplMostRunsCSVHashMapMap == null || iplMostRunsCSVHashMapMap.size() == 0) {
            throw new AnalyserException("No Census Data", AnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList arrayList = iplMostRunsCSVHashMapMap.values().stream()
                .sorted(this.fields.get(fieldName))
                .collect(toCollection(ArrayList::new));
        String sortedStateCensus = new Gson().toJson(arrayList);
        return sortedStateCensus;
    }
}
