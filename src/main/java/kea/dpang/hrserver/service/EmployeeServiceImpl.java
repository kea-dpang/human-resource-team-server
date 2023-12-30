package kea.dpang.hrserver.service;

import kea.dpang.hrserver.dto.CreateEmployeeDto;
import kea.dpang.hrserver.dto.EmployeeExistsResponseDto;
import kea.dpang.hrserver.dto.EmployeeResponseDto;
import kea.dpang.hrserver.dto.UpdateEmployeeDto;
import kea.dpang.hrserver.entity.Employee;
import kea.dpang.hrserver.exception.EmailAlreadyExistsException;
import kea.dpang.hrserver.exception.MemberNotFoundException;
import kea.dpang.hrserver.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeResponseDto createEmployee(CreateEmployeeDto dto) {
        String email = dto.getEmail();

        // 이미 사용 중인 이메일인지 확인
        if (employeeRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }

        Employee employee = Employee.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .hireDate(dto.getHireDate())
                .build();

        return new EmployeeResponseDto(employeeRepository.save(employee));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployee(Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .map(EmployeeResponseDto::new)
                .orElseThrow(() -> new MemberNotFoundException(employeeId));
    }

    @Override
    @Transactional
    public EmployeeResponseDto updateEmployee(Integer employeeId, UpdateEmployeeDto dto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new MemberNotFoundException(employeeId));

        // 사용자 정보 업데이트
        employee.updateInformation(dto.getEmail(), dto.getName());

        return new EmployeeResponseDto(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public void deleteEmployee(Integer employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new MemberNotFoundException(employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }


    @Override
    @Transactional(readOnly = true)
    public EmployeeExistsResponseDto isEmployeeExists(Integer employeeId, String email, String name) {
        boolean exists = employeeRepository.existsByIdAndEmailAndName(employeeId, email, name);
        return new EmployeeExistsResponseDto(exists);
    }
}
