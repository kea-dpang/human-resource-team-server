package kea.dpang.hrserver.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 5, nullable = false)
    private String name;

    @Column(name = "hire_date", nullable = false)
    private LocalDateTime hireDate;

}
