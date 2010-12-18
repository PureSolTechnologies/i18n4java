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

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;

public class Frame extends JFrame implements ConnectionHandler {

    private static final long serialVersionUID = -7797445282861924849L;

    private final ConnectionManager connectionManager =
	    ConnectionManager.createFor(this);

    public Frame() throws HeadlessException {
	super();
    }

    public Frame(GraphicsConfiguration gc) {
	super(gc);
    }

    public Frame(String title, GraphicsConfiguration gc) {
	super(title, gc);
    }

    public Frame(String title) throws HeadlessException {
	super(title);
    }

    @Override
    public void connect(String signal, Object receiver, String slot,
	    Class<?>... types) {
	connectionManager.connect(signal, receiver, slot, types);
    }

    @Override
    public boolean isConnected(String signal, Object receiver,
	    String slot, Class<?>... types) {
	return connectionManager
		.isConnected(signal, receiver, slot, types);
    }

    @Override
    public void release(String signal, Object receiver, String slot,
	    Class<?>... types) {
	connectionManager.release(signal, receiver, slot, types);
    }

    protected void emitSignal(String signal, Object... params) {
	connectionManager.emitSignal(signal, params);
    }
}
