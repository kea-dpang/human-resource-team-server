package kea.dpang.hrserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends RuntimeException {
    private final Integer memberId;

    public MemberNotFoundException(Integer memberId) {
        super(String.format("회원을 찾을 수 없음: 회원 ID - '%s'", memberId));
        this.memberId = memberId;
    }
}
