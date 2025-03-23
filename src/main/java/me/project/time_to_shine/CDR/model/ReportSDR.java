package me.project.time_to_shine.CDR.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "report_sdr")
public class ReportSDR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int id;

    @Convert(converter = CallDirectionConverter.class)
    private CallDirection callDirection;

    private String callerNumber;
    private String receiveNumber;

    private LocalDateTime beginningDate;
    private LocalDateTime endingDate;

}
