package javax.swingx;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;
import javax.swingx.validator.Validator;

public class TextField extends JTextField implements Widget, ActionListener,
		CaretListener, FocusListener {

	private static final long serialVersionUID = 1L;

	protected ConnectionManager connectionManager = ConnectionManager
			.createFor(this);
	private Validator validator = null;

	private String oldText;

	public TextField() {
		super();
		initialize();
	}

	public TextField(String text) {
		super(text);
		initialize();
	}

	public TextField(String text, int length) {
		super(text, length);
		initialize();
	}

	public TextField(int length) {
		super(length);
		initialize();
	}

	private void initialize() {
		addActionListener(this);
		addCaretListener(this);
		addFocusListener(this);
		oldText = getText();
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	private void invokeValidator() {
		if (validator == null) {
			return;
		}
		if (validator.isValid(getText())) {
			setForeground(Color.BLACK);
		} else {
			setForeground(Color.RED);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void connect(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.connect(signal, receiver, slot, types);
	}

	/**
	 * {@inheritDoc}
	 */
	public void release(String signal, Object receiver, String slot) {
		connectionManager.release(signal, receiver, slot);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isConnected(String signal, Object receiver, String slot) {
		return connectionManager.isConnected(signal, receiver, slot);
	}

	@Signal
	public void changeText(String text) {
		connectionManager.emitSignal("changeText", text);
	}

	@Signal
	public void changeTextByReturn(String text) {
		connectionManager.emitSignal("changeTextByReturn", text);
	}

	@Signal
	public void changeTextByCaretMove(String text, int dot, int mark) {
		connectionManager.emitSignal("changeTextByCaretMove", text, dot, mark);
	}

	public void actionPerformed(ActionEvent e) {
		changeTextByReturn(getText());
	}

	public void caretUpdate(CaretEvent caretEvent) {
		String text = getText();
		changeTextByCaretMove(text, caretEvent.getDot(), caretEvent.getMark());
		if (!text.equals(oldText)) {
			changeText(text);
			oldText = text;
			invokeValidator();
		}
	}

	@Signal
	public void focusGained(FocusEvent focusEvent) {
		connectionManager.emitSignal("focusGained", focusEvent);
	}

	@Signal
	public void focusLost(FocusEvent focusEvent) {
		connectionManager.emitSignal("focusLost", focusEvent);
	}

	public void addMediator(Mediator mediator) {
		connectionManager.connect("changed", mediator, "widgetChanged");
	}

	public void changed(Widget widget) {
		connectionManager.emitSignal("changed", widget);
	}
}
