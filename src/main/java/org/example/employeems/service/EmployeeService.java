package org.example.employeems.service;

import jakarta.transaction.Transactional;
import org.example.employeems.dto.EmployeeDTO;
import org.example.employeems.entity.Employee;
import org.example.employeems.repo.EmployeeRepo;
import org.example.employeems.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper modelMapper;

    public String createEmployee(EmployeeDTO employeeDTO) {
        if(employeeRepo.existsById(employeeDTO.getEmpID())) {
            return VarList.RSP_DUPLICATED;
        }
        else {
            employeeRepo.save(modelMapper.map(employeeDTO, Employee.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public String updateEmployee(EmployeeDTO employeeDTO) {
        if(employeeRepo.existsById(employeeDTO.getEmpID())) {
            employeeRepo.save(modelMapper.map(employeeDTO, Employee.class));
            return VarList.RSP_SUCCESS;
        }
        else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employeeList =  employeeRepo.findAll();
        return modelMapper.map(employeeList, new TypeToken<ArrayList<EmployeeDTO>>(){}.getType());
    }

    public EmployeeDTO getEmployeeByID(int empID) {
        if(employeeRepo.existsById(empID)) {
            Employee employee = employeeRepo.findById(empID).orElse(null);
            return modelMapper.map(employee, EmployeeDTO.class);
        }
        else {
            return null;
        }
    }

    public String deleteEmployee(int empID) {
        if(employeeRepo.existsById(empID)) {
            employeeRepo.deleteById(empID);
            return VarList.RSP_SUCCESS;
        }
        else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
}
