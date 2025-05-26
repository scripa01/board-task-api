package md.pbl.project.boardtaskapi.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import md.pbl.project.boardtaskapi.constants.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class PblCustomException extends Exception {
    private String message;
    private HttpStatus statusCode;
    private ErrorCode errorCode;
    private Throwable cause;

    public PblCustomException(String message) {
        super(message);
        this.message = message;
    }

    public PblCustomException(String message, ErrorCode errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public PblCustomException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.errorCode = errorCode;
        this.cause = cause;
    }

    public PblCustomException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public PblCustomException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.cause = cause;
    }

    public PblCustomException(String userDoesNotExist, HttpStatus httpStatus) {

    }

    public PblCustomException(String userDoesNotExist, ErrorCode errorCode, HttpStatus httpStatus) {
        this.message = userDoesNotExist;
        this.errorCode = errorCode;
        this.statusCode = httpStatus;
    }
}
