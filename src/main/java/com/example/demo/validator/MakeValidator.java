package com.example.demo.validator;

import org.springframework.util.StringUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

public class MakeValidator extends AbstractValidator {

	@Override
	public void validate(ValidationContext ctx) {
		String val = (String) ctx.getProperty().getValue();
		if(StringUtils.isEmpty(val)||val.length()>10) {
			addInvalidMessage(ctx, "fr","vui lòng không để trống và  phải dưới 10 kí tự ");
		}
	}
 

}
