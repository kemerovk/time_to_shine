package me.project.time_to_shine.CDR.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ClientInterval {
    private LocalDateTime begin;
    private LocalDateTime end;

}
