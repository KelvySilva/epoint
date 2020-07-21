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
    private Boolean isLate;


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

    @Override
    public String toString() {
        return "Point{\n" +
                "\npointDate=" + pointDate +
                ", \npointTime=" + pointTime +
                ", \nisLate=" + isLate +
                ", \nemployee=" + employee +
                ", \ntype=" + type +
                "\n"+
                '}';
    }






    public enum POINT_TYPE {
        ENTRADA("Entrada"),
        SAIDA("Saída");

        private String description;

        POINT_TYPE(String description){
            this.description = description;
        }
    }

    public static final class PointBuilder {
        protected Long id;
        private LocalDate pointDate = LocalDate.now();
        private LocalTime pointTime = LocalTime.now();
        private Boolean isLate;
        private Employee employee;
        private POINT_TYPE type;

        private PointBuilder() {
        }

        public static PointBuilder aPoint() {
            return new PointBuilder();
        }

        public PointBuilder withPointDate(LocalDate pointDate) {
            this.pointDate = pointDate;
            return this;
        }

        public PointBuilder withPointTime(LocalTime pointTime) {
            this.pointTime = pointTime;
            return this;
        }

        public PointBuilder withIsLate(Boolean isLate) {
            this.isLate = isLate;
            return this;
        }

        public PointBuilder withEmployee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public PointBuilder withType(POINT_TYPE type) {
            this.type = type;
            return this;
        }

        public PointBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public Point build() {
            Point point = new Point();
            point.setPointDate(pointDate);
            point.setPointTime(pointTime);
            point.setIsLate(isLate);
            point.setEmployee(employee);
            point.setType(type);
            point.setId(id);
            return point;
        }
    }
}
