package com.sts.app.ws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.sts.app.ws.ui.model.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionsHandler {
	
	@ExceptionHandler(value= {UserServiceException.class})
	public ResponseEntity<Object> handleUserServiceException(UserServiceException e, WebRequest request){
		
		ErrorMessage errorMessage = new ErrorMessage(new Date(), e.getMessage());
		
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value= {Exception.class})
	public ResponseEntity<Object> handleUserOtherException(Exception e, WebRequest request){
		
		ErrorMessage errorMessage = new ErrorMessage(new Date(), e.getMessage());
		
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
