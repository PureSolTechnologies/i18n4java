package javax.swingx.validator;

import java.util.Date;

public class DoubleValidator extends AbstractValidator {

	private double upperLimit = 0;
	private boolean upperLimitSet = false;
	private double lowerLimit = 0;
	private boolean lowerLimitSet = false;

	public void setLimits(int lowerLimit, int upperLimit) {
		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
	}

	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
		lowerLimitSet = true;
	}

	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
		upperLimitSet = true;
	}

	public boolean isValid(String input) {
		try {
			Double doubleValue = Double.valueOf(input);
			return isValid(doubleValue);
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

	public boolean isValid(Date input) {
		return false;
	}

	public boolean isValid(Integer input) {
		return isValid((double) input);
	}

}
