package kea.dpang.hrserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import kea.dpang.hrserver.dto.CreateEmployeeDto;
import kea.dpang.hrserver.dto.EmployeeExistsResponseDto;
import kea.dpang.hrserver.dto.EmployeeResponseDto;
import kea.dpang.hrserver.dto.UpdateEmployeeDto;
import kea.dpang.hrserver.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "사원 추가", description = "새로운 사원 정보를 시스템에 추가합니다.")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody CreateEmployeeDto createEmployeeDto) {
        EmployeeResponseDto employee = employeeService.createEmployee(createEmployeeDto);
        log.info("새로운 직원 생성 완료. 직원 ID: {}", employee.getId());

        // ServletUriComponentsBuilder를 이용해 생성된 직원의 URI를 구성
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") // 생성된 직원의 id를 경로에 추가
                .buildAndExpand(employee.getId()) // 경로에 변수를 추가하고 URI를 빌드
                .toUri();

        // 생성된 직원의 URI를 'Location' 헤더에 추가하고, 상태 코드 201을 반환.
        return ResponseEntity.created(location).body(employee);
    }

    @GetMapping("/{employeeId}")
    @Operation(summary = "사원 조회", description = "등록된 사원 정보를 조회합니다.")
    public ResponseEntity<EmployeeResponseDto> getEmployee(@PathVariable Integer employeeId) {
        EmployeeResponseDto employee = employeeService.getEmployee(employeeId);
        log.info("직원 정보 조회 완료. 직원 ID: {}", employee.getId());

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{employeeId}")
    @Operation(summary = "사원 수정", description = "기존 사원 정보를 업데이트합니다.")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable Integer employeeId, @RequestBody UpdateEmployeeDto updateEmployeeDto) {
        EmployeeResponseDto employee = employeeService.updateEmployee(employeeId, updateEmployeeDto);
        log.info("직원 정보 업데이트 완료. 직원 ID: {}", employee.getId());

        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{employeeId}")
    @Operation(summary = "사원 삭제", description = "사원 정보를 시스템에서 제거합니다.")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
        log.info("직원 정보 삭제 완료. 직원 ID: {}", employeeId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{employeeId}")
    @Operation(summary = "사원 존재 여부 확인", description = "특정 사원이 시스템에 존재하는지 확인합니다.")
    public ResponseEntity<EmployeeExistsResponseDto> isEmployeeExists(@PathVariable Integer employeeId, @RequestParam String email, @RequestParam String name) {
        EmployeeExistsResponseDto result = employeeService.isEmployeeExists(employeeId, email, name);
        log.info("직원 존재 여부 확인 완료. 직원 ID: {}, 이메일: {}, 이름: {}", employeeId, email, name);

        return ResponseEntity.ok(result);
    }
}
