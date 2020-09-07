package com.edfer.except;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.edfer.model.filtersDTO.FieldToJsonDTO;

@Configuration
@ControllerAdvice
public class TratarExceptions {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> errorDeConversao(HttpMessageNotReadableException e,
			HttpServletRequest request) {
		FieldToJsonDTO fieldToJsonDTO = new FieldToJsonDTO("dadoIncorreto", e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldToJsonDTO);
	}
}
