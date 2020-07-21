package br.com.epoint.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@Entity
public class Employee extends AbstractEntity {

    @NotNull
    @NotEmpty
    @NotBlank
    @JsonProperty(required = true)
    private String name;

    @NotEmpty
    @NotNull
    @NotBlank
    private String username;

    private String password;

    @NotEmpty(message = "É preciso informar o campo admin")
    @NotBlank
    @Column(columnDefinition="BOOLEAN DEFAULT FALSE")
    private Boolean admin;

    @NotNull
    @NotEmpty
    @NotBlank
    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private Long code;

    @Column(columnDefinition="BOOLEAN DEFAULT FALSE")
    @NotBlank
    private Boolean isBlocked;

    @NotNull
    @NotEmpty
    @NotBlank
    @Column(columnDefinition="BOOLEAN DEFAULT TRUE")
    private Boolean isActive;

    private String blockCauseMessage;

    @NotNull
    @NotEmpty
    @NotBlank
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
                "\nname='" + name + '\'' +
                ", \nusername='" + username + '\'' +
                ", \npassword='" + password + '\'' +
                ", \nadmin=" + admin +
                ", \ncpf='" + cpf + '\'' +
                ", \ncode=" + code +
                ", \nisBlocked=" + isBlocked +
                ", \nisActive=" + isActive +
                ", \nblockCauseMessage='" + blockCauseMessage + '\'' +
                ", \ntype=" + type +
                "\n"+'}';
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
