package javax.swingx.validator;

import java.util.Date;
import java.util.regex.Pattern;

import javax.swingx.data.Time;

public class RegExpValidator extends AbstractValidator {

	private Pattern pattern = null;

	public RegExpValidator(String regExp) {
		pattern = Pattern.compile(regExp);
	}

	public boolean isValid(String input) {
		return pattern.matcher(input).matches();
	}

	public boolean isValid(Boolean input) {
		return pattern.matcher(Boolean.toString(input)).matches();
	}

	public boolean isValid(Float input) {
		return pattern.matcher(Float.toString(input)).matches();
	}

	public boolean isValid(Double input) {
		return pattern.matcher(Double.toString(input)).matches();
	}

	public boolean isValid(Date input) {
		return pattern.matcher(Time.date2String(input)).matches();
	}

	public boolean isValid(Integer input) {
		return pattern.matcher(Integer.toString(input)).matches();
	}

}
