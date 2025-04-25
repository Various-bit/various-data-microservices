package org.example.support.service.impl;

import lombok.AllArgsConstructor;
import org.example.support.entity.Report;
import org.example.support.repository.ReportRepository;
import org.example.support.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public void saveReport(Report report) {
        reportRepository.save(report);
    }

    @Override
    public List<Report> findAllReport() {
        return reportRepository.findAll();
    }
}
