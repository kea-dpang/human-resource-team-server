package kea.dpang.hrserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateEmployeeDto {

    @Schema(example = "namsh1125@dpang.com", requiredProperties = "true", description = "사원 이메일")
    private String email;

    @Schema(example = "남승현", requiredProperties = "true", description = "사원 이름")
    private String name;
}