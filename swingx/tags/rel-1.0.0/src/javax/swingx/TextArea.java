package javax.swingx;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;

/**
 * TextArea is a multiline text field. The text can be edited if necessary.
 * 
 * @see javax.swing.JTextArea
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class TextArea extends JTextArea implements Widget,
	CaretListener, FocusListener {

    private static final long serialVersionUID = 1L;

    /**
     * This variable keeps the instance of the connection manager.
     */
    protected ConnectionManager connectionManager =
	    ConnectionManager.createFor(this);

    private String oldText;

    /**
     * This is the standard constructor. No text is set.
     */
    public TextArea() {
	super();
	initialize();
    }

    /**
     * This constructor is able to set a starting text for this text field.
     * 
     * @param text
     *            is a String with the text to be set.
     */
    public TextArea(String text) {
	super(text);
	initialize();
    }

    public TextArea(int rows, int columns) {
	super(rows, columns);
	initialize();
    }

    public TextArea(String text, int rows, int columns) {
	super(text, rows, columns);
	initialize();
    }

    private void initialize() {
	addCaretListener(this);
	addFocusListener(this);
	oldText = getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(String signal, Object receiver,
	    String slot, Class<?>... types) {
	connectionManager.connect(signal, receiver, slot,
		types);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void release(String signal, Object receiver,
	    String slot, Class<?>... types) {
	connectionManager.release(signal, receiver, slot,
		types);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConnected(String signal,
	    Object receiver, String slot, Class<?>... types) {
	return connectionManager.isConnected(signal,
		receiver, slot, types);
    }

    @Signal
    public void changeText(String text) {
	connectionManager.emitSignal("changeText", text);
    }

    @Signal
    public void changeTextByCaretMove(String text, int dot,
	    int mark) {
	connectionManager.emitSignal(
		"changeTextByCaretMove", text, dot, mark);
    }

    public void caretUpdate(CaretEvent caretEvent) {
	String text = getText();
	changeTextByCaretMove(text, caretEvent.getDot(),
		caretEvent.getMark());
	if (!text.equals(oldText)) {
	    changeText(text);
	    oldText = text;
	}
    }

    @Signal
    public void focusGained(FocusEvent focusEvent) {
	connectionManager.emitSignal("focusGained",
		focusEvent);
    }

    @Signal
    public void focusLost(FocusEvent focusEvent) {
	connectionManager.emitSignal("focusLost",
		focusEvent);
    }

    public void addMediator(Mediator mediator) {
	connectionManager.connect("changed", mediator,
		"widgetChanged");
    }

    public void changed(Widget widget) {
	connectionManager.emitSignal("changed", widget);
    }
}
