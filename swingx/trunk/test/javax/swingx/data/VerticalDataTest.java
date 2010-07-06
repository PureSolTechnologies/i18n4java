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

package javax.swingx.data;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class VerticalDataTest extends TestCase {

	@Test
	public void testConstructor() {
		VerticalData data = new VerticalData();
		Assert.assertEquals(0, data.getColumnNumber());
		Assert.assertEquals(0, data.getRowNumber());
	}

	@Test
	public void testSettersAndGetters() {
		VerticalData data = new VerticalData();
		data.addColumn("String", String.class);
		data.addColumn("Integer", Integer.class);
		Assert.assertEquals(0, data.getColumnID("String"));
		Assert.assertEquals(1, data.getColumnID("Integer"));
		data.addRow("Row1", 1);
		data.addRow("Row2", 2);
		Assert.assertEquals(0, data.getColumnID("String"));
		Assert.assertEquals(1, data.getColumnID("Integer"));
		Assert.assertEquals("Row1", data.getString(0, 0));
		Assert.assertEquals("Row2", data.getString(1, 0));
		Assert.assertEquals(1, (int) data.getInteger(0, 1));
		Assert.assertEquals(2, (int) data.getInteger(1, 1));
	}

	@Test
	public void testTynamicalAssignments() {
		VerticalData data = new VerticalData();
		data.addColumn("String", String.class);
		data.addColumn("Integer", Integer.class);
		data.addColumn("Double", Double.class);
		data.addRow("S", "1", "1.1");
		Assert.assertEquals("S", data.getString(0, 0));
		Assert.assertEquals(1, (int) data.getInteger(0, 1));
		Assert.assertEquals(1.1, (double) data.getDouble(0, 2));
	}
}
