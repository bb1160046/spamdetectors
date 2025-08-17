package com.spamdetector.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.spamdetector.entity.SpamReport;

public interface SpamReportRepository extends JpaRepository<SpamReport, Long> {
    long countByPhoneNumber(String phoneNumber);
}

