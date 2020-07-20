package br.com.epoint.domain;

import br.com.epoint.constants.DateAndTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private POINT_TYPE type;


    public enum POINT_TYPE {
        ENTRADA("Entrada"),
        SAIDA("Saída");

        private String description;

        POINT_TYPE(String description){
            this.description = description;
        }
    }
}
