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

package javax.swingx.config;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ClassRegistryElementTypeTest extends TestCase {

    @Test
    public void testFrom() {
	Assert.assertEquals(ClassRegistryElementType.CLONED,
		ClassRegistryElementType.from("cloned"));
	Assert.assertEquals(ClassRegistryElementType.CLONED,
		ClassRegistryElementType.from("CLONED"));

	Assert.assertEquals(ClassRegistryElementType.FACTORY,
		ClassRegistryElementType.from("factory"));
	Assert.assertEquals(ClassRegistryElementType.FACTORY,
		ClassRegistryElementType.from("FACTORY"));

	Assert.assertEquals(ClassRegistryElementType.SINGLETON,
		ClassRegistryElementType.from("singleton"));
	Assert.assertEquals(ClassRegistryElementType.SINGLETON,
		ClassRegistryElementType.from("SINGLETON"));
    }

    @Test
    public void testInvalidName() {
	try {
	    ClassRegistryElementType.from("INVALID");
	    Assert.fail("IllegalArgumentException was expected!");
	} catch (IllegalArgumentException e) {
	    // nothing to catch, because it was expected...
	}
    }

}
