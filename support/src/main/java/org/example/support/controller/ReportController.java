package org.example.support.controller;


import lombok.AllArgsConstructor;
import org.example.support.entity.Report;
import org.example.support.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/report")
    public ResponseEntity<?> report(@RequestBody Report report) {
        reportService.saveReport(report);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reports")
    public List<Report> reports() {
        return reportService.findAllReport();
    }

}
