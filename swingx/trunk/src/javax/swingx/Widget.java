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
import javax.swingx.connect.Signal;

/**
 * This interface is for defining so called widgets. Widgets are graphical
 * components which have the ability to react to user actions. A widget has the
 * ability to send signals and alternatively to deal with mediators. The general
 * interface for mediator is changed(Widget) which calls the mediators
 * widgetChange(Object) method. changed(Widget) is also capable to be a signal
 * for all other methods used.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public interface Widget extends ConnectionHandler {

	/**
	 * This method adds a mediator to the widget. This method does not do any
	 * more than connecting the changed() method to the widgetChanged(Widget)
	 * method of the mediator. It is not planned to have a functionality to
	 * allow to remove mediators from widgets. This is something which should
	 * show a weak implementation.
	 * 
	 * It is also not planned to allow more than one mediator. This is something
	 * which should be avoided for clean layouts. It's also to pay a lot of
	 * attention to the connections if there is a mediator defined to avoid
	 * infinite loops because of updates of the updated objects.
	 * 
	 * @param mediator
	 *            is the reference to the mediator to be added.
	 */
	public void addMediator(Mediator mediator);

	/**
	 * This method is called when an update took place. This method emits all
	 * signals connected and also calls all widgetChanged(Widget) methods from
	 * all connected mediators.
	 * 
	 * @param widget
	 *            is a reference to be send to the mediator to signal which
	 *            widget was effected.
	 */
	@Signal
	public void changed(Widget widget);
}
