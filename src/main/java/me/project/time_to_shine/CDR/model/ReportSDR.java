package me.project.time_to_shine.CDR.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "report_sdr")
public class ReportSDR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int id;

    private CallDirection callDirection;

    private String callerNumber;
    private String receiveNumber;

    private LocalDateTime beginningDate;
    private LocalDateTime endingDate;

}
