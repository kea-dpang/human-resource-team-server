package kea.dpang.hrserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeExistsResponseDto {

    @JsonProperty("isExist")
    @Schema(example = "true", requiredProperties = "true", description = "사원 존재 여부")
    private boolean exist;
}
