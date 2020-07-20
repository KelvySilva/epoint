package br.com.epoint.domain;

import br.com.epoint.constants.DateAndTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Point extends AbstractEntity {

    private LocalDate pointDate = LocalDate.now();
    private LocalTime pointTime = LocalTime.now();

    @Column(columnDefinition="BOOLEAN DEFAULT FALSE")
    private boolean isLate;


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            @JoinColumn(name = "employee_name", referencedColumnName = "name")
    })
    private Employee employee;

}
