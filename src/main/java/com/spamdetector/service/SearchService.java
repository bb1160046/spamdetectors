package com.spamdetector.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spamdetector.dto.PersonDetailsDto;
import com.spamdetector.dto.SearchResultDto;
import com.spamdetector.entity.User;
import com.spamdetector.repo.ContactRepository;
import com.spamdetector.repo.SpamReportRepository;
import com.spamdetector.repo.UserRepository;

@Service
public class SearchService {

    @Autowired
    private ContactRepository contactRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SpamReportRepository spamRepo;

    public List<SearchResultDto> searchByName(String query) {
        String lowered = query.toLowerCase();
        List<Object[]> first = contactRepo.findByNameStartingWith(lowered);
        List<Object[]> second = contactRepo.findByNameContainingNotStarting(lowered);

        List<SearchResultDto> results = new ArrayList<>();

        for (Object[] r : first) {
            String name = (String) r[0];
            String phone = (String) r[1];
            double spam = spamLikelihood(phone);
            results.add(new SearchResultDto(name, phone, spam));
        }

        for (Object[] r : second) {
            String name = (String) r[0];
            String phone = (String) r[1];
            double spam = spamLikelihood(phone);
            results.add(new SearchResultDto(name, phone, spam));
        }

        return results;
    }

    public List<SearchResultDto> searchByPhone(String number) {
        Optional<User> userOpt = userRepo.findByPhoneNumber(number);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return List.of(new SearchResultDto(
                user.getName(),
                user.getPhoneNumber(),
                spamLikelihood(user.getPhoneNumber())
            ));
        }

        List<Object[]> contacts = contactRepo.findByPhoneNumber(number);
        Set<SearchResultDto> results = new HashSet<>();

        for (Object[] r : contacts) {
            String name = (String) r[0];
            String phone = (String) r[1];
            double spam = spamLikelihood(phone);
            results.add(new SearchResultDto(name, phone, spam));
        }

        return new ArrayList<>(results);
    }


    public PersonDetailsDto getPersonDetails(String phoneNumber, String requesterPhone) {
        PersonDetailsDto dto = new PersonDetailsDto();
        dto.setPhoneNumber(phoneNumber);
        dto.setSpamLikelihood(spamLikelihood(phoneNumber));

        Optional<User> userOpt = userRepo.findByPhoneNumberWithContacts(phoneNumber);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            dto.setName(user.getName());

            boolean isInContacts = user.getContacts().stream()
                .anyMatch(c -> c.getPhoneNumber().equals(requesterPhone));

            if (isInContacts) {
                dto.setEmail(user.getEmail());
            }
        } else {
            contactRepo.findFirstByPhoneNumber(phoneNumber)
                .ifPresent(dto::setName);
        }

        return dto;
    }

    private double spamLikelihood(String phoneNumber) {
        long spamCount = spamRepo.countByPhoneNumber(phoneNumber);
        long contactCount = contactRepo.countByPhoneNumber(phoneNumber);
        long total = spamCount + contactCount;
        if (total == 0) {
            return 0.0;
        }
        return (double) spamCount / total;
    }
}
