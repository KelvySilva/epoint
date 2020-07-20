package br.com.epoint.repository;

import br.com.epoint.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {

    Integer countByIsLateIsTrue();

}
