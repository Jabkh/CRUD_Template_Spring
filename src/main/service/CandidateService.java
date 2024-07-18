package org.example.gestionrh.service;


import org.example.gestionrh.dto.CandidateDTO;
import org.example.gestionrh.model.Candidate;
import org.example.gestionrh.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public List<CandidateDTO> getAllCandidates() {
        return candidateRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CandidateDTO getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public CandidateDTO createCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = convertToEntity(candidateDTO);
        candidate = candidateRepository.save(candidate);
        return convertToDTO(candidate);
    }

    public CandidateDTO updateCandidate(Long id, CandidateDTO candidateDTO) {
        Candidate candidate = convertToEntity(candidateDTO);
        candidate.setId(id);
        candidate = candidateRepository.save(candidate);
        return convertToDTO(candidate);
    }

    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }

    private CandidateDTO convertToDTO(Candidate candidate) {
        CandidateDTO dto = new CandidateDTO();
        dto.setId(candidate.getId());
        dto.setName(candidate.getName());
        dto.setIdentificationNumber(candidate.getIdentificationNumber());
        dto.setAddress(candidate.getAddress());
        dto.setPhone(candidate.getPhone());
        dto.setEmail(candidate.getEmail());
        dto.setBirthDate(candidate.getBirthDate());
        dto.setRating(candidate.getRating());
        dto.setObservation(candidate.getObservation());
        dto.setSkill(candidate.getSkill());
        dto.setTechnicalArea(candidate.getTechnicalArea());
        dto.setJobInterviewDate(candidate.getJobInterviewDate());
        return dto;
    }

    private Candidate convertToEntity(CandidateDTO candidateDTO) {
        Candidate candidate = new Candidate();
        candidate.setName(candidateDTO.getName());
        candidate.setIdentificationNumber(candidateDTO.getIdentificationNumber());
        candidate.setAddress(candidateDTO.getAddress());
        candidate.setPhone(candidateDTO.getPhone());
        candidate.setEmail(candidateDTO.getEmail());
        candidate.setBirthDate(candidateDTO.getBirthDate());
        candidate.setRating(candidateDTO.getRating());
        candidate.setObservation(candidateDTO.getObservation());
        candidate.setSkill(candidateDTO.getSkill());
        candidate.setTechnicalArea(candidateDTO.getTechnicalArea());
        candidate.setJobInterviewDate(candidateDTO.getJobInterviewDate());
        return candidate;
    }
}
