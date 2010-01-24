package javax.swingx.validator;

import java.util.Date;

public class IntegerValidator extends AbstractValidator {

	private int upperLimit = 0;
	private boolean upperLimitSet = false;
	private int lowerLimit = 0;
	private boolean lowerLimitSet = false;

	public void setLimits(int lowerLimit, int upperLimit) {
		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
	}

	public void setLowerLimit(int lowerLimit) {
		this.lowerLimit = lowerLimit;
		lowerLimitSet = true;
	}

	public void setUpperLimit(int upperLimit) {
		this.upperLimit = upperLimit;
		upperLimitSet = true;
	}

	public boolean isValid(String input) {
		try {
			Integer integer = Integer.valueOf(input);
			return isValid(integer);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isValid(Boolean input) {
		return false;
	}

	public boolean isValid(Float input) {
		return false;
	}

	public boolean isValid(Double input) {
		return false;
	}

	public boolean isValid(Date input) {
		return false;
	}

	public boolean isValid(Integer input) {
		if (upperLimitSet) {
			if (input > upperLimit) {
				return false;
			}
		}
		if (lowerLimitSet) {
			if (input < lowerLimit) {
				return false;
			}
		}
		return true;
	}

}
