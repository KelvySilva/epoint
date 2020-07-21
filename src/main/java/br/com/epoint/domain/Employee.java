package br.com.epoint.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@Entity
public class Employee extends AbstractEntity {

    @NotNull
    @NotEmpty
    private String name;

    @NotEmpty
    @NotNull
    private String username;

    private String password;

    @NotEmpty(message = "É preciso informar o campo admin")
    @Column(columnDefinition="BOOLEAN DEFAULT FALSE")
    private boolean admin;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private Long code;

    @Column(columnDefinition="BOOLEAN DEFAULT FALSE")
    private boolean isBlocked;

    @NotNull
    @NotEmpty
    @Column(columnDefinition="BOOLEAN DEFAULT TRUE")
    private boolean isActive;

    private String blockCauseMessage;

    @NotNull
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private TYPE type;

    public Employee() {
        long leftLimit = 1000L;
        long rightLimit = 8999L;
        this.code = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                ", cpf='" + cpf + '\'' +
                ", code=" + code +
                ", isBlocked=" + isBlocked +
                ", isActive=" + isActive +
                ", blockCauseMessage='" + blockCauseMessage + '\'' +
                ", type=" + type +
                '}';
    }

    public enum TYPE {

        RH("Recursos Humano", "RH"),
        DEV("Desenvolvimento", "DEV"),
        SUP("Suporte","SUP"),
        SAC("Atendimento","SAC");

        private String text;
        private String initials;

        TYPE(String text, String initials) {
            this.text = text;
            this.initials = initials;
        }

        public String getText() {
            return this.text;
        }

        public String getInitials() {
            return this.initials;
        }
    }


}
