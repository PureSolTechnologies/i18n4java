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

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;

/**
 * This panel is a inheritance of JPanel. The Panel was enhanced with connection
 * handling and translation facilities.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class Panel extends JPanel implements ConnectionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This variable keeps the instance of the connection manager.
	 */
	protected ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	/**
	 * This is a standard constructor.
	 * 
	 */
	public Panel() {
		super();
	}

	/**
	 * This method sets a titled border on a very easy way. A text can be
	 * specified, but everything else is standard.
	 * 
	 * @param title
	 *            is the title of the border.
	 */
	public void setTitledBorder(String title) {
		this.setBorder(new TitledBorder(title));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.connect(signal, receiver, slot, types);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void release(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.release(signal, receiver, slot, types);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected(String signal, Object receiver, String slot,
			Class<?>... types) {
		return connectionManager.isConnected(signal, receiver, slot, types);
	}
}
