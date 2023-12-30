package kea.dpang.hrserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployeeDto {

    @Schema(example = "namsh1125@dpang.com", requiredProperties = "true", description = "사원 이메일")
    private String email;

    @Schema(example = "남승현", requiredProperties = "true", description = "사원 이름")
    private String name;
}