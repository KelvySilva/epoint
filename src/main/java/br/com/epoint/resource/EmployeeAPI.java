package br.com.epoint.resource;

import br.com.epoint.domain.Employee;
import br.com.epoint.error.ErrorDetails;
import br.com.epoint.error.ResourceNotFoundException;
import br.com.epoint.error.ValidationErrorDetails;
import br.com.epoint.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("employee")
public class EmployeeAPI {

    private EmployeeService service;

    @Autowired
    public EmployeeAPI(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity listAll() {
        List<Employee> employees = this.service.listAll();
        if (employees.isEmpty())
            return ResponseEntity
                    .status(404)
                    .body(ErrorDetails.Builder.newBuilder()
                    .title("Nunhum Funcinário cadastrado!")
                            .status(404)
                            .detail("Nenhum funcionário foi cadastrado até o momento.")
                            .developerMessage("Em desenvolvimento")
                            .timestamp(Date.from(Instant.now()).getTime())
                            .build());
        return ResponseEntity.ok(employees);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity listOne(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.listOne(id));
    }

    @PostMapping
    public ResponseEntity saveOne(@Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(this.service.saveOne(employee));
    }

    @GetMapping(path = "/blockeds")
    public ResponseEntity listBlockeds(){
        return ResponseEntity.ok(this.service.listBlockedEmployees());
    }

    @PutMapping(path = "/{id}")
    @Transactional
    @PreAuthorize("")
    public ResponseEntity updateOne(@PathVariable Long id, @RequestBody Employee employee) {
        Optional<Employee> employee1 = this.service.listOne(id);
        if (employee1.isPresent()) {
            employee.setId(id);
            System.out.println(employee);
            return ResponseEntity.ok(this.service.updateOne(employee));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/code/{code}")
    public ResponseEntity listByCode(@PathVariable Long code) {
        Employee employee = new Employee();
        return ResponseEntity.ok(this.service.getByCode(code));
    }


}
