package com.nqminh.api.requestbody;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryDetailsRequestBody {

	@NotBlank
	@Size(min = 3, message = "Category name must have more than 3 characters")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
