package com.ftn.event_hopper.repositories.reports;

import com.ftn.event_hopper.models.reports.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
}
