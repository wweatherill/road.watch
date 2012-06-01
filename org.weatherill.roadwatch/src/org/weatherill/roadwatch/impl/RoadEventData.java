package org.weatherill.roadwatch.impl;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.weatherill.roadwatch.IRoadWatch;
import org.weatherill.roadwatch.journey.Journey;
import org.weatherill.roadwatch.location.Location;

/**
 * Provides access to road event source data and also some example Journeys that are
 * known to be associated with road events
 * 
 * @author william
 *
 */
public class RoadEventData
{
	/** Those journeys known to be associated with road events */
	private List<Journey> exampleJourneys = null;

	/** Package only access */
	RoadEventData()
	{
		super();
	}

	/**
	 * @return URL - the location of the source data for all road events known to RoadWatch
	 */
	URL getUrl()
	{
		return RoadEventData.class.getResource("sample.road.events.xml");
	}
	
	/** 
	 * @return List - a list of Journeys known to be associated with road events
	 * Should never be null or empty
	 */
	List<Journey> getExampleJourneys()
	{
		if(exampleJourneys == null)
		{
			exampleJourneys = new ArrayList<Journey>();
			
			// Leeds to Sheffield
			exampleJourneys.add(getExampleJourney("53.801576", "-1.549104", "53.381133", "-1.470434"));
			
			// Doncaster to Sheffield
			exampleJourneys.add(getExampleJourney("53.523357", "-1.128651", "53.381133", "-1.470434"));
		}
		
		return exampleJourneys;
	}
	
	/**
	 * Helper method for creating a Journey. The journey will only have 2 steps - a start and an end
	 * 
	 * @param startLatitude - part of the returned Journey's start coords
	 * @param startLongitude - part of the returned Journey's start coords
	 * @param endLatitude - part of the returned Journey's end coords
	 * @param endLongitude - part of the returned Journey's end coords
	 * @return Journey - as described
	 */
	private Journey getExampleJourney(String startLatitude, String startLongitude, String endLatitude, String endLongitude)
	{
		List<Location> steps = new ArrayList<Location>();
			
		Location startLocation = IRoadWatch.API.getLocation(new BigDecimal(startLatitude), new BigDecimal(startLongitude));
		steps.add(startLocation);
		
		Location endLocation = IRoadWatch.API.getLocation(new BigDecimal(endLatitude), new BigDecimal(endLongitude));
		steps.add(endLocation);
		
		return IRoadWatch.API.getJourney(steps);
	}	
}
