package com.edfer.model.filtersDTO;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldToJsonDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String field;
	
	private String defaultMessage;
}
