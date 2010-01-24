package javax.swingx.validator;

public class EMailAddressValidator extends RegExpValidator {

	public EMailAddressValidator() {
		super("[^@]+@[^@]+\\.[^@.]{2,}");
	}
}
