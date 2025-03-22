package me.project.time_to_shine.CDR.exception.custom;

public class ReportNotFoundException extends RuntimeException {

    public ReportNotFoundException(String msg) {
        super(msg);
    }

    public ReportNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
