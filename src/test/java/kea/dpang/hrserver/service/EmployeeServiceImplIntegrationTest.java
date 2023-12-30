package kea.dpang.hrserver.service;

import kea.dpang.hrserver.dto.CreateEmployeeDto;
import kea.dpang.hrserver.dto.EmployeeExistsResponseDto;
import kea.dpang.hrserver.dto.EmployeeResponseDto;
import kea.dpang.hrserver.dto.UpdateEmployeeDto;
import kea.dpang.hrserver.entity.Employee;
import kea.dpang.hrserver.exception.MemberNotFoundException;
import kea.dpang.hrserver.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Rollback
@SpringBootTest
class EmployeeServiceImplIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void init() {
        employeeRepository.deleteAll();
    }

    @Test
    public void 사원_생성_1() {
        // Given : 새로운 직원의 정보가 주어졌을 때
        CreateEmployeeDto dto = new CreateEmployeeDto("test@test.com", "test", LocalDate.now());
        Employee employee = Employee.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .hireDate(dto.getHireDate())
                .build();

        // When : 해당 직원을 생성하는 서비스를 호출하면
        EmployeeResponseDto result = employeeService.createEmployee(dto);

        // Then : 생성된 직원의 정보가 정확하게 반환되어야 한다.
        assertEquals(employee.getEmail(), result.getEmail());
        assertEquals(employee.getName(), result.getName());
    }

    @Test
    public void 사원_조회_1() {
        // Given : 직원이 주어졌을 때
        Employee employee = Employee.builder()
                .email("test@test.com")
                .name("test")
                .hireDate(LocalDate.now())
                .build();

        employeeRepository.save(employee);

        // When : 직원을 조회하는 서비스를 호출하면
        EmployeeResponseDto result = employeeService.getEmployee(employee.getId());

        // Then : 조회된 직원의 정보가 정확하게 조회되어야 한다.
        assertEquals(employee.getEmail(), result.getEmail());
        assertEquals(employee.getName(), result.getName());
    }

    @Test
    public void 사원_조회_2() {
        // Given : 사원이 주어졌을 때
        CreateEmployeeDto dto = new CreateEmployeeDto("test@test.com", "test", LocalDate.now());
        EmployeeResponseDto response = employeeService.createEmployee(dto);

        // When : 사원을 조회하는 서비스를 호출하면
        EmployeeResponseDto result = employeeService.getEmployee(response.getId());

        // Then : 조회된 사원의 정보가 정확하게 조회되어야 한다.
        assertEquals(response.getEmail(), result.getEmail());
        assertEquals(response.getName(), result.getName());
    }

    @Test
    public void 사원_조회_3() {
        // Given : 사원이 존재하지 않을 때
        // When : 특정 아이디(1)를 가진 원을 조회하는 메소드를 실행하면
        // Then : MemberNotFoundException 예외가 발생해야 한다
        assertThrows(MemberNotFoundException.class, () -> employeeService.getEmployee(1));
    }

    @Test
    public void 사원_수정_1() {
        // Given : 직원이 주어졌을 때
        String beforeEmail = "test@test.com";
        String beforeName = "남숭현";

        Employee employee = Employee.builder()
                .email(beforeEmail)
                .name(beforeName)
                .hireDate(LocalDate.now())
                .build();

        employeeRepository.save(employee);

        // When : 직원의 정보를 업데이트하는 서비스를 호출하면
        String afterEmail = "updated@test.com";
        String afterName = "남승현";

        EmployeeResponseDto result = employeeService.updateEmployee(employee.getId(), new UpdateEmployeeDto(afterEmail, afterName));

        // Then : 업데이트된 직원의 정보가 정확하게 반환되어야 한다.
        assertNotEquals(beforeEmail, result.getEmail()); // 기존 이메일 검증
        assertNotEquals(beforeName, result.getName()); // 기존 이름 검증
        assertEquals(afterEmail, result.getEmail()); // 수정된 이메일 검증
        assertEquals(afterName, result.getName()); // 수정된 이름 검증
    }

    @Test
    public void 사원_수정_2() {
        // Given : 직원이 주어졌을 때
        String beforeEmail = "test@test.com";
        String beforeName = "남숭현";

        CreateEmployeeDto dto = new CreateEmployeeDto(beforeEmail, beforeName, LocalDate.now());
        EmployeeResponseDto response = employeeService.createEmployee(dto);

        // When : 직원의 정보를 업데이트하는 서비스를 호출하면
        String afterEmail = "updated@test.com";
        String afterName = "남승현";

        EmployeeResponseDto result = employeeService.updateEmployee(response.getId(), new UpdateEmployeeDto(afterEmail, afterName));

        // Then : 업데이트된 직원의 정보가 정확하게 반환되어야 한다.
        assertNotEquals(beforeEmail, result.getEmail()); // 기존 이메일 검증
        assertNotEquals(beforeName, result.getName()); // 기존 이름 검증
        assertEquals(afterEmail, result.getEmail()); // 수정된 이메일 검증
        assertEquals(afterName, result.getName()); // 수정된 이름 검증
    }

    @Test
    public void 사원_수정_3_너무_긴_이름() {
        // Given : 직원이 주어졌을 때
        String beforeEmail = "test@test.com";
        String beforeName = "남숭현";

        CreateEmployeeDto dto = new CreateEmployeeDto(beforeEmail, beforeName, LocalDate.now());
        EmployeeResponseDto response = employeeService.createEmployee(dto);

        // When : 너무 긴 이름으로 정보를 수정한다면
        String afterEmail = "updated@test.com";
        String afterName = "남승현입니다";

        // Then: DataIntegrityViolationException이 발생해야 한다
        assertThrows(DataIntegrityViolationException.class, () -> employeeService.updateEmployee(response.getId(), new UpdateEmployeeDto(afterEmail, afterName)));
    }

    @Test
    public void 사원_삭제_1() {
        // Given : 직원이 주어졌을 때
        CreateEmployeeDto dto = new CreateEmployeeDto("test@test.com", "test", LocalDate.now());
        EmployeeResponseDto response = employeeService.createEmployee(dto);

        // When : 직원을 삭제하는 메소드를 실행하면
        // Then : 해당 메소드 실행에서 예외가 발생하지 않아야 한다
        assertDoesNotThrow(() -> employeeService.deleteEmployee(response.getId()));

        // Then: 직원이 조회가 되면 안된다.
        assertThrows(MemberNotFoundException.class, () -> employeeService.getEmployee(response.getId()));
    }

    @Test
    public void 사원_삭제_2() {
        // Given : 직원이 주어졌을 때 (없음)
        // When : 직원을 삭제하는 메소드를 실행하면
        // Then : 해당 메소드 실행에서 예외가 발생하지 않아야 한다
        assertThrows(MemberNotFoundException.class, () -> employeeService.deleteEmployee(new Random().nextInt()));
    }

    @Test
    public void 사원_존재_여부_확인_1() {
        // Given : 직원이 존재할 때
        CreateEmployeeDto dto = new CreateEmployeeDto("test@test.com", "test", LocalDate.now());
        EmployeeResponseDto response = employeeService.createEmployee(dto);

        // When : 해당 정보를 가진 직원의 존재 여부를 확인하는 메소드를 실행하면
        EmployeeExistsResponseDto result = employeeService.isEmployeeExists(response.getId(), "test@test.com", "test");

        // Then : 존재한다고 응답해야 한다
        assertTrue(result.isExist());
    }

    @Test
    public void 사원_존재_여부_확인_2() {
        // Given : 사원이 존재할 때
        CreateEmployeeDto dto = new CreateEmployeeDto("test@test.com", "test", LocalDate.now());
        EmployeeResponseDto response = employeeService.createEmployee(dto);

        // When : 올바르지 않은 정보로 사원의 존재 여부를 확인하는 메소드를 실행하면
        EmployeeExistsResponseDto result = employeeService.isEmployeeExists(response.getId(), "test@dpang.com", "test");

        // Then : 존재하지 않는다고 응답해야 한다
        assertFalse(result.isExist());
    }

    @Test
    public void 사원_존재_여부_확인_3() {
        // Given : 삭제된 사원이 존재할 때
        CreateEmployeeDto dto = new CreateEmployeeDto("test@test.com", "test", LocalDate.now());
        EmployeeResponseDto response = employeeService.createEmployee(dto);

        employeeService.deleteEmployee(response.getId());

        // When : 올바르지 않은 정보로 사원의 존재 여부를 확인하는 메소드를 실행하면
        EmployeeExistsResponseDto result = employeeService.isEmployeeExists(response.getId(), "test@dpang.com", "test");

        // Then : 존재하지 않는다고 응답해야 한다
        assertFalse(result.isExist());
    }

}