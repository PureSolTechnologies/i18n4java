/***************************************************************************
 *
 * Copyright 2009-2010 PureSol Technologies 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 *
 ***************************************************************************/

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
