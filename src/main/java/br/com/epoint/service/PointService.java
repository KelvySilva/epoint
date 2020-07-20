package br.com.epoint.service;

import br.com.epoint.constants.DateAndTime;
import br.com.epoint.domain.Employee;
import br.com.epoint.domain.Point;
import br.com.epoint.error.ActionNotPermittedException;
import br.com.epoint.error.ResourceNotFoundException;
import br.com.epoint.repository.EmployeeRepository;
import br.com.epoint.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class PointService {

    private PointRepository repository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public PointService(PointRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Point saveOne(Point point) {
        Long code = point.getEmployee().getCode();
        Employee employee = this.employeeRepository.findByCode(code);

        if (Objects.isNull(employee)) {
            throw new ResourceNotFoundException("O funcionário informado não existe");
        }
        if (employee.isBlocked()) {
            throw new ActionNotPermittedException("O funcionário informado está com bloqueio. Entre em contato com o RH");
        }

        if (this.isLate(point)) {
            point.setLate(true);
        }

        if (this.repository.countByIsLateIsTrue() > 4) {
            employee.setBlocked(true);
            employee.setBlockCauseMessage("Mais de 5 atrasos no ponto");
        }


        point.setEmployee(employee);

        return this.repository.save(point);
    }

    public List<Point> listAll(){
        return this.repository.findAll();
    }



    public boolean isLate(Point point) {
        if (point.getPointTime().isAfter(DateAndTime.INITIAL_TIME.plusMinutes(5))) {
            return true;
        }
        return false;
    }
}
