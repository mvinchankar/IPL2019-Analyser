package com.iplanalyser;

public class AnalyserException extends Exception {

    enum ExceptionType {
        NO_SUCH_FILE,CENSUS_FILE_PROBLEM,CSV_FILE_ISSUES
    }

    ExceptionType type;

    public AnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
