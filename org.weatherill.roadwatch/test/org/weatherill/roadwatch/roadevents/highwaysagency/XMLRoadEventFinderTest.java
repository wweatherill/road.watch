/**
 * 
 */
package org.weatherill.roadwatch.roadevents.highwaysagency;

import org.weatherill.roadwatch.location.Location;
import org.weatherill.roadwatch.location.LocationInstances;
import org.weatherill.roadwatch.roadevents.RoadEventFinderException;

import junit.framework.TestCase;

/**
 * Tests for {@link XMLRoadEventFinder}
 * 
 * @author william
 *
 */
public class XMLRoadEventFinderTest extends TestCase
{
	/** It's source will always throw an exception */
	XMLRoadEventFinder finder01 = null;
	
	/** It's source wraps invalid XML */
	XMLRoadEventFinder finder02 = null;
	
	/** It's source wraps valid XML */
	XMLRoadEventFinder finder03 = null;

	/** An unknown location, not associated with any known road events */
	Location location01 = null;

	/** An known location, road events can be found based on this location */
	Location location02 = null;
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{	
		finder01 = new XMLRoadEventFinder(XMLRoadEventSourceInstances.INSTANCE.getXMLRoadEventSource001());
		finder02 = new XMLRoadEventFinder(XMLRoadEventSourceInstances.INSTANCE.getXMLRoadEventSource002());
		finder03 = new XMLRoadEventFinder(XMLRoadEventSourceInstances.INSTANCE.getXMLRoadEventSource003());
		
		location01 = LocationInstances.INSTANCE.getLocation001();
		location02 = LocationInstances.INSTANCE.getLocation003();
	}

	/**
	 * Test method for {@link org.weatherill.roadwatch.roadevents.highwaysagency.XMLRoadEventFinder#XMLRoadEventFinder(org.weatherill.roadwatch.roadevents.highwaysagency.XMLRoadEventSource)}.
	 */
	public void testXMLRoadEventFinder()
	{
		try
		{
			new XMLRoadEventFinder(null);
			fail("An IllegalArgumentException since the given XMLRoadEventSource was null.");
		}
		catch(IllegalArgumentException iae)
		{
			// we're expecting an exception
		}
	}

	/**
	 * Test method for {@link org.weatherill.roadwatch.roadevents.highwaysagency.XMLRoadEventFinder#getRoadEvents(org.weatherill.roadwatch.location.Location)}.
	 * @throws RoadEventFinderException 
	 */
	public void testGetRoadEvents() throws RoadEventFinderException
	{
		try
		{
			finder01.getRoadEvents(location01);
			fail("the finder's XMLRoadEventSource should have thrown an exception");
		}
		catch (RoadEventFinderException e)
		{
			// we're expecting an exception
		}
		
		try
		{
			finder02.getRoadEvents(location01);
			fail("the finder's should have thrown an exception since the XML is invalid");
		}
		catch (RoadEventFinderException e)
		{
			// we're expecting an exception
		}
		
		assertTrue("make sure there are no road events returned from the finder for an unknown location", finder03.getRoadEvents(location01).isEmpty());
		assertFalse("make sure there are road events returned from the finder for a known location", finder03.getRoadEvents(location02).isEmpty());
	}

}
