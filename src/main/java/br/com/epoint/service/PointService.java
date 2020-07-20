package br.com.epoint.service;

import br.com.epoint.constants.DateAndTime;
import br.com.epoint.domain.Employee;
import br.com.epoint.domain.Point;
import br.com.epoint.error.ActionNotPermittedException;
import br.com.epoint.error.ResourceNotFoundException;
import br.com.epoint.repository.EmployeeRepository;
import br.com.epoint.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

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

        if(this.repository.countByEmployeeIdAndPointDateEquals(employee.getId(), LocalDate.now()) < 1){
            point.setType(Point.POINT_TYPE.ENTRADA);
            if (this.isLate(point)) {
                point.setLate(true);
            }
        }else if (this.repository.countByEmployeeIdAndPointDateEquals(employee.getId(), LocalDate.now()) == 1) {
            point.setType(Point.POINT_TYPE.SAIDA);
        }else {
            throw new ActionNotPermittedException("O funcionário já bateu todos os pontos");
        }
        if (this.repository.countByEmployeeIdAndIsLateIsTrueAndPointDateIsBetween(
                employee.getId(),
                LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() + (-1)),
                LocalDate.now()) > 4
        ) {
            employee.setBlocked(true);
            employee.setBlockCauseMessage("Mais de 5 atrasos no ponto");
        }


        point.setEmployee(employee);

        return this.repository.save(point);
    }

    public List<Point> listAll(){
        return this.repository.findAll();
    }

    public Page<Point> listAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    public boolean isLate(Point point) {
        if (point.getPointTime().isAfter(DateAndTime.INITIAL_TIME.plusMinutes(5))) {
            return true;
        }
        return false;
    }

    public List<Point> findByDate(LocalDate date) {
        return this.repository.findByPointDate(date);
    }

    public List<Point> findByDateAndEmployeeId(LocalDate date, Long id) {
        if (Objects.isNull(id) && !(Long.valueOf(id) instanceof Long)) {
            throw new ResourceNotFoundException("Valores não permitidos ou valor não encontrado");
        }
        return this.repository.findByPointDateAndEmployeeId(date, Long.valueOf(id));

    }


}
