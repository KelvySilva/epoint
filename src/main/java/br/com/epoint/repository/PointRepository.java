package br.com.epoint.repository;

import br.com.epoint.domain.PageableResponse;
import br.com.epoint.domain.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long>{

    Integer countByEmployeeIdAndIsLateIsTrueAndPointDateIsBetween(Long id, LocalDate localDate, LocalDate localDate2);

    Integer countByEmployeeIdAndPointDateEquals(Long id, LocalDate now);

    List<Point> findByPointDate(LocalDate date);

    List<Point> findByPointDateAndEmployeeId(LocalDate date, Long id);

    Page<Point> findByEmployeeNameIgnoreCaseContaining(String name, Pageable pageable);

}
