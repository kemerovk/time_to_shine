package me.project.time_to_shine.CDR.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class ReportUDR {

    @Setter
    @Getter
    @JsonProperty(value = "msisdn")
    private String clientNumber;


    @Setter
    private LocalTime totalIncomingCallsTime;

    @Setter
    private LocalTime totalOutgoingCallsTime;

    public ReportUDR(String number, LocalTime totalIncomingTime, LocalTime totalOutgoingTime) {
        this.clientNumber = number;
        this.totalIncomingCallsTime = totalIncomingTime;
        this.totalOutgoingCallsTime = totalOutgoingTime;
    }

    @JsonIgnore
    public LocalTime getTotalIncomingCallsTime() {
        return totalIncomingCallsTime;
    }

    @JsonIgnore
    public LocalTime getTotalOutgoingCallsTime() {
        return totalOutgoingCallsTime;
    }


    @JsonProperty(value = "incoming call")
    public Map<String, LocalTime> incomingCall(){
        Map<String, LocalTime> incomingCall = new LinkedHashMap<>();
        incomingCall.put("total time", getTotalIncomingCallsTime());
        return incomingCall;
    }


    @JsonProperty(value = "outgoing call")
    public Map<String, LocalTime> outgoingCall(){
        Map<String, LocalTime> outgoingCall = new LinkedHashMap<>();
        outgoingCall.put("total time", getTotalOutgoingCallsTime());
        return outgoingCall;
    }

}
