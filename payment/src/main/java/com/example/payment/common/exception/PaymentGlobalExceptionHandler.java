package com.example.payment.common.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentGlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundExceptions(NotFoundException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
		return ResponseEntity.status(NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse("예상치 못한 서버 오류가 발생했습니다.");
		return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}
