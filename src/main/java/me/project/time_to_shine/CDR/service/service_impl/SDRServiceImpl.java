package me.project.time_to_shine.CDR.service.service_impl;

import me.project.time_to_shine.CDR.exception.custom.ReportNotFoundException;
import me.project.time_to_shine.CDR.model.ReportSDR;
import me.project.time_to_shine.CDR.repo.SDRRepo;
import me.project.time_to_shine.CDR.service.SDRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SDRServiceImpl implements SDRService {
    @Autowired
    private SDRRepo repo;


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
    public void deleteReport(int id) {
        ReportSDR report = repo.findById(id).get();
        repo.delete(report);
    }
}
