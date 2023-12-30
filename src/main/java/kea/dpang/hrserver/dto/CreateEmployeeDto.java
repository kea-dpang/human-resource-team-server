package kea.dpang.hrserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateEmployeeDto {

    @Schema(example = "namsh1125@dpang.com", requiredProperties = "true", description = "사원 이메일")
    private String email;

    @Schema(example = "남승현", requiredProperties = "true", description = "사원 이름")
    private String name;

    @Schema(example = "2023-12-30", requiredProperties = "true", description = "입사 날짜")
    private LocalDate hireDate;
}
