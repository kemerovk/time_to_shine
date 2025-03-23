package me.project.time_to_shine.CDR.controller;

import jakarta.annotation.PostConstruct;
import me.project.time_to_shine.CDR.model.ReportSDR;
import me.project.time_to_shine.CDR.model.ReportUDR;
import me.project.time_to_shine.CDR.service.SDRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sdr")
public class ReportSDRController {

    @Autowired
    private SDRService sdrService;


    @GetMapping("reports")
    public ResponseEntity<List<ReportSDR>> getReports() {
        return ResponseEntity.ok(sdrService.getReports());
    }

    @GetMapping("reports/{id}")
    public ResponseEntity<ReportSDR> getSDR(@PathVariable int id) {
        return ResponseEntity.ok(sdrService.getReport(id));
    }

    @PostConstruct
    public void generateReport() {
        sdrService.generateReports();
    }

}
