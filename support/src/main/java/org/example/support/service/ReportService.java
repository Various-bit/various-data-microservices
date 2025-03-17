package org.example.support.service;

import org.example.support.entity.Report;

import java.util.List;

public interface ReportService {
    void saveReport(Report report);

    List<Report> findAllReport();
}
