package kea.dpang.hrserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false, unique = true, updatable = false)
    private String email;

    @Column(length = 5, nullable = false)
    private String name;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    public void updateInformation(String email, String name) {
        this.email = email;
        this.name = name;
    }

}
