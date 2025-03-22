package me.project.time_to_shine.CDR.service;

import me.project.time_to_shine.CDR.model.ReportSDR;

import java.util.List;

public interface SDRService {
    public List<ReportSDR> getReports();

    public ReportSDR getReport(int id);
    public ReportSDR addReport(ReportSDR report);
    public void deleteReport(int id);
    public List<ReportSDR> generateReports();
}
