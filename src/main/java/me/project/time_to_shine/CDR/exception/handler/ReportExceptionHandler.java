package me.project.time_to_shine.CDR.exception.handler;

import me.project.time_to_shine.CDR.exception.custom.ReportNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReportExceptionHandler {

    @ExceptionHandler({ReportNotFoundException.class})
    public ResponseEntity<String> handleReportNotFoundException(ReportNotFoundException e) {
        return ResponseEntity.ok(e.getMessage());
    }


}
