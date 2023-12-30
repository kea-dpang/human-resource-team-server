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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceImplUnitTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createEmployeeTest() {
        // Given : 새로운 직원의 정보가 주어졌을 때
        CreateEmployeeDto dto = new CreateEmployeeDto("test@test.com", "test", LocalDate.now());
        Employee employee = Employee.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .hireDate(dto.getHireDate())
                .build();

        // Mockito를 이용해 save 메소드의 호출에 대한 응답 정의
        when(employeeRepository.save(any())).thenReturn(employee);

        // When : 해당 직원을 생성하는 서비스를 호출하면
        EmployeeResponseDto result = employeeService.createEmployee(dto);

        // Then : 생성된 직원의 정보가 정확하게 반환되어야 한다.
        assertEquals(employee.getEmail(), result.getEmail());
        assertEquals(employee.getName(), result.getName());
    }

    @Test
    public void getEmployeeTest() {
        // Given : 특정 아이디(1)를 가진 직원을
        Employee employee = Employee.builder()
                .id(1)
                .email("test@test.com")
                .name("test")
                .hireDate(LocalDate.now())
                .build();

        // Mockito를 이용해 findById 메소드의 호출에 대한 응답 정의
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        // When : 조회하는 서비스를 호출하면
        EmployeeResponseDto result = employeeService.getEmployee(1);

        // Then : 조회된 직원의 정보가 정확하게 반환되어야 한다.
        assertEquals(employee.getEmail(), result.getEmail());
        assertEquals(employee.getName(), result.getName());
    }

    @Test
    public void updateEmployeeTest() {
        // Given : 특정 아이디(1)를 가진 직원의 정보를 수정하려고 할 때
        String beforeEmail = "test@test.com";
        String beforeName = "test";

        Employee employee = Employee.builder()
                .id(1)
                .email(beforeEmail)
                .name(beforeName)
                .hireDate(LocalDate.now())
                .build();

        String afterEmail = "updated@test.com";
        String afterName = "updated";
        UpdateEmployeeDto dto = new UpdateEmployeeDto(afterEmail, afterName);

        // Mockito를 이용해 findById와 save 메소드의 호출에 대한 응답 정의
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any())).thenReturn(employee);

        // When : 해당 아이디를 가진 직원의 정보를 업데이트하는 서비스를 호출하면
        EmployeeResponseDto result = employeeService.updateEmployee(1, dto);

        // Then : 업데이트된 직원의 정보가 정확하게 반환되어야 한다.
        assertNotEquals(beforeEmail, result.getEmail()); // 기존 이메일 검증
        assertNotEquals(beforeName, result.getName()); // 기존 이름 검증
        assertEquals(afterEmail, result.getEmail()); // 수정된 이메일 검증
        assertEquals(afterName, result.getName()); // 수정된 이름 검증
    }

    @Test
    public void deleteEmployeeTest() {
        // Given : 아이디(1)를 가진 직원이 존재할 때
        when(employeeRepository.existsById(1)).thenReturn(true);

        // When : 해당 아이디를 가진 직원을 삭제하는 메소드를 실행하면
        // Then : 해당 메소드 실행에서 예외가 발생하지 않아야 한다
        assertDoesNotThrow(() -> employeeService.deleteEmployee(1));

        // Then : deleteById 메소드가 한 번 호출되어야 한다(즉, 직원이 한 번 삭제되어야 한다)
        verify(employeeRepository, times(1)).deleteById(1);
    }

    @Test
    public void isEmployeeExistsTest() {
        // Given : 직원이 존재할 때 (사원번호: 1, 이메일: "test@test.com", 이름: "test")
        when(employeeRepository.existsByIdAndEmailAndName(1, "test@test.com", "test")).thenReturn(true);

        // When : 해당 정보를 가진 직원의 존재 여부를 확인하는 메소드를 실행하면
        EmployeeExistsResponseDto result = employeeService.isEmployeeExists(1, "test@test.com", "test");

        // Then : 존재한다고 응답해야 한다
        assertTrue(result.isExist());
    }

    @Test
    public void getEmployeeNotFoundTest() {
        // Given : 사원이 존재하지 않을 때
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());

        // When : 특정 아이디(1)를 가진 직원을 조회하는 메소드를 실행하면
        // Then : MemberNotFoundException 예외가 발생해야 한다
        assertThrows(MemberNotFoundException.class, () -> employeeService.getEmployee(1));
    }

    @Test
    public void deleteEmployeeNotFoundTest() {
        // Given : 특정 아이디(1)를 가진 직원이 존재하지 않을 때
        when(employeeRepository.existsById(1)).thenReturn(false);

        // When : 특정 아이디(1)를 가진 직원을 삭제하는 메소드를 실행
        // Then : MemberNotFoundException 예외가 발생해야 한다.
        assertThrows(MemberNotFoundException.class, () -> employeeService.deleteEmployee(1));
    }
}