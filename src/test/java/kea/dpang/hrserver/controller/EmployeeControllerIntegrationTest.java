package kea.dpang.hrserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.dpang.hrserver.dto.EmployeeResponseDto;
import kea.dpang.hrserver.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void init() {
        employeeRepository.deleteAll();
    }

    @Test
    public void 사원_생성_1() throws Exception {
        // Given: 생성할 직원 정보가 주어졌을 때
        String employeeJson = "{\"email\":\"test@test.com\",\"name\":\"test\",\"hireDate\":\"2023-12-30\"}";

        // When: 새로운 직원 생성 요청을 하면
        // Then: 응답 상태 코드가 201(created)이어야 한다.
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void 사원_생성_2_중복_사원_생성() throws Exception {
        // Given: 직원이 존재할 때
        String employeeJson = "{\"email\":\"test@test.com\",\"name\":\"test\",\"hireDate\":\"2023-12-30\"}";

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andReturn();

        // When: 동일한 직원 정보로 생성 요청을 하면
        // Then: 상태코드가 400(Bad request)이어야 한다
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void 사원_조회_1() throws Exception {
        // Given : 직원이 존재하지 않을 때
        // When: 사원 조회 요청을 하면
        // Then: 상태코드가 404(Not found)이어야 한다
        mockMvc.perform(get("/employees/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void 사원_조회_2() throws Exception {
        // Given: 직원이 존재할 때
        String employeeJson = "{\"email\":\"test@test.com\",\"name\":\"test\",\"hireDate\":\"2023-12-30\"}";

        MvcResult result = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        EmployeeResponseDto createdEmployee = new ObjectMapper().readValue(response, EmployeeResponseDto.class);

        // When: 사원 조회 요청을 하면
        // Then: 상태코드가 200이어야 한다
        mockMvc.perform(get("/employees/{id}", createdEmployee.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void 사원_수정_1() throws Exception {
        // Given : 직원이 존재하지 않을 때
        // When: 사원 수정 요청을 하면
        // Then: 상태코드가 404(Not found)이어야 한다
        String employeeJson = "{\"email\":\"test2@test.com\",\"name\":\"test2\",\"dateOfBirth\":\"2023-12-31\"}";
        mockMvc.perform(put("/employees/{id}", new Random().nextInt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void 사원_수정_2() throws Exception {
        // Given: 직원이 존재할 때
        String employeeJson = "{\"email\":\"test@test.com\",\"name\":\"test\",\"hireDate\":\"2023-12-30\"}";

        MvcResult result = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        EmployeeResponseDto createdEmployee = new ObjectMapper().readValue(response, EmployeeResponseDto.class);

        // When: 사원 수정 요청을 하면
        // Then: 상태코드가 200이어야 한다
        String updateEmployeeJson = "{\"email\":\"test2@test.com\",\"name\":\"test2\",\"dateOfBirth\":\"2023-12-31\"}";
        mockMvc.perform(put("/employees/{id}", createdEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateEmployeeJson))
                .andExpect(status().isOk());
    }

    @Test
    public void 사원_삭제_1() throws Exception {
        // Given : 직원이 존재하지 않을 때
        // When: 사원 삭제 요청을 하면
        // Then: 상태코드가 404(Not found)이어야 한다
        mockMvc.perform(delete("/employees/{id}", new Random().nextInt()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void 사원_삭제_2() throws Exception {
        // Given: 직원이 존재할 때
        String employeeJson = "{\"email\":\"test@test.com\",\"name\":\"test\",\"hireDate\":\"2023-12-30\"}";

        MvcResult result = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        EmployeeResponseDto createdEmployee = new ObjectMapper().readValue(response, EmployeeResponseDto.class);

        // When: 사원 삭제 요청을 하면
        // Then: 상태코드가 204이어야 한다
        mockMvc.perform(delete("/employees/{id}", createdEmployee.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void 사원_존재_여부_확인_1() throws Exception {
        // Given : 직원이 존재하지 않을 때
        // When: 사원 존재 확인 요청을 하면
        // Then: 상태코드가 200이어야 한다
        mockMvc.perform(get("/employees/exists/{id}", new Random().nextInt())
                        .param("email", "test@test.com")
                        .param("name", "test"))
                .andExpect(status().isOk());
    }

    @Test
    public void 사원_존재_여부_확인_2() throws Exception {
        // Given: 직원이 존재할 때
        String employeeJson = "{\"email\":\"test@test.com\",\"name\":\"test\",\"hireDate\":\"2023-12-30\"}";

        MvcResult result = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        EmployeeResponseDto createdEmployee = new ObjectMapper().readValue(response, EmployeeResponseDto.class);

        // When: 사원 존재 확인 요청을 하면
        // Then: 상태코드가 200이어야 한다
        mockMvc.perform(get("/employees/exists/{id}", createdEmployee.getId())
                        .param("email", "test@test.com")
                        .param("name", "test"))
                .andExpect(status().isOk());
    }

}