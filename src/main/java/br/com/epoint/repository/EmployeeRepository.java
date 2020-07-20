package br.com.epoint.repository;

import br.com.epoint.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByCode(Long code);

    List<Employee> findByIsBlockedIsTrue();

    Employee findByUsername(String username);
}
