package me.project.time_to_shine.CDR.service.service_impl;

import me.project.time_to_shine.CDR.exception.custom.ReportNotFoundException;
import me.project.time_to_shine.CDR.model.CallDirection;
import me.project.time_to_shine.CDR.model.Client;
import me.project.time_to_shine.CDR.model.ClientInterval;
import me.project.time_to_shine.CDR.model.ReportSDR;
import me.project.time_to_shine.CDR.repo.ClientRepository;
import me.project.time_to_shine.CDR.repo.SDRRepository;
import me.project.time_to_shine.CDR.service.SDRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class SDRServiceImpl implements SDRService {
    @Autowired
    private SDRRepository repo;

    @Autowired
    private ClientRepository clientRepo;

    @Override
    public List<ReportSDR> getReports() {
        return repo.findAll();
    }

    @Override
    public ReportSDR getReport(int id) {
        Optional<ReportSDR> opt = repo.findById(id);
        if (opt.isEmpty()) {
            throw new ReportNotFoundException("не получилось найти отчет с id = " + id);
        }
        return opt.get();
    }

    @Override
    public ReportSDR addReport(ReportSDR report) {
        return repo.save(report);
    }
// написать проверку границ даты
    @Override
    public List<ReportSDR> generateReports(){
        Random random = new Random();
        int numOfReports = random.nextInt(10000) + 1;

        List<ReportSDR> reports = new ArrayList<>();
        List<Client> clients = clientRepo.findAll();

        int firstId;
        int secondId;

        LocalDate startDate = LocalDate.of(2025, Month.JANUARY, 1);

        for (int i = 0; i < numOfReports; i++) {
            do{
                firstId = random.nextInt(clients.size());
                secondId = random.nextInt(clients.size());
            } while (firstId == secondId);


            ReportSDR report = new ReportSDR();
            Client firstClient = clients.get(firstId);
            Client secondClient = clients.get(secondId);

            report.setCallDirection(random.nextDouble() <= 0.5? CallDirection.INCOMING: CallDirection.OUTGOING);
            report.setCallerNumber(firstClient.getNumber());
            report.setReceiveNumber(secondClient.getNumber());

            LocalDateTime firstDate = LocalDateTime.of(
                                         startDate.plusDays(random.nextInt(365)),
                                         LocalTime.of(random.nextInt(24),
                                                      random.nextInt(60),
                                                      random.nextInt(60)));

            Duration duration = Duration.ofHours(random.nextInt(10)).
                                         plusMinutes(random.nextInt(60)).
                                         plusSeconds(random.nextInt(60));

            report.setBeginningDate(firstDate);
            report.setEndingDate(firstDate.plus(duration));

            reports.add(report);
        }

        reports.sort(Comparator.comparing(ReportSDR::getEndingDate));
        repo.saveAll(reports);
        return reports;
    }

    @Override
    public void deleteReport(int id) {
        ReportSDR report = repo.findById(id).get();
        repo.delete(report);
    }
}
