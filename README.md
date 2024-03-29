﻿# BE_Human_Resource_Team_Server

## 🌐 프로젝트 개요

해당 프로젝트는 DPANG 서비스의 외부 서비스인 인사팀 서버로, DPANG 서비스에 회원가입을 할 때 사원 검증 용도로 사용되는 서버입니다.

```mermaid
sequenceDiagram
    participant 사용자 as 사용자
    participant DPANG서비스 as DPANG 서비스
    participant 인사팀서버 as 인사팀 서버
    participant 인사팀DB as 인사팀 DB
    사용자 ->> DPANG서비스: 회원가입 요청
    DPANG서비스 ->> 인사팀서버: 사원 존재 여부 확인 요청
    인사팀서버 ->> 인사팀DB: 사원 조회
    인사팀DB ->> 인사팀서버: 사원 존재 여부 응답
    인사팀서버 ->> DPANG서비스: 사원 존재 여부 결과 응답
    DPANG서비스 ->> 사용자: 회원가입 성공/실패 응답

```

## 🗃️ 데이터베이스 구조

인사팀 서버에서 사용하는 데이터베이스(Postgresql)는 사원 정보 관리를 위해 다음과 같은 테이블 구조를 가지고 있습니다

| 필드명         | 데이터 타입               | 설명      |
|-------------|----------------------|---------|
| employee_id | INTEGER, PRIMARY KEY | 사원 번호   |
| email       | VARCHAR(50), UNIQUE  | 회사 이메일  |
| name        | VARCHAR(5)           | 직원의 이름  |
| hire_date   | DATE                 | 직원의 입사일 |

> 💡 **참고:** 실제로는 더 복잡한 테이블 구성이 필요할 수 있지만, 현재는 DPANG 서비스의 사원 검증 목적에 초점을 맞춰 필수 정보만을 포함하여 간소화된 구조로 되어 있습니다.

## 📡 API 명세

인사팀 서버는 사원 정보 관리를 위해 다음과 같은 API를 제공합니다:

1. 사원 추가: 새로운 사원 정보를 시스템에 추가합니다.

2. 사원 조회: 등록된 사원 정보를 조회합니다.

3. 사원 수정: 기존 사원 정보를 업데이트합니다.

4. 사원 삭제: 사원 정보를 시스템에서 제거합니다.

5. 사원 존재 여부 확인: 특정 사원이 시스템에 존재하는지 확인합니다.

API 명세서에 대해 보고 싶으신 분은 [여기](https://editor.swagger.io/#/?import=https://github.com/KEA-DPang/BE_Human_Resource_Team_Server/blob/release-v1.1.0/docs/openapi.yaml)를 클릭해 주세요

## 🛠️ 프로젝트 개발 환경

프로젝트는 아래 환경에서 개발되었습니다.

> OS: Windows 10   
> IDE: Intellij IDEA  
> Java 17

## ✅ 프로젝트 실행

해당 프로젝트를 추가로 개발 혹은 실행시켜보고 싶으신 경우 아래의 절차에 따라 진행해주세요

#### 1. `secret.yml` 생성

```commandline
cd ./src/main/resources
touch secret.yml
```

#### 2. `secret.yml` 작성

```text
spring:
  datasource:
    url: {Database_URL}
    username: {Name}
    password: {Password}
```

#### 3. 프로젝트 실행

```commandline
./gradlew bootrun
```

**참고) 프로젝트가 실행 중인 환경에서 아래 URL을 통해 API 명세서를 확인할 수 있습니다**

```commandline
http://localhost:8080/swagger-ui/index.html
```
