package com.sejong.capstone.common.exception;


import com.sejong.capstone.common.exception.Subtitle.InvalidFileFormat;
import com.sejong.capstone.common.exception.token.*;
import com.sejong.capstone.common.exception.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    /**
     * CET -> Capstone Error Token
     */
    @ExceptionHandler(InvalidJwtToken.class)
    public ResponseEntity<ApiErrorResponse> handleException(InvalidJwtToken ex){
        ApiErrorResponse response = new ApiErrorResponse("CET-001", "Invalid JWT Token. Token : " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenExpired.class)
    public ResponseEntity<ApiErrorResponse> handleException(TokenExpired ex){
        ApiErrorResponse response = new ApiErrorResponse("CET-002", "Token Has Been Expired. Token : " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidRefreshToken.class)
    public ResponseEntity<ApiErrorResponse> handleException(InvalidRefreshToken ex){
        ApiErrorResponse response = new ApiErrorResponse("CET-003", "Invalid Refresh Token. Token : "+ ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RefreshTokenNotFound.class)
    public ResponseEntity<ApiErrorResponse> handleException(RefreshTokenNotFound ex){
        ApiErrorResponse response = new ApiErrorResponse("CET-004", "Refresh Token Not Found : " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IncorrectRefreshToken.class)
    public ResponseEntity<ApiErrorResponse> handleException(IncorrectRefreshToken ex){
        ApiErrorResponse response = new ApiErrorResponse("CET-005", "Incorrect Refresh Token. Token : " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * CEU -> Capstone Error User
     */
    @ExceptionHandler(AccountNotFound.class)
    public ResponseEntity<ApiErrorResponse> handleException(AccountNotFound ex){
        ApiErrorResponse response = new ApiErrorResponse("CEU-001", "Account Not Found. Student ID : " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailNotFound.class)
    public ResponseEntity<ApiErrorResponse> handleException(EmailNotFound ex){
        ApiErrorResponse response = new ApiErrorResponse("CEU-002", "Email Not Found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserIdNotFound.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserIdNotFound ex){
        ApiErrorResponse response = new ApiErrorResponse("CEU-003", "User Id (Primary key) Not Found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordNotFound.class)
    public ResponseEntity<ApiErrorResponse> handleException(PasswordNotFound ex){
        ApiErrorResponse response = new ApiErrorResponse("CEU-004", "Password Not Found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNameNotFound.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserNameNotFound ex){
        ApiErrorResponse response = new ApiErrorResponse("CEU-005", "User Name Not Found.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * CES -> Capstone Error Subtitle
     */
    @ExceptionHandler(InvalidFileFormat.class)
    public ResponseEntity<ApiErrorResponse> handleException(InvalidFileFormat ex){
        ApiErrorResponse response = new ApiErrorResponse("CES-001", "Invalid File Format. file : "+ ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

