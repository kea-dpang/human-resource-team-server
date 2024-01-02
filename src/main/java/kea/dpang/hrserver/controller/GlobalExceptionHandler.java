package kea.dpang.hrserver.controller;

import kea.dpang.hrserver.dto.ErrorResponseDto;
import kea.dpang.hrserver.exception.EmailAlreadyExistsException;
import kea.dpang.hrserver.exception.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMemberNotFoundException(MemberNotFoundException ex) {
        log.error("회원 조회 실패: 회원 ID - '{}'", ex.getMemberId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto("존재하지 않는 회원입니다."));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto("이미 존재하는 이메일입니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(Exception ex) {
        log.error("Exception caught: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDto("예기치 않은 오류가 발생했습니다."));
    }
}
