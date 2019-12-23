package com.iplanalyser;

import com.csvbuilder.CSVBuilderException;
import com.csvbuilder.CSVBuilderFactory;
import com.csvbuilder.ICSBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class BatsmanAdapter extends IPLAdapter {

    @Override
    public Map<String, IPLDAO> loadIPLData(String... csvFilePath) throws AnalyserException {
        try {
            Map<String, IPLDAO> censusStateMap = super.loadIPLData(IPLMostRunsCSV.class, csvFilePath[0]);
            if (csvFilePath.length == 2)
                this.loadBowlerData(censusStateMap, csvFilePath[1]);
            return censusStateMap;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AnalyserException(e.getMessage(), AnalyserException.ExceptionType.NO_SUCH_FILE);
        }
    }

    public int loadBowlerData(Map<String, IPLDAO> ipldaoMap, String csvFilePath) throws AnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IPLBowlersCSV> csvStateCode = csvBuilder.getCSVFileIterator(reader, IPLBowlersCSV.class);
            Iterable<IPLBowlersCSV> stateCodeCSVS = () -> csvStateCode;
            StreamSupport.stream(stateCodeCSVS.spliterator(), false)
                    .filter(csvState -> ipldaoMap.get(csvState.playerName) != null)
                    .forEach(csvState -> ipldaoMap.get(csvState.playerName).averageOfBowler = csvState.avgOfBowler);
            return ipldaoMap.size();
        } catch (IOException | CSVBuilderException e) {
            throw new AnalyserException(e.getMessage(),
                    AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new AnalyserException(e.getMessage(), AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
