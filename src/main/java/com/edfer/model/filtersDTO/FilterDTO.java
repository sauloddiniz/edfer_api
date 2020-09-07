package com.edfer.model.filtersDTO;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FilterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int totalList;

	List<?> objs;
}