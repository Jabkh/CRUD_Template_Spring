package org.example.gestionrh.service;


import org.example.gestionrh.dto.EmployeeDTO;
import org.example.gestionrh.model.Employee;
import org.example.gestionrh.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return convertToDTO(employee);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        employee.setId(id);
        employee = employeeRepository.save(employee);
        return convertToDTO(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setIdentificationNumber(employee.getIdentificationNumber());
        dto.setAddress(employee.getAddress());
        dto.setPhone(employee.getPhone());
        dto.setEmail(employee.getEmail());
        dto.setBirthDate(employee.getBirthDate());
        dto.setContractStart(employee.getContractStart());
        dto.setContractEnd(employee.getContractEnd());
        dto.setOccupation(employee.getOccupation());
        dto.setPassword(employee.getPassword());
        dto.setAdmin(employee.isAdmin());
        dto.setSalary(employee.getSalary());
        dto.setObservation(employee.getObservation());
        return dto;
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setIdentificationNumber(employeeDTO.getIdentificationNumber());
        employee.setAddress(employeeDTO.getAddress());
        employee.setPhone(employeeDTO.getPhone());
        employee.setEmail(employeeDTO.getEmail());
        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setContractStart(employeeDTO.getContractStart());
        employee.setContractEnd(employeeDTO.getContractEnd());
        employee.setOccupation(employeeDTO.getOccupation());
        employee.setPassword(employeeDTO.getPassword());
        employee.setAdmin(employeeDTO.isAdmin());
        employee.setSalary(employeeDTO.getSalary());
        employee.setObservation(employeeDTO.getObservation());
        return employee;
    }
}
