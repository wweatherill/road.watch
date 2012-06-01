package org.weatherill.roadwatch.roadevents.io;

import org.weatherill.roadwatch.roadevents.RoadEvent;
import org.weatherill.roadwatch.roadevents.RoadEventInstances;

import junit.framework.TestCase;

/**
 * Tests for {@link JSON}
 * 
 * @author william
 *
 */
public class JSONTest extends TestCase
{
	/** A road event of known content */
	private RoadEvent test01 = null;
	
	/** The expected serialisation String of {@link JSONTest#test01}*/
	private String test01String = null;
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		test01 = RoadEventInstances.INSTANCES.getRoadEvent001();
		test01String = "[{  \"latitude\": \"10\",  \"longitude\": \"1\",  \"publishedDate\": \"Mon May 21 13:54:33 BST 2012\",  \"title\": \"road event 01\",   \"category\": \"Roadworks\",   \"description\": \"description of road event 01\",  \"road\": \"M1\"}]";
	}

	/**
	 * Test method for {@link JSON#toJson(org.weatherill.roadwatch.roadevents.RoadEvent...)}.
	 */
	public void testToJson()
	{		
		assertEquals("Since no RoadEvents have been passed for serialisation an empty JSON array should be returned.", "[]", JSON.INSTANCE.toJson(new RoadEvent[] {}));
		assertEquals("Since null RoadEvents have been passed for serialisation an empty JSON array should be returned.", "[]", JSON.INSTANCE.toJson((RoadEvent[]) null));
		assertEquals("Make sure that the JSON text is as expected.", test01String, JSON.INSTANCE.toJson(test01));
	}

}
