package kea.dpang.hrserver.repository;

import kea.dpang.hrserver.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByIdAndEmailAndName(Integer employeeId, String email, String name);
}