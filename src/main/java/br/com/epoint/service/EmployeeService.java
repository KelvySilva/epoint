package br.com.epoint.service;

import br.com.epoint.domain.Employee;
import br.com.epoint.error.ResourceNotFoundException;
import br.com.epoint.error.ValidationErrorDetails;
import br.com.epoint.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> listAll() {
        return this.repository.findAll();
    }

    public Optional<Employee> listOne(Long id) {
        this.employeeExists(id);
        return this.repository.findById(id);
    }

    @Transactional
    public Employee saveOne(Employee employee) {
            return this.repository.save(employee);
    }

    public void employeeExists(Long id) {
        Optional<Employee> op = this.repository.findById(id);
        if (!op.isPresent()) throw new ResourceNotFoundException(String.format("Student not found for id: %s", id));
    }

    public Employee getByCode(Long code) {
        Employee em = this.repository.findByCode(code);
        return em;
    }

    public List<Employee> listBlockedEmployees(){
        return this.repository.findByIsBlockedIsTrue();
    }

    @Transactional
    public Employee updateOne(Employee employee) {
        return this.repository.saveAndFlush(employee);
    }
}
