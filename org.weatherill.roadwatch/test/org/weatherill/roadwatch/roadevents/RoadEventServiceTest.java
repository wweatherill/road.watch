/**
 * 
 */
package org.weatherill.roadwatch.roadevents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.weatherill.roadwatch.journey.Journey;
import org.weatherill.roadwatch.journey.JourneyInstances;
import org.weatherill.roadwatch.location.Location;
import org.weatherill.roadwatch.location.LocationInstances;

/**
 * Tests for {@link RoadEventService}
 * 
 * @author william
 *
 */
public class RoadEventServiceTest extends TestCase
{
	/** The service under test */
	private RoadEventService test = null;
	
	/** This will stay null */
	private Journey journey01 = null;
	
	/** Journey with 0 steps */
	private Journey journey02 = null;
	
	/** Journey with 1 step (doesn't match any road events) */
	private Journey journey03 = null;

	/** Journey with 1 step (matches one road event) */
	private Journey journey04 = null;
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		// setup lookup for Location --> RoadEvents
		Set<RoadEvent> roadEvents = new HashSet<RoadEvent>();
		roadEvents.add(RoadEventInstances.INSTANCES.getRoadEvent001());
		
		Map<Location, Set<RoadEvent>> roadEventLookup = new HashMap<Location, Set<RoadEvent>>();
		roadEventLookup.put(LocationInstances.INSTANCE.getLocation002(), roadEvents);

		RoadEventFinderMock roadEventFinder = new RoadEventFinderMock();
		roadEventFinder.setLookup(roadEventLookup);
		
		test = new RoadEventService(roadEventFinder);
		
		journey01 = null;
		journey02 = JourneyInstances.INSTANCE.getJourney001();
		journey03 = JourneyInstances.INSTANCE.getJourney002();
		journey04 = JourneyInstances.INSTANCE.getJourney003();		
	}

	/**
	 * Test method for {@link org.weatherill.roadwatch.roadevents.RoadEventService#getRoadEvents(org.weatherill.roadwatch.journey.Journey)}.
	 * @throws RoadEventFinderException 
	 */
	public void testGetRoadEvents() throws RoadEventFinderException
	{
		// null Journey
		assertTrue("Since a null Journey has been passed an empty set should have been returned.", test.getRoadEvents(journey01).isEmpty());
		
		// Journey with 0 steps
		assertTrue("Since a Journey with no steps has been passed an empty set should have been returned.", test.getRoadEvents(journey02).isEmpty());
		
		// Journey with 1 step (doesn't match any road events)
		assertTrue("Since a Journey with no matching steps has been passed an empty set should have been returned.", test.getRoadEvents(journey03).isEmpty());
		
		// Journey with 1 step (matches one road event)
		assertEquals("Since a Journey with one matching step has been passed set whose size is 1 should have been returned.", 1, test.getRoadEvents(journey04).size());		
	}

}
