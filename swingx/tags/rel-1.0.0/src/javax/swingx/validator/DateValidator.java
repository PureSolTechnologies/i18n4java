package javax.swingx.validator;

import java.util.Date;

import javax.swingx.data.Time;

public class DateValidator extends AbstractValidator {

	public boolean isValid(String input) {
		Date date = Time.string2Date(input);
		if (date == null) {
			return false;
		}
		return true;
	}

	public boolean isValid(Boolean input) {
		return false;
	}

	public boolean isValid(Integer input) {
		return false;
	}

	public boolean isValid(Float input) {
		return false;
	}

	public boolean isValid(Double input) {
		return false;
	}

	public boolean isValid(Date input) {
		return true;
	}

}
