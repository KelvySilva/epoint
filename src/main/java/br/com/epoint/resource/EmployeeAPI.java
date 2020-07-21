package br.com.epoint.resource;

import br.com.epoint.domain.Employee;
import br.com.epoint.error.ErrorDetails;
import br.com.epoint.service.EmployeeService;
import br.com.epoint.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("employee")
public class EmployeeAPI {

    private EmployeeService service;

    @Autowired
    public EmployeeAPI(EmployeeService service) {
        this.service = service;
    }

    @GetMapping(path = "/protected")
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

    @GetMapping(path = "protected/{id}")
    public ResponseEntity listOne(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.listOne(id));
    }

    @PostMapping(path = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity saveOne(@Valid @RequestBody Employee employee) {
        String pass = PasswordEncoder.encode(employee.getPassword());
        employee.setPassword(pass);
        return ResponseEntity.ok(this.service.saveOne(employee));
    }

    @GetMapping(path = "protected/blockeds")
    public ResponseEntity listBlockeds(){
        return ResponseEntity.ok(this.service.listBlockedEmployees());
    }

    @PutMapping(path = "admin/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateOne(@PathVariable Long id,@Valid @RequestBody(required = true) Employee employee) {
        System.out.println(employee);
        Optional<Employee> employee1 = this.service.listOne(id);
        if (employee1.isPresent()) {
            Employee update = employee1.get();
            if (Objects.nonNull(employee.getType())) {
                update.setType(employee.getType());
            }
            if (Objects.nonNull(employee.getName())) {
                update.setName(employee.getName());
            }
            if (Objects.nonNull(employee.getCpf())) {
                update.setCpf(employee.getCpf());
            }
            if (Objects.nonNull(employee.getUsername())) {
                update.setUsername(employee.getUsername());
            }
            if ((Objects.nonNull(employee.getPassword())) && !(update.getPassword().equals(employee.getPassword()))) {
                update.setPassword(PasswordEncoder.encode(employee.getUsername()));
            }
            if (Objects.nonNull(employee.getBlockCauseMessage())) {
                update.setBlockCauseMessage(employee.getBlockCauseMessage());
            }
            if (!(employee.isBlocked() && update.isBlocked())) {
                update.setBlocked(employee.isBlocked());
            }
            if (!(employee.isAdmin() && update.isAdmin())) {
                update.setAdmin(employee.isAdmin());
            }
            if (!(employee.isActive() && update.isActive())) {
                update.setActive(employee.isActive());
            }
            return ResponseEntity.ok(this.service.updateOne(update));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "protected/code/{code}")
    public ResponseEntity listByCode(@PathVariable Long code) {
        Employee employee = new Employee();
        return ResponseEntity.ok(this.service.getByCode(code));
    }


}
