package me.project.time_to_shine.CDR.controller;

import me.project.time_to_shine.CDR.model.Client;
import me.project.time_to_shine.CDR.model.ClientInterval;
import me.project.time_to_shine.CDR.model.ReportUDR;
import me.project.time_to_shine.CDR.repo.ClientRepository;
import me.project.time_to_shine.CDR.service.service_impl.SDRServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Контроллер для работы с отчетами о звонках (UDR).
 * Предоставляет REST API для получения сводной информации о звонках клиента.
 */
@RestController
@RequestMapping("udr")
public class ReportUDRController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SDRServiceImpl service;

    /**
     * Возвращает отчет о звонках клиента за указанный месяц.
     *
     * @param id    идентификатор клиента.
     * @param month месяц, за который требуется получить отчет (1-12).
     * @return ResponseEntity с отчетом о звонках.
     */
    @GetMapping("{id}/{month}")
    public ResponseEntity<ReportUDR> getUDR(@PathVariable int id, @PathVariable int month) {
        return ResponseEntity.ok(reportUDR(id, month));
    }

    /**
     * Возвращает отчет о звонках клиента за целый год.
     *
     * @param id идентификатор клиента.
     * @return ResponseEntity с отчетом о звонках.
     */
    @GetMapping("{id}")
    public ResponseEntity<ReportUDR> getUDR(@PathVariable int id) {
        return ResponseEntity.ok(reportUDR(id, 0));
    }

    /**
     * Формирует отчет о звонках клиента.
     *
     * @param id    идентификатор клиента.
     * @param month месяц, за который требуется получить отчет (0 — за весь год).
     * @return отчет о звонках клиента.
     */
    public ReportUDR reportUDR(int id, int month) {
        Client client = clientRepository.findById(id).get();

        LocalTime totalIncomingTime = LocalTime.of(0, 0);
        LocalTime totalOutgoingTime = LocalTime.of(0, 0);

        for (ClientInterval clientInterval : service.getListOfClientIntervalsByClientId(id)) {
            if (month != 0 &&
                    (clientInterval.getStartTime().
                            isAfter(LocalDateTime.of(month == 12 ? 2026 : 2025, month == 12 ? 1 : month + 1, 1, 0, 0, 0))
                            ||
                            clientInterval.getEndTime().
                                    isBefore(LocalDateTime.of(2025, month, 1, 0, 0, 0)))) continue;

            switch (clientInterval.getCallDirection()) {
                case OUTGOING -> totalOutgoingTime = totalOutgoingTime.plus(Duration.between(clientInterval.getStartTime(), clientInterval.getEndTime()));
                case INCOMING -> totalIncomingTime = totalIncomingTime.plus(Duration.between(clientInterval.getStartTime(), clientInterval.getEndTime()));
            }
        }

        return new ReportUDR(client.getNumber(), totalIncomingTime, totalOutgoingTime);
    }
}