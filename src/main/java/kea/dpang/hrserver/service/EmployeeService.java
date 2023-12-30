package kea.dpang.hrserver.service;

import kea.dpang.hrserver.dto.CreateEmployeeDto;
import kea.dpang.hrserver.dto.EmployeeExistsResponseDto;
import kea.dpang.hrserver.dto.EmployeeResponseDto;
import kea.dpang.hrserver.dto.UpdateEmployeeDto;

public interface EmployeeService {

    /**
     * 새로운 직원을 생성합니다.
     *
     * @param createEmployeeDto 생성할 직원의 정보가 담긴 DTO
     * @return 생성된 직원의 정보가 담긴 Response DTO
     */
    EmployeeResponseDto createEmployee(CreateEmployeeDto createEmployeeDto);

    /**
     * 주어진 ID에 해당하는 직원의 정보를 조회합니다.
     *
     * @param employeeId 조회할 직원의 ID
     * @return 조회된 직원의 정보가 담긴 Response DTO
     */
    EmployeeResponseDto getEmployee(Integer employeeId);

    /**
     * 직원의 정보를 업데이트합니다.
     *
     * @param employeeId        업데이트할 직원의 ID
     * @param updateEmployeeDto 업데이트할 정보가 담긴 DTO
     * @return 업데이트된 직원의 정보가 담긴 Response DTO
     */
    EmployeeResponseDto updateEmployee(Integer employeeId, UpdateEmployeeDto updateEmployeeDto);

    /**
     * 주어진 ID에 해당하는 직원을 삭제합니다.
     *
     * @param employeeId 삭제할 직원의 ID
     */
    void deleteEmployee(Integer employeeId);

    /**
     * 주어진 ID에 해당하는 직원이 존재하는지 확인합니다.
     *
     * @param employeeId 확인할 직원의 ID
     * @return 직원의 존재 여부
     */
    EmployeeExistsResponseDto isEmployeeExists(Integer employeeId, String email, String name);
}
