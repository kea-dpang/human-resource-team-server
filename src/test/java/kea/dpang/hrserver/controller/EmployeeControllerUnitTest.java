package kea.dpang.hrserver.controller;

import kea.dpang.hrserver.dto.CreateEmployeeDto;
import kea.dpang.hrserver.dto.EmployeeExistsResponseDto;
import kea.dpang.hrserver.dto.EmployeeResponseDto;
import kea.dpang.hrserver.dto.UpdateEmployeeDto;
import kea.dpang.hrserver.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        when(employeeService.createEmployee(any(CreateEmployeeDto.class))).thenReturn(new EmployeeResponseDto());
        when(employeeService.getEmployee(any(Integer.class))).thenReturn(new EmployeeResponseDto());
        when(employeeService.updateEmployee(any(Integer.class), any(UpdateEmployeeDto.class))).thenReturn(new EmployeeResponseDto());
        when(employeeService.isEmployeeExists(any(Integer.class), any(String.class), any(String.class))).thenReturn(new EmployeeExistsResponseDto());
    }

    @Test
    void 사원_생성() throws Exception {
        // POST 요청을 수행하고 HTTP 상태 코드가 201 (Created)인지 검증한다.
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());
    }


    @Test
    void 사원_조회() throws Exception {
        // GET 요청을 수행하고 HTTP 상태 코드가 200 (OK)인지 검증한다.
        mockMvc.perform(get("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void 사원_수정() throws Exception {
        // PUT 요청을 수행하고 HTTP 상태 코드가 200 (OK)인지 검증한다.
        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void 사원_삭제() throws Exception {
        // DELETE 요청을 수행하고 HTTP 상태 코드가 204 (No Content)인지 검증한다.
        mockMvc.perform(delete("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void 사원_존재_여부_확인() throws Exception {
        // GET 요청을 수행하고 HTTP 상태 코드가 200 (OK)인지 검증한다.
        mockMvc.perform(get("/employees/exists/1?email=test@test.com&name=test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}