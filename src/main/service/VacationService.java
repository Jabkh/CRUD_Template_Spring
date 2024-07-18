package org.example.gestionrh.service;

import org.example.gestionrh.dto.VacationDTO;
import org.example.gestionrh.model.Vacation;
import org.example.gestionrh.repository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacationService {

    @Autowired
    private VacationRepository vacationRepository;

    public List<VacationDTO> getAllVacations() {
        return vacationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VacationDTO getVacationById(Long id) {
        return vacationRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public VacationDTO createVacation(VacationDTO vacationDTO) {
        Vacation vacation = convertToEntity(vacationDTO);
        vacation = vacationRepository.save(vacation);
        return convertToDTO(vacation);
    }

    public VacationDTO updateVacation(Long id, VacationDTO vacationDTO) {
        return vacationRepository.findById(id).map(existingVacation -> {
            existingVacation.setStartDate(vacationDTO.getStartDate());
            existingVacation.setEndDate(vacationDTO.getEndDate());
            existingVacation.setType(vacationDTO.getType());
            existingVacation.setReason(vacationDTO.getReason());
            vacationRepository.save(existingVacation);
            return convertToDTO(existingVacation);
        }).orElse(null);
    }

    public void deleteVacation(Long id) {
        vacationRepository.deleteById(id);
    }

    private VacationDTO convertToDTO(Vacation vacation) {
        VacationDTO dto = new VacationDTO();
        dto.setId(vacation.getId());
        dto.setStartDate(vacation.getStartDate());
        dto.setEndDate(vacation.getEndDate());
        dto.setType(vacation.getType());
        dto.setReason(vacation.getReason());
        return dto;
    }

    private Vacation convertToEntity(VacationDTO vacationDTO) {
        Vacation vacation = new Vacation();
        vacation.setStartDate(vacationDTO.getStartDate());
        vacation.setEndDate(vacationDTO.getEndDate());
        vacation.setType(vacationDTO.getType());
        vacation.setReason(vacationDTO.getReason());
        return vacation;
    }
}