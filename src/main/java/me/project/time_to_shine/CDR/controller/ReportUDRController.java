package me.project.time_to_shine.CDR.controller;

import me.project.time_to_shine.CDR.model.Client;
import me.project.time_to_shine.CDR.model.ClientInterval;
import me.project.time_to_shine.CDR.model.ReportUDR;
import me.project.time_to_shine.CDR.repo.ClientRepository;
import me.project.time_to_shine.CDR.service.service_impl.SDRServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalTime;

@RestController
@RequestMapping("udr")
public class ReportUDRController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SDRServiceImpl service;

    @GetMapping("{id}")
    public ResponseEntity<ReportUDR> reportUDR(@PathVariable int id) {
        Client client = clientRepository.findById(id).get();

        LocalTime totalIncomingTime = LocalTime.ofSecondOfDay(0);
        LocalTime totalOutgoingTime = LocalTime.ofSecondOfDay(0);

        for (ClientInterval clientInterval: service.getListOfClientIntervalsByClientId(id)){
            switch (clientInterval.getCallDirection()) {
                case OUTGOING -> totalOutgoingTime = totalOutgoingTime.plus(Duration.between(clientInterval.getStartTime(), clientInterval.getEndTime()));
                case INCOMING -> totalIncomingTime = totalIncomingTime.plus(Duration.between(clientInterval.getStartTime(), clientInterval.getEndTime()));
            }
        }

        ReportUDR reportUDR = new ReportUDR(client.getNumber(), totalIncomingTime, totalOutgoingTime);

        return ResponseEntity.ok(reportUDR);
    }

}
