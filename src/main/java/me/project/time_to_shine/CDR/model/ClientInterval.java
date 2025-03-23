package me.project.time_to_shine.CDR.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ClientInterval {

    private CallDirection callDirection;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ClientInterval( CallDirection callDirection,
                           LocalDateTime startTime,
                           LocalDateTime endTime) {
        this.callDirection = callDirection;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
