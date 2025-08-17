package com.spamdetector.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spamdetector.dto.PersonDetailsDto;
import com.spamdetector.dto.SearchResultDto;
import com.spamdetector.service.SearchService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/search")
public class SearchController {
	
	@Autowired
	private SearchService searchService;

	@GetMapping("/name")
	public ResponseEntity<List<SearchResultDto>> searchByName(@RequestParam String query) {
		return ResponseEntity.ok(searchService.searchByName(query));
	}

	@GetMapping("/phone")
	public ResponseEntity<List<SearchResultDto>> searchByPhone(@RequestParam String number) {
		return ResponseEntity.ok(searchService.searchByPhone(number));
	}

	@GetMapping("/person/{phoneNumber}")
	public ResponseEntity<PersonDetailsDto> getDetails(@PathVariable String phoneNumber, Principal principal) {
		return ResponseEntity.ok(searchService.getPersonDetails(phoneNumber, principal.getName()));
	}
}
