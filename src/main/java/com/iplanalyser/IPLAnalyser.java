package com.iplanalyser;

import com.csvbuilder.CSVBuilderException;
import com.csvbuilder.CSVBuilderFactory;
import com.csvbuilder.ICSBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IPLAnalyser {

    public Map<String, IPLMostRunsCSV> loadIPLData(String filePath) throws AnalyserException {
        Map<String, IPLMostRunsCSV> iplMostRunsCSVHashMapMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IPLMostRunsCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IPLMostRunsCSV.class);
            Iterable<IPLMostRunsCSV> censusDAOIterable = () -> censusCSVIterator;
            StreamSupport.stream(censusDAOIterable.spliterator(), false)
                    .forEach(censusCSV -> iplMostRunsCSVHashMapMap.put(censusCSV.playerName, new IPLMostRunsCSV(censusCSV)));
            return iplMostRunsCSVHashMapMap;
        } catch (NoSuchFileException e) {
            throw new AnalyserException(e.getMessage(), AnalyserException.ExceptionType.NO_SUCH_FILE);
        } catch (RuntimeException e){
            throw new AnalyserException(e.getMessage(),AnalyserException.ExceptionType.CSV_FILE_ISSUES);
        }
        catch (IOException | CSVBuilderException e) {
            throw new AnalyserException(e.getMessage(), AnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
