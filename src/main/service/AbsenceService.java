package org.example.gestionrh.service;

import org.example.gestionrh.dto.AbsenceDTO;
import org.example.gestionrh.model.Absence;
import org.example.gestionrh.model.Employee;
import org.example.gestionrh.repository.AbsenceRepository;
import org.example.gestionrh.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<AbsenceDTO> getAllAbsences() {
        return absenceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AbsenceDTO getAbsenceById(Long id) {
        return absenceRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public AbsenceDTO createAbsence(AbsenceDTO absenceDTO) {
        Absence absence = convertToEntity(absenceDTO);
        absence = absenceRepository.save(absence);
        return convertToDTO(absence);
    }

    public AbsenceDTO updateAbsence(Long id, AbsenceDTO absenceDTO) {
        Absence absence = convertToEntity(absenceDTO);
        absence.setId(id);
        absence = absenceRepository.save(absence);
        return convertToDTO(absence);
    }

    public void deleteAbsence(Long id) {
        absenceRepository.deleteById(id);
    }

    private AbsenceDTO convertToDTO(Absence absence) {
        AbsenceDTO dto = new AbsenceDTO();
        dto.setId(absence.getId());
        dto.setEmployeeId(absence.getEmployee().getId());
        // Assuming the Absence model has startDate and endDate fields
        dto.setStartDate(absence.getStartDate());
        dto.setEndDate(absence.getEndDate());
        return dto;
    }

    private Absence convertToEntity(AbsenceDTO absenceDTO) {
        Absence absence = new Absence();
        absence.setId(absenceDTO.getId());
        Employee employee = employeeRepository.findById(absenceDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        absence.setEmployee(employee);
        // Assuming the AbsenceDTO has startDate and endDate fields to match the model
        absence.setStartDate(absenceDTO.getStartDate());
        absence.setEndDate(absenceDTO.getEndDate());
        return absence;
    }
}
