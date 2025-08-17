package com.spamdetector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spamdetector.entity.SpamReport;
import com.spamdetector.entity.User;
import com.spamdetector.repo.SpamReportRepository;
import com.spamdetector.repo.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private SpamReportRepository spamReportRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void markSpam(SpamReport dto, String reporterPhone) {
		User reporter = userRepository.findByPhoneNumber(reporterPhone)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid user"));

		SpamReport report = new SpamReport();
		report.setPhoneNumber(dto.getPhoneNumber());
		report.setReporterUserId(reporter.getId());
		spamReportRepository.save(report);
	}
}