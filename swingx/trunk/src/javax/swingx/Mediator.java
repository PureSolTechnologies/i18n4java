package javax.swingx;

import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.Slot;

/**
 * This interface is for defining so-called mediators. A mediator is an object
 * which cares about changes of widgets. In dialog boxes of instance a widgets
 * change can cause updates in other elements or action should be performed. A
 * mediator takes care about this inter-widget communication.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public interface Mediator extends ConnectionHandler {

	/**
	 * This method is called if any widget has changed. The reference of the
	 * changed widget is transmitted as parameter.
	 * 
	 * @param widget
	 *            is the reference to the currently updated widget.
	 */
	@Slot
	public void widgetChanged(Widget widget);
}
