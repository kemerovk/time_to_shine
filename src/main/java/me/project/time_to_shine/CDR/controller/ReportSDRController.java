package me.project.time_to_shine.CDR.controller;

import jakarta.annotation.PostConstruct;
import me.project.time_to_shine.CDR.model.ReportSDR;
import me.project.time_to_shine.CDR.service.SDRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с отчетами о звонках (SDR).
 * Предоставляет REST API для получения отчетов и их генерации.
 */
@RestController
@RequestMapping("/sdr")
public class ReportSDRController {

    @Autowired
    private SDRService sdrService;

    /**
     * Возвращает список всех отчетов о звонках.
     *
     * @return ResponseEntity со списком всех отчетов.
     */
    @GetMapping("reports")
    public ResponseEntity<List<ReportSDR>> getReports() {
        return ResponseEntity.ok(sdrService.getReports());
    }

    /**
     * Возвращает отчет о звонке по его идентификатору.
     *
     * @param id идентификатор отчета.
     * @return ResponseEntity с найденным отчетом.
     */
    @GetMapping("reports/{id}")
    public ResponseEntity<ReportSDR> getSDR(@PathVariable int id) {
        return ResponseEntity.ok(sdrService.getReport(id));
    }

    /**
     * Метод, выполняемый после создания бина. Генерирует случайные отчеты о звонках.
     */
    @PostConstruct
    public void generateReport() {
        System.out.println(sdrService.generateReports());
    }
}