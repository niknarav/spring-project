package com.example.hotel_booking_systems.controller;

import com.example.hotel_booking_systems.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService service;

    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportStatistics() throws IOException {
        String currentDir = Paths.get("").toAbsolutePath().toString();
        String filePath = currentDir + "/statistics/statistics.csv";
        service.exportToCSV(filePath);
    }

    @GetMapping("/download")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadStatistics() {
        String currentDir = Paths.get("").toAbsolutePath().toString();
        String filePath = currentDir + "/statistics/statistics.csv";

        FileSystemResource file = new FileSystemResource(filePath);
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statistics.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(file);
    }
}
