//package com.edfer.except;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import com.edfer.model.filtersDTO.FieldToJsonDTO;
//
//public class ApplicationException extends ResponseEntityExceptionHandler{
//
//	@ExceptionHandler(HttpMessageNotReadableException.class)
//	public ResponseEntity<?> errorDeConversao(HttpMessageNotReadableException e,
//			HttpServletRequest request) {
//		FieldToJsonDTO fieldToJsonDTO = new FieldToJsonDTO("dadoIncorreto", e.getMessage());
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldToJsonDTO);
//	}
//}
