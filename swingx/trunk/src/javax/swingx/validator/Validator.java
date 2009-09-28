package javax.swingx.validator;

import java.util.Date;

public interface Validator {

	public boolean isValid(String input);

	public boolean isValid(Boolean input);

	public boolean isValid(Integer input);

	public boolean isValid(Float input);

	public boolean isValid(Double input);

	public boolean isValid(Date input);
}
