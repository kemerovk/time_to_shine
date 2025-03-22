package me.project.time_to_shine.CDR.service.service_impl;

import me.project.time_to_shine.CDR.exception.custom.ReportNotFoundException;
import me.project.time_to_shine.CDR.model.Client;
import me.project.time_to_shine.CDR.model.ReportSDR;
import me.project.time_to_shine.CDR.repo.ClientRepository;
import me.project.time_to_shine.CDR.repo.SDRRepository;
import me.project.time_to_shine.CDR.service.SDRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    @Override
    public List<ReportSDR> generateReports(){
        Random random = new Random();
        int numOfReports = random.nextInt(100000) + 1000;

        List<ReportSDR> reports = new ArrayList<>();
        List<Client> clients = clientRepo.findAll();

        int firstId;
        int secondId;
        for (int i = 0; i < numOfReports; i++) {
            do{
                firstId = random.nextInt(clients.size());
                secondId = random.nextInt(clients.size());
            } while (firstId == secondId);

            ReportSDR report = new ReportSDR();
            String firstClientNumber = clients.get(firstId).getNumber();
            String secondClientNumber = clients.get(secondId).getNumber();

            report.setCallerNumber(firstClientNumber);
            report.setReceiveNumber(secondClientNumber);



        }

        return reports;
    }

    @Override
    public void deleteReport(int id) {
        ReportSDR report = repo.findById(id).get();
        repo.delete(report);
    }
}
