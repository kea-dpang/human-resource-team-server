package kea.dpang.hrserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kea.dpang.hrserver.entity.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDto {

    @Schema(example = "1", requiredProperties = "true", description = "사원 번호")
    private Integer id;

    @Schema(example = "namsh1125@dpang.com", requiredProperties = "true", description = "사원 이메일")
    private String email;

    @Schema(example = "남승현", requiredProperties = "true", description = "사원 이름")
    private String name;

    public EmployeeResponseDto(Employee employee) {
        this.id = employee.getId();
        this.email = employee.getEmail();
        this.name = employee.getName();
    }
}